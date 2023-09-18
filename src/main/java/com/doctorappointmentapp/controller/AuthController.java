package com.doctorappointmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.requestDto.LoginRequest;
import com.doctorappointmentapp.requestDto.RegisterRequest;
import com.doctorappointmentapp.service.DoctorService;
import com.doctorappointmentapp.service.PatientService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/doctor/register")
    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequest request) {
        boolean registrationSuccess = doctorService.registerDoctor(request);
        if (registrationSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Doctor registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor registration failed");
        }
    }

    @PostMapping("/patient/register")
    public ResponseEntity<?> registerPatient(@RequestBody RegisterRequest request) {
        boolean registrationSuccess = patientService.registerPatient(request);
        if (registrationSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient registration failed");
        }
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<?> doctorLogin(@RequestBody LoginRequest request) {
        Doctor doctor = doctorService.loginDoctor(request);
        if (doctor != null) {
            // Generate and return JWT token
            return ResponseEntity.ok("Doctor logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Doctor login failed");
        }
    }

    @PostMapping("/patient/login")
    public ResponseEntity<?> patientLogin(@RequestBody LoginRequest request) {
        Patient patient = patientService.loginPatient(request);
        if (patient != null) {
            // Generate and return JWT token
            return ResponseEntity.ok("Patient logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Patient login failed");
        }
    }
}
