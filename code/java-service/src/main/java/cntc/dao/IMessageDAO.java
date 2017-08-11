package cntc.dao;

import java.util.List;

import cntc.dto.Message;

public interface IMessageDAO extends IBaseDAO{
	public boolean saveMessage(int idRoom, String contentMessage, String senderName);
	public int getIDRoom(String roomName);
	public List<Message> getMessages(String fromUser, String toUser);
	public List<Message> getHistoryMessage(String curUser, String selUser);
}
