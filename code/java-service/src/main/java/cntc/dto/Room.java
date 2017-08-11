package cntc.dto;

public class Room {
	private int id;
	private String roomName;
	private String fromUser;
	private String toUser;
	public int getId() {
		return id;
	}
	public String getRoomName() {
		return roomName;
	}
	public String getFromUser() {
		return fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	
	
}
