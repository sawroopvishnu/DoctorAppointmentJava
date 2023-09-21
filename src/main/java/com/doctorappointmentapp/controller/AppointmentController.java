package com.doctorappointmentapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.doctorappointmentapp.entity.PatientAppointment;
import com.doctorappointmentapp.requestDto.AppointmentRequest;
import com.doctorappointmentapp.responseDto.AppointmentStatusResponse;
import com.doctorappointmentapp.service.AppointmentService;
import com.doctorappointmentapp.service.DoctorService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/getAllAppointments")
    public List<PatientAppointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
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

    @PostMapping("/{id}/upload-report")
    public ResponseEntity<?> uploadPatientReport(@PathVariable Long id, @RequestBody byte[] report) {
        boolean uploaded = appointmentService.uploadPatientReport(id, report);
        if (uploaded) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
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
                    appointment.getStatus()
                ));
            }
            return ResponseEntity.ok(statusResponses);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
