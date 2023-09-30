package com.doctorappointmentapp.ServiceImpl;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.DoctorRepository;
import com.doctorappointmentapp.repository.PatientRepository;
import com.doctorappointmentapp.requestDto.UserDto;
//import com.doctorappointmentapp.requestDto.LoginRequest;
import com.doctorappointmentapp.responseDto.UniversalErrorResponseDTO;
import com.doctorappointmentapp.responseDto.UniversalSuccessResponseDTO;
import com.doctorappointmentapp.securityconfig.JwtTokenHelper;
import com.doctorappointmentapp.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;


	private String generateToken(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenHelper.generateJwtToken(authentication);

	}
	
	private String generateTokenDoctor(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return jwtTokenHelper.generateJwtTokenDoctor(authentication);

	}

	//register patient
	@Override
	public UserDto registerNewPatient(UserDto userDto) {
		Patient user = this.modelMapper.map(userDto, Patient.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.patientRepository.save(user);
		userDto.setPassword(generateToken(userDto.getUsername(), userDto.getPassword()));
		return userDto;
	}

	//login patient
	@Override
	public ResponseEntity<?> loginPatient(UserDto authenticationRequest, HttpServletRequest request) {
		String token = null;
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			token = jwtTokenHelper.generateJwtToken(authentication);
			Patient user = (Patient) authentication.getPrincipal();
			authenticationRequest = this.modelMapper.map(user, UserDto.class);
			authenticationRequest.setPassword(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "email  or password wrong"));
		}
		return ResponseEntity.ok(new UniversalSuccessResponseDTO<UserDto>(200, authenticationRequest));
		}

	//register doctor
		@Override
		public UserDto registerNewDoctor(UserDto userDto) {
			try {
				Doctor user = this.modelMapper.map(userDto, Doctor.class);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				this.doctorRepository.save(user);
				userDto.setPassword(generateTokenDoctor(userDto.getUsername(), userDto.getPassword()));
				return userDto;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		//login doctor
		@Override
		public ResponseEntity<?> loginDoctor(UserDto authenticationRequest, HttpServletRequest request) {
			String token = null;
			try {
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(), authenticationRequest.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				token = jwtTokenHelper.generateJwtTokenDoctor(authentication);
				Doctor user = (Doctor) authentication.getPrincipal();
				authenticationRequest = this.modelMapper.map(user, UserDto.class);
				authenticationRequest.setPassword(token);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(new UniversalErrorResponseDTO(400, "email  or password wrong"));
			}
			return ResponseEntity.ok(new UniversalSuccessResponseDTO<UserDto>(200, authenticationRequest));
			}

	

}
