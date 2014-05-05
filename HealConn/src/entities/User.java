package entities;

import java.io.Serializable;

public class User implements Serializable {
	
	// private instance variables
	private String name;
	private String department;
	private String studentID;
	
	// constructor
	public User(String name, String department, String studentID) {
		this.name = name;
		this.department = department;
		this.studentID = studentID;
	}
	
	// getters
	public String getName() {
		return name;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
}
