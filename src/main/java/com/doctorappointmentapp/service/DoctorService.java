package com.doctorappointmentapp.service;


import java.util.List;
import java.util.Optional;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.PatientAppointment;
//import com.doctorappointmentapp.requestDto.LoginRequest;
//import com.doctorappointmentapp.requestDto.RegisterRequest;
import com.doctorappointmentapp.requestDto.UserDto;


public interface DoctorService {
//
//	Doctor loginDoctor(LoginRequest request);
//
//	boolean registerDoctor(RegisterRequest request);

	boolean acceptAppointment(Long id);

	boolean rejectAppointment(Long id);

	List<Doctor> getAllDoctors();

	Optional<Doctor> getDoctorById(Long id);

	Optional<Doctor> getDoctorByName(String fullName);

	Doctor updateDoctor(Long id, Doctor updatedDoctor);

	void deleteDoctor(Long id);

	List<PatientAppointment> getAppointmentsForPatient(Long patientId);
//
//	boolean registerDoctor(UserDto request);
//
//	Doctor loginDoctor(UserDto request);

   
}
