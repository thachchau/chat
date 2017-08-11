package cntc.bo;

import java.util.List;

import cntc.dao.IMessageDAO;
import cntc.dao.MessageDAOImpl;
import cntc.dto.Message;

public class MessageBO extends BaseBO {
	IMessageDAO messageDAO;
	public MessageBO(){
		messageDAO = new MessageDAOImpl();
	}
	
	public boolean saveMessage(int idRoom, String contentMessage, String senderName){
		messageDAO.setConnection(getConnection());
		return messageDAO.saveMessage(idRoom, contentMessage, senderName);
	}
	
	public int getIDRoom(String roomName){
		messageDAO.setConnection(getConnection());
		return messageDAO.getIDRoom(roomName);
	}
	
	public List<Message> getMessages(String fromUser, String toUser){
		messageDAO.setConnection(getConnection());
		return messageDAO.getMessages(fromUser, toUser);
	}
	
	public List<Message> getHistoryMessage(String curUser, String selUser){
		messageDAO.setConnection(getConnection());
		return messageDAO.getHistoryMessage(curUser, selUser);
	}
}
