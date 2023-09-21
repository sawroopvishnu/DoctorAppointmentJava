package com.doctorappointmentapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.entity.PatientAppointment;
import com.doctorappointmentapp.repository.AppointmentRepository;
import com.doctorappointmentapp.requestDto.AppointmentRequest;


public interface AppointmentService {

	List<PatientAppointment> getAllAppointments();



	List<PatientAppointment> getAppointmentsForDoctor(Long doctorId);

	List<PatientAppointment> getAppointmentsForPatient(Long patientId);

	boolean updateAppointmentStatus(Long id, String status);

	boolean uploadPatientReport(Long id, byte[] report);

	boolean cancelAppointment(Long id);

	PatientAppointment createAppointment(AppointmentRequest appointment);



	Optional<PatientAppointment> getAppointmentById(Long id);

	
   

}
