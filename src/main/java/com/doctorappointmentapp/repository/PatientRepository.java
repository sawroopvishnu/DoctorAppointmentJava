package com.doctorappointmentapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorappointmentapp.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	Optional<Patient> findByUsername(String username);

	boolean existsByUsername(String username);
	}