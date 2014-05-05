package entities;

import java.io.Serializable;

public class HealConnUser extends User implements SignUp, Serializable {
	
	// private instance variables
	private String name;
	private String department;
	private String studentID;
	
	// constructor
	public HealConnUser(String name, String department, String studentID) {
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
	
	// implement inherited User class's abstract methods
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassWord(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	// implements signUp method
	public HealConnUser signUp(String username, String password,
			String name, String department, String id) {
		HealConnUser newUser = new HealConnUser(name, department, id);
		newUser.setPassWord(password);
		newUser.setUserName(username);
		return newUser;
	}
}
