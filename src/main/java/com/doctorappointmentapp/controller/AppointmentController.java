package com.doctorappointmentapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doctorappointmentapp.entity.PatientAppointment;
import com.doctorappointmentapp.repository.AppointmentRepository;
import com.doctorappointmentapp.requestDto.AppointmentRequest;
import com.doctorappointmentapp.responseDto.AppointmentStatusResponse;
import com.doctorappointmentapp.service.AppointmentService;
import com.doctorappointmentapp.service.DoctorService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/appointments")
public class AppointmentController {
	
	@Autowired
	@Value("${upload-dir}") // Define this property in your application.properties
    private String uploadDir;
	
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getAllAppointments")
    public List<PatientAppointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}") // Get Appointment By Appointment Id
    public ResponseEntity<PatientAppointment> getAppointmentById(@PathVariable Long id) {
        Optional<PatientAppointment> appointment = ((AppointmentService) appointmentService).getAppointmentById(id);
        
        if (appointment.isPresent()) {
            return ResponseEntity.ok(appointment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public List<PatientAppointment> getAppointmentsForDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsForDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<PatientAppointment> getAppointmentsForPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsForPatient(patientId);
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<PatientAppointment> createAppointment(@RequestBody AppointmentRequest appointment) {
        PatientAppointment createdAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        boolean updated = appointmentService.updateAppointmentStatus(id, status);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{id}/upload-report")
//    public ResponseEntity<?> uploadPatientReport(@PathVariable Long id, @RequestBody byte[] report) {
//        boolean uploaded = appointmentService.uploadPatientReport(id, report);
//        if (uploaded) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    
    
    @PostMapping("/{id}/upload-report")
    public ResponseEntity<?> uploadReport(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
        }

        try {
            // Save the file to the server
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Update the appointment record to store the file path or other information
        Optional<PatientAppointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
        	PatientAppointment updatedAppointment = appointment.get();
            updatedAppointment.setReportPath(uploadDir + file.getOriginalFilename());
            appointmentRepository.save(updatedAppointment);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        boolean canceled = appointmentService.cancelAppointment(id);
        if (canceled) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/appointments/status/{patientId}")
    public ResponseEntity<?> getAppointmentStatusForPatient(@PathVariable Long patientId) {
        List<PatientAppointment> appointments = doctorService.getAppointmentsForPatient(patientId);
        if (!appointments.isEmpty()) {
            // Create a response containing appointment statuses
            List<AppointmentStatusResponse> statusResponses = new ArrayList<>();
            for (PatientAppointment appointment : appointments) {
                statusResponses.add(new AppointmentStatusResponse(
                    appointment.getId(),
                    appointment.getStatus(),
                    appointment.getAppointmentDateTime(),
                    appointment.getPatientName()
                ));
            }
            return ResponseEntity.ok(statusResponses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
