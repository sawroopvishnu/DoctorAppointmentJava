package com.doctorappointmentapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.response.PatientResponse;
import com.doctorappointmentapp.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    
    @GetMapping("/getAllPatient")
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> patientResponses = patientService.getAllPatient();
        return ResponseEntity.ok(patientResponses);
    }
    
    
    // Implement REST endpoints for patient-related operations
}

