package com.doctorappointmentapp.requestDto;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;

public class AppointmentRequest {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String patientName;
	    
	    private int age;
	    
	    private String address;
	    
	    private String contactNo;
	    
	    @ManyToOne
	    @JoinColumn(name = "doctor_id")
	    private Doctor doctor;

	    @ManyToOne
	    @JoinColumn(name = "patient_id")
	    private Patient patient;

	    private String appointmentDateTime;

	    private String status; // Pending, Accepted, Rejected

	    private String notes;
	    
	    

		public AppointmentRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPatientName() {
			return patientName;
		}

		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		public Doctor getDoctor() {
			return doctor;
		}

		public void setDoctor(Doctor doctor) {
			this.doctor = doctor;
		}

		public Patient getPatient() {
			return patient;
		}

		public void setPatient(Patient patient) {
			this.patient = patient;
		}

		public String getAppointmentDateTime() {
			return appointmentDateTime;
		}

		public void setAppointmentDateTime(String appointmentDateTime) {
			this.appointmentDateTime = appointmentDateTime;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}


		@Override
		public String toString() {
			return "AppointmentRequest [id=" + id + ", patientName=" + patientName + ", age=" + age + ", address="
					+ address + ", contactNo=" + contactNo + ", doctor=" + doctor + ", patient=" + patient
					+ ", appointmentDateTime=" + appointmentDateTime + ", status=" + status + ", notes=" + notes + "]";
		}

		public AppointmentRequest(Long id, String patientName, int age, String address, String contactNo, Doctor doctor,
				Patient patient, String appointmentDateTime, String status, String notes) {
			super();
			this.id = id;
			this.patientName = patientName;
			this.age = age;
			this.address = address;
			this.contactNo = contactNo;
			this.doctor = doctor;
			this.patient = patient;
			this.appointmentDateTime = appointmentDateTime;
			this.status = status;
			this.notes = notes;
		}
	    
	    

}
