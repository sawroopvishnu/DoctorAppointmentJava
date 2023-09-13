package com.doctorappointmentapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorappointmentapp.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	 Optional<Doctor> findByUsername(String username);

	boolean existsByUsername(String username);

	Optional<Doctor> findByFullName(String fullName);
}
