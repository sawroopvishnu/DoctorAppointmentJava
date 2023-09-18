package com.doctorappointmentapp.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.PatientRepository;
import com.doctorappointmentapp.requestDto.LoginRequest;
import com.doctorappointmentapp.requestDto.RegisterRequest;
import com.doctorappointmentapp.responseDto.PatientResponse;
import com.doctorappointmentapp.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	    @Autowired
	    private PatientRepository patientRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Override
	    public boolean registerPatient(RegisterRequest request) {
	        if (patientRepository.existsByUsername(request.getUsername())) {
	            return false;
	        }

	        Patient patient = new Patient();
	        patient.setUsername(request.getUsername());
	        patient.setPassword(passwordEncoder.encode(request.getPassword()));
	        patient.setFullName(request.getFullName());

	        patientRepository.save(patient);
	        return true;
	    }

	    @Override
	    public Patient loginPatient(LoginRequest request) {
	        Optional<Patient> patientOptional = patientRepository.findByUsername(request.getUsername());
	        if (patientOptional.isPresent()) {
	            Patient patient = patientOptional.get();
	            if (passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
	                return patient;
	            }
	        }
	        return null;
	    }
	    
	    @Override
	    public List<PatientResponse> getAllPatient() {
	        List<Patient> patients = patientRepository.findAll();
	        List<PatientResponse> patientResponses = new ArrayList<>();

	        for (Patient patient : patients) {
	            PatientResponse response = new PatientResponse(patient.getId(), patient.getFullName());
	            patientResponses.add(response);
	        }

	        return patientResponses;
	    }
	    
	    @Override
	    public Optional<Patient> findPatientByUsername(String username) {
	        return patientRepository.findByUsername(username);
	    }

	    
	    @Override
	    public String deletePatient(Long id) {
	        try {
	            patientRepository.deleteById(id);
	            return "Patient with ID " + id + " has been deleted.";
	        } catch (EmptyResultDataAccessException e) {
	            return "Patient with ID " + id + " not found.";
	        }
	    }
	    
	    @Override
	    public Patient getPatientById(Long id) {
	        return patientRepository.findById(id).orElse(null);
	    }

}
