package com.doctorappointmentapp.responseDto;

public class AppointmentStatusResponse {

	private String appointmentDateTime;
	
    public String getAppointmentDateTime() {
		return appointmentDateTime;
	}
	public void setAppointmentDateTime(String appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}
	public AppointmentStatusResponse(String appointmentDateTime) {
		super();
		this.appointmentDateTime = appointmentDateTime;
	}
    
    
    
	private Long appointmentId;
    private String status;
    
    
	public AppointmentStatusResponse(Long appointmentId, String status) {
		super();
		this.appointmentId = appointmentId;
		this.status = status;
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

    
}
