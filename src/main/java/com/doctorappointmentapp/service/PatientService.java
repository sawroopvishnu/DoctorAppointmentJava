package com.doctorappointmentapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.PatientRepository;
import com.doctorappointmentapp.request.LoginRequest;
import com.doctorappointmentapp.request.RegisterRequest;
import com.doctorappointmentapp.response.PatientResponse;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    
    public List<PatientResponse> getAllPatient() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponse> patientResponses = new ArrayList<>();

        for (Patient patient : patients) {
            PatientResponse response = new PatientResponse(patient.getId(), patient.getFullName());
            patientResponses.add(response);
        }

        return patientResponses;
    }

}
