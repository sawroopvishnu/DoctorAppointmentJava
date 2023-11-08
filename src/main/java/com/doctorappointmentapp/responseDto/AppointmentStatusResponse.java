package com.doctorappointmentapp.responseDto;

public class AppointmentStatusResponse {

	    private String appointmentDateTime;
		private Long appointmentId;
	    private String status;
	    private String patientName;
	    
    
	public AppointmentStatusResponse(Long appointmentId, String status,String appointmentDateTime,String patientName) {
		super();
		this.appointmentDateTime = appointmentDateTime;
		this.appointmentId = appointmentId;
		this.status = status;
		this.patientName=patientName;
	}
	
	public String getAppointmentDateTime() {
		return appointmentDateTime;
	}
	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
	
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

    
}
