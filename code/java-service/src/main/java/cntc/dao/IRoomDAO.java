package cntc.dao;

import java.util.List;

public interface IRoomDAO extends IBaseDAO {
	public boolean isSaveRoom(String nameRoom, String senderName, String recieverName);
	public List<String> getContactUsername(String username);
}
