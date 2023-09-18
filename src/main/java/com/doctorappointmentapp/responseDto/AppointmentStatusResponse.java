package com.doctorappointmentapp.responseDto;

public class AppointmentStatusResponse {

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
