package com.doctorappointmentapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.PatientRepository;
import com.doctorappointmentapp.requestDto.LoginRequest;
import com.doctorappointmentapp.requestDto.RegisterRequest;
import com.doctorappointmentapp.responseDto.PatientResponse;

public interface PatientService {

	List<PatientResponse> getAllPatient();

	Patient getPatientById(Long id);

	String deletePatient(Long id);

	Optional<Patient> findPatientByUsername(String username);

	boolean registerPatient(RegisterRequest request);

	Patient loginPatient(LoginRequest request);

   
}
