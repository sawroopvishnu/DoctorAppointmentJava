package com.doctorappointmentapp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class PatientAppointment {
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

    @Lob
    private byte[] patientReport; // Storing the report as a byte array

    private String reportPath; // Add this property to store the report file path

    public PatientAppointment() {
        super();
    }

    public PatientAppointment(Long id, String patientName, int age, String address, String contactNo, Doctor doctor,
            Patient patient, String appointmentDateTime, String status, String notes, byte[] patientReport, String reportPath) {
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
        this.patientReport = patientReport;
        this.reportPath = reportPath; // Initialize the report path
    }

    // Getters and setters for the report path
    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
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

	public byte[] getPatientReport() {
		return patientReport;
	}

	public void setPatientReport(byte[] patientReport) {
		this.patientReport = patientReport;
	}

    // Rest of the getters and setters

}
