package com.doctorappointmentapp.securityconfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.DoctorRepository;
import com.doctorappointmentapp.repository.PatientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Doctor> doctorOptional = doctorRepository.findByUsername(username);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            return User.builder()
                    .username(doctor.getUsername())
                    .password(doctor.getPassword())
                    .roles("DOCTOR")
                    .build();
        }

        Optional<Patient> patientOptional = patientRepository.findByUsername(username);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            return User.builder()
                    .username(patient.getUsername())
                    .password(patient.getPassword())
                    .roles("PATIENT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
