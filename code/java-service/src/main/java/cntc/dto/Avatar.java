package cntc.dto;

public class Avatar {
	private int id;
	private String username;
	private String image;
	private byte[] avatar;
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getImage() {
		return image;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public byte[] getAvatar() {
		return avatar;
	}
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	
	
}
