package com.doctorappointmentapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.doctorappointmentapp.requestDto.UserDto;

public interface AuthService {

	UserDto registerNewPatient(UserDto userDto);

	ResponseEntity<?> loginPatient(UserDto authenticationRequest, HttpServletRequest request);

	UserDto registerNewDoctor(UserDto userDto);

	ResponseEntity<?> loginDoctor(UserDto authenticationRequest, HttpServletRequest request);

}
