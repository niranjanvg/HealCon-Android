package entities;

public abstract class User {
	
	protected String userName;
	protected String password;
	
	public User() {
	}
	
	abstract void setUserName(String userName) ;
	abstract void setPassWord(String password);
	abstract String getUserName();
	
	// no getter for password for user privacy
}
