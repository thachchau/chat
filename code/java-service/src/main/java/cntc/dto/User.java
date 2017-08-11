package cntc.dto;

public class User {
	private int iD;
	private String email;
	private String username;
	private String password;
	private String cfm_password;
	private boolean isOnline;
	
	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getCfm_password() {
		return cfm_password;
	}

	public void setCfm_password(String cfm_password) {
		this.cfm_password = cfm_password;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
