package com.doctorappointmentapp.entity;


import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
public class Doctor implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String fullName;
    
    @Column(nullable = false)
    private String specialization;

    private String qualification;

    private String experience;

    private String profileImageUrl;
    
    

	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Doctor(Long id, String username, String password, String fullName, String specialization,
			String qualification, String experience, String profileImageUrl) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.specialization = specialization;
		this.qualification = qualification;
		this.experience = experience;
		this.profileImageUrl = profileImageUrl;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getSpecialization() {
		return specialization;
	}



	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}



	public String getQualification() {
		return qualification;
	}



	public void setQualification(String qualification) {
		this.qualification = qualification;
	}



	public String getExperience() {
		return experience;
	}



	public void setExperience(String experience) {
		this.experience = experience;
	}



	public String getProfileImageUrl() {
		return profileImageUrl;
	}



	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}



	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}



	public Doctor get() {
		// TODO Auto-generated method stub
		return null;
	}



//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define the authorities for the Doctor role, e.g., "ROLE_DOCTOR".
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_DOCTOR"));
    }


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}




    



}
