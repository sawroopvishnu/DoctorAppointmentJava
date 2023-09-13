package com.doctorappointmentapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.entity.PatientAppointment;

public interface AppointmentRepository extends JpaRepository<PatientAppointment, Long> {
    List<PatientAppointment> findByDoctor(Doctor doctor);
    List<PatientAppointment> findByPatient(Patient patient);
	List<PatientAppointment> findByStatus(String status);
	List<PatientAppointment> findByAppointmentDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
	List<PatientAppointment> findByDoctorAndAppointmentDateTimeBetween(Doctor doctor, LocalDateTime startDate,
			LocalDateTime endDate);
	List<PatientAppointment> findByPatientAndStatus(Patient patient, String status);
	List<PatientAppointment> findByDoctorId(Long doctorId);
	List<PatientAppointment> findByPatientId(Long patientId);
}