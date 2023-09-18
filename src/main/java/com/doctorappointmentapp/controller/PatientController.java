package com.doctorappointmentapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.responseDto.PatientResponse;
import com.doctorappointmentapp.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    
    @GetMapping("/getAllPatient")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> patientResponses = patientService.getAllPatient();
        return ResponseEntity.ok(patientResponses);
    }
    
    @GetMapping("getpatient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patient);
    }
    
    @DeleteMapping("deletepatient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Patient has been successfully deleted");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
        }
    }
    
    @GetMapping("/find/{username}") 
    public ResponseEntity<Patient> findPatientByUsername(@PathVariable String username) {
        System.out.println("Received username: " + username);
        Optional<Patient> patient = patientService.findPatientByUsername(username);
        
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get()); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

}

