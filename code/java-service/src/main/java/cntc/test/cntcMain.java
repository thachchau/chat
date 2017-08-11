package cntc.test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cntc.bo.MessageBO;
import cntc.bo.RoomBO;
import cntc.bo.UserBO;
import cntc.dto.Avatar;
import cntc.dto.Message;

public class cntcMain {

	public static void main(String[] args) throws SQLException {
		System.out.println("Hello word !");
//		MessageBO msgBO = new MessageBO();
//		List<Message> messages = new ArrayList<>();
//		messages = msgBO.getHistoryMessage("Bob", "Mike");
//		for(int i = 0; i < messages.size(); i++){
//			Message msgItem = messages.get(i);
//			System.out.println("======================================");
//			System.out.println("id: " + msgItem.getId());
//			System.out.println("create date: " + msgItem.getCr_date());
//			System.out.println("text: " + msgItem.getText());
//			System.out.println("idRoom: " + msgItem.getIdRoom());
//			System.out.println("sender: " + msgItem.getSender());
//		}
//		
//		RoomBO roomBO = new RoomBO();
//		List<String> users = new ArrayList<>();
//		users = roomBO.getContactUsername("Bob");
//		for (int i = 0; i < users.size(); i++) {
//			String user = users.get(i);
//			System.out.println("Username was contacted: " + user);
//		}
		UserBO userbo = new UserBO();
		//userbo.updateAvatar("Phuc", "avt1.jpg");
	}
}
