package cntc.bo;

import java.util.List;

import cntc.dao.IRoomDAO;
import cntc.dao.RoomDAOImpl;

public class RoomBO extends BaseBO {
	IRoomDAO roomDAO;
	public RoomBO(){
		roomDAO = new RoomDAOImpl();
	}
	
	public boolean isSaveRoom(String nameRoom, String senderName, String recieverName){
		roomDAO.setConnection(getConnection());
		return roomDAO.isSaveRoom(nameRoom, senderName, recieverName);
	}
	
	public List<String> getContactUsername(String username){
		roomDAO.setConnection(getConnection());
		return roomDAO.getContactUsername(username);
	}
}
