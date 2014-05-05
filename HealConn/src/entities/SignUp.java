package entities;

public interface SignUp {
	// signUp for a new user
	public HealConnUser signUp(String username, String password, 
			String name, String department, String id);
}
