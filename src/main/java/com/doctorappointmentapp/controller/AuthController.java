package com.doctorappointmentapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorappointmentapp.Exception.ApiException;
import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.requestDto.UserDto;
//import com.doctorappointmentapp.requestDto.LoginRequest;
//import com.doctorappointmentapp.requestDto.RegisterRequest;
import com.doctorappointmentapp.responseDto.UniversalErrorResponseDTO;
import com.doctorappointmentapp.responseDto.UniversalSuccessResponseDTO;
import com.doctorappointmentapp.securityconfig.CustomUserDetailService;
import com.doctorappointmentapp.securityconfig.JwtTokenHelper;
import com.doctorappointmentapp.service.AuthService;
import com.doctorappointmentapp.service.DoctorService;
import com.doctorappointmentapp.service.PatientService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private AuthService authService;
	@Autowired
	private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

//    @PostMapping("/doctor/register")
//    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequest request) {
//        boolean registrationSuccess = doctorService.registerDoctor(request);
//        if (registrationSuccess) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("Doctor registered successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor registration failed");
//        }
//    }
//
//    @PostMapping("/patient/register")
//    public ResponseEntity<?> registerPatient(@RequestBody RegisterRequest request) {
//        boolean registrationSuccess = patientService.registerPatient(request);
//        if (registrationSuccess) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("Patient registered successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient registration failed");
//        }
//    }
//
//    @PostMapping("/doctor/login")
//    public ResponseEntity<?> doctorLogin(@RequestBody LoginRequest request) {
//        Doctor doctor = doctorService.loginDoctor(request);
//        if (doctor != null) {
//            // Generate and return JWT token
//            return ResponseEntity.ok("Doctor logged in successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Doctor login failed");
//        }
//    }
//
//    @PostMapping("/patient/login")
//    public ResponseEntity<?> patientLogin(@RequestBody LoginRequest request) {
//        Patient patient = patientService.loginPatient(request);
//        if (patient != null) {
//            // Generate and return JWT token
//            return ResponseEntity.ok("Patient logged in successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Patient login failed");
//        }
//    }
    
    
    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e){
            System.out.println("Invalid details");
            throw new ApiException("Invalid userName or password");
        }
    }
    
    
    
    //patient/login
    @PostMapping("/patient/login")
    public ResponseEntity<?> login(@RequestBody UserDto authenticationRequest, HttpServletRequest request){
        if (authenticationRequest.getUsername() == null) {
            return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "E-mail address not entered"));
        }
        if (authenticationRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "Password not entered"));
        }
        return authService.loginPatient(authenticationRequest, request);
    }


    //patient registerr
    @PostMapping("/patient/register")
    public ResponseEntity<?> patientRegister(@RequestBody UserDto userDto){
    	UserDto userRegistered = this.authService.registerNewPatient(userDto);
        return ResponseEntity.ok(new UniversalSuccessResponseDTO<>(200, userRegistered));
    }
    
    //doctor/login
    @PostMapping("/doctor/login")
    public ResponseEntity<?> doctorLogin(@RequestBody UserDto authenticationRequest, HttpServletRequest request){
        if (authenticationRequest.getUsername() == null) {
            return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "User-Name address not entered"));
        }
        if (authenticationRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "Password not entered"));
        }
        return authService.loginDoctor(authenticationRequest, request);
    }

    //doctor registerr
    @PostMapping("/doctor/register")
    public ResponseEntity<?> doctorRegister(@RequestBody UserDto userDto){
        UserDto userRegistered = this.authService.registerNewDoctor(userDto);
        return ResponseEntity.ok(new UniversalSuccessResponseDTO<>(200, userRegistered));
    }
    
    
}
