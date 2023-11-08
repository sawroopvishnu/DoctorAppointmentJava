package com.doctorappointmentapp.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.PatientAppointment;
import com.doctorappointmentapp.repository.AppointmentRepository;
import com.doctorappointmentapp.requestDto.AppointmentRequest;
import com.doctorappointmentapp.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	
	    @Autowired
	    private AppointmentRepository appointmentRepository;

	    public List<PatientAppointment> getAllAppointments() {
	        return appointmentRepository.findAll();
	    }

	    public Optional<PatientAppointment> getAppointmentById(Long id) {
	        return appointmentRepository.findById(id);
	    }

	    public List<PatientAppointment> getAppointmentsForDoctor(Long doctorId) {
	        return appointmentRepository.findByDoctorId(doctorId);
	    }

	    public List<PatientAppointment> getAppointmentsForPatient(Long patientId) {
	        return appointmentRepository.findByPatientId(patientId);
	    }

	    public PatientAppointment createAppointment(AppointmentRequest appointment) {
	        // Set status to Pending and appointment creation time
	    	System.out.println(appointment.toString());
	        //appointment.setStatus("Pending");
	        //appointment.setAppointmentDateTime(LocalDateTime.now());
	        PatientAppointment patientAppointment = new PatientAppointment();
	        patientAppointment.setPatientName(appointment.getPatientName());
	        patientAppointment.setAddress(appointment.getAddress());
	        patientAppointment.setAge(appointment.getAge());
	        patientAppointment.setStatus("pending");
	        patientAppointment.setContactNo(appointment.getContactNo());
	        patientAppointment.setDoctor(appointment.getDoctor());
	        patientAppointment.setPatient(appointment.getPatient());
	        patientAppointment.setNotes(appointment.getNotes());
	        patientAppointment.setAppointmentDateTime(appointment.getAppointmentDateTime());

	        return appointmentRepository.save(patientAppointment);
	    }

	    public boolean updateAppointmentStatus(Long appointmentId, String status) {
	        Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointmentId);
	        if (appointmentOptional.isPresent()) {
	            PatientAppointment appointment = appointmentOptional.get();
	            appointment.setStatus(status);
	            appointmentRepository.save(appointment);
	            return true;
	        }
	        return false;
	    }

//	    public boolean uploadPatientReport(Long appointmentId, byte[] report) {
//	        Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointmentId);
//	        if (appointmentOptional.isPresent()) {
//	            PatientAppointment appointment = appointmentOptional.get();
//	            appointment.setPatientReport(report);
//	            appointmentRepository.save(appointment);
//	            return true;
//	        }
//	        return false;
//	    }

	    public boolean cancelAppointment(Long appointmentId) {
	        Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointmentId);
	        if (appointmentOptional.isPresent()) {
	            appointmentRepository.deleteById(appointmentId);
	            return true;
	        }
	        return false;
	    }

}
