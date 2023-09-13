package com.doctorappointmentapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.PatientAppointment;
import com.doctorappointmentapp.repository.AppointmentRepository;
import com.doctorappointmentapp.repository.DoctorRepository;
import com.doctorappointmentapp.request.LoginRequest;
import com.doctorappointmentapp.request.RegisterRequest;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Optional<Doctor> getDoctorByName(String fullName) {
        return doctorRepository.findByFullName(fullName);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctorOptional = doctorRepository.findById(id);
        if (existingDoctorOptional.isPresent()) {
            Doctor existingDoctor = existingDoctorOptional.get();
            existingDoctor.setFullName(updatedDoctor.getFullName());
            existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
            existingDoctor.setQualification(updatedDoctor.getQualification());
            existingDoctor.setExperience(updatedDoctor.getExperience());
            existingDoctor.setProfileImageUrl(updatedDoctor.getProfileImageUrl());
            // Update other fields as needed

            return doctorRepository.save(existingDoctor);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }
    
    
    public boolean acceptAppointment(Long appointmentId) {
        Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isPresent()) {
            PatientAppointment appointment = appointmentOptional.get();
            appointment.setStatus("Accepted");
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    public boolean rejectAppointment(Long appointmentId) {
        Optional<PatientAppointment> appointmentOptional = appointmentRepository.findById(appointmentId);
        if (appointmentOptional.isPresent()) {
            PatientAppointment appointment = appointmentOptional.get();
            appointment.setStatus("Rejected");
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }
    
    
    
    // User Registration 
    public boolean registerDoctor(RegisterRequest request) {
        if (doctorRepository.existsByUsername(request.getUsername())) {
            return false;
        }

        Doctor doctor = new Doctor();
        doctor.setUsername(request.getUsername());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setFullName(request.getFullName());
        doctor.setExperience(request.getExperience());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setProfileImageUrl(request.getProfileImageUrl());

        doctorRepository.save(doctor);
        return true;
    }

    public Doctor loginDoctor(LoginRequest request) {
        Optional<Doctor> doctorOptional = doctorRepository.findByUsername(request.getUsername());
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            if (passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
                return doctor;
            }
        }
        return null;
    }

    public List<PatientAppointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}
