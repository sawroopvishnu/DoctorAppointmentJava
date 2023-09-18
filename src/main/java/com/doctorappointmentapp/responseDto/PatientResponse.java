package com.doctorappointmentapp.responseDto;

public class PatientResponse {
	private Long id;
	  private String fullName;
	public PatientResponse(Long id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}
	public PatientResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	  
	  

}
