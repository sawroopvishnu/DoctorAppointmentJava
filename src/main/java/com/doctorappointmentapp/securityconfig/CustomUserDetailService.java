package com.doctorappointmentapp.securityconfig;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doctorappointmentapp.Exception.ResourceNotFoundException;
import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;
import com.doctorappointmentapp.repository.DoctorRepository;
import com.doctorappointmentapp.repository.PatientRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Doctor> doctorOptional = this.doctorRepository.findByUsername(username);
//        if (doctorOptional.isPresent()) {
//            Doctor doctor = doctorOptional.get();
//            return doctor;
//        }
//
//        Optional<Patient> patientOptional = this.patientRepository.findByUsername(username);
//        if (patientOptional.isPresent()) {
//            Patient patient = patientOptional.get();
//           return patient;
//        }
//
//        throw new UsernameNotFoundException("User not found with username: " + username);
//    }
    
    public CustomUserDetailService(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Doctor> doctorOptional = this.doctorRepository.findByUsername(username);
        if (doctorOptional.isPresent()) {
        	return doctorOptional.get();
        }

        Optional<Patient> patientOptional = this.patientRepository.findByUsername(username);
        if (patientOptional.isPresent()) {
            return patientOptional.get();
            // Create a custom UserDetails class for Patient similarly.
          //  return new Patient(patient);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //load user from database by username
//        Patient user = this.patientRepository.findByUsername(username)
//                .orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username, 0));
//        System.out.println("xxxxxxxxxxxxx");
//        System.out.println(user.toString());
//        return user;
//    }
         //patent
//         @Override
//         public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //load user from database by username
//         Patient user = this.patientRepository.findByUsername(username)
//                .orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username, 0));
//          return user;
//    }
    

//             @Override
//             public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                 Patient user = patientRepository.findByUsername(username);
//                 if(user == null) new UsernameNotFoundException("User not found");
//                 return user;
//             }

//             @Transactional
//             public Doctor loadUserById(String username){
//                 Doctor user = doctorRepository.findByUsername(username);
//                 if(user == null) new UsernameNotFoundException("User not found");
//                 return user;
//             }
//         
         
//         //doctor
//         @Override
//         public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //load user from database by username
//         Doctor user = this.doctorRepository.findByUsername(username)
//                .orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username, 0));
//          return user;
//    }
    
}
