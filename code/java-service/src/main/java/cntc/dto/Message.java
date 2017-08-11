package cntc.dto;

import java.sql.Timestamp;

public class Message {
	private int id;
	private int idRoom;
	private String roomName;
	private String text;
	private String sender;
	private Timestamp cr_date;
	
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getId() {
		return id;
	}
	public int getIdRoom() {
		return idRoom;
	}
	public String getSender() {
		return sender;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Timestamp getCr_date() {
		return cr_date;
	}
	public void setCr_date(Timestamp cr_date) {
		this.cr_date = cr_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idRoom;
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (idRoom != other.idRoom)
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
	
}
