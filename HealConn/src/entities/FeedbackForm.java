package entities;

import java.io.Serializable;

public class FeedbackForm implements Serializable {
	
	// private instance variables
	private String dateOfAppointment;
	private String doctorName;
	private int rating;
	private String additionalComments;
	
	// constructor
	public FeedbackForm(String dateOfAppointment, String doctorName,
						int rating, String additionalComments) {
		this.dateOfAppointment = dateOfAppointment;
		this.doctorName = doctorName;
		this.rating = rating;
		this.additionalComments = additionalComments;
	}
	
	// getters
	public String getDateOfAppointment() {
		return this.dateOfAppointment;
	}
	
	public String getDoctorName() {
		return this.doctorName;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public String getAdditionalComments() {
		return this.additionalComments;
	}
}
