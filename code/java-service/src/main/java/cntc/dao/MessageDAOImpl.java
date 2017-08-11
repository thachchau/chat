package cntc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cntc.dto.Message;

public class MessageDAOImpl extends BaseDAO implements IMessageDAO {

	@Override
	public boolean saveMessage(int idRoom, String contentMessage,
			String senderName) {
		try {
			Statement sts = getConnection().createStatement();
			String sql_insert_msg = "INSERT INTO messages(idRoom, msg, sender) "
					+ "VALUES('"
					+ idRoom
					+ "', "
					+ "'"
					+ contentMessage
					+ "', " + "'" + senderName + "')";
			sts.executeUpdate(sql_insert_msg);
			System.out.println("Insert msg success");
		} catch (SQLException e) {
			System.out.println("Insert msg fail");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public int getIDRoom(String roomName) {
		PreparedStatement ps = null;
		int idRoom = 0;
		String sqlGetIDRoom = "SELECT id FROM room WHERE roomName = '"
				+ roomName + "'";
		try {
			ps = getConnection().prepareStatement(sqlGetIDRoom);
		} catch (SQLException e) {
			System.out.println("select fail");
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				idRoom = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idRoom;
	}

	@Override
	public List<Message> getMessages(String fromUser, String toUser) {
		List<Message> messages = new ArrayList<Message>();
		PreparedStatement ps = null;
		String sql_getMessages = "SELECT * FROM messages WHERE messages.idRoom IN (SELECT room.id FROM room WHERE (room.fromUser = '"
				+ fromUser
				+ "' and room.toUser='"
				+ toUser
				+ "') or (room.fromUser = '"
				+ toUser
				+ "' and room.toUser='"
				+ fromUser + "'))";
		try {
			ps = getConnection().prepareStatement(sql_getMessages);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		try {
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setCr_date(rs.getTimestamp("cr_date"));
				message.setIdRoom(rs.getInt("idRoom"));
				message.setText(rs.getString("msg"));
				message.setSender(rs.getString("sender"));
				messages.add(message);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		Set<Message> filter = new HashSet<Message>();
		filter.addAll(messages);

		return new ArrayList<Message>(filter);
	}

	@Override
	public List<Message> getHistoryMessage(String curUser, String selUser) {

		List<Message> messages = new ArrayList<Message>();
		PreparedStatement ps = null;
		String sql_getMessages = "select * from messages where ((messages.sender = '"
				+ curUser
				+ "' or messages.sender in (select room.toUser from room where ((room.fromUser = '"
				+ curUser
				+ "' or room.toUser = '"
				+ curUser
				+ "') and room.toUser != '"
				+ curUser
				+ "') )) and (messages.idRoom in (select room.id from room where (room.fromUser = '"
				+ curUser
				+ "' and room.toUser = '"
				+ selUser
				+ "') or (room.fromUser = '"
				+ selUser
				+ "' and room.toUser = '" + curUser + "'))))";
		try {
			ps = getConnection().prepareStatement(sql_getMessages);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		try {
			while (rs.next()) {
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setCr_date(rs.getTimestamp("cr_date"));
				message.setIdRoom(rs.getInt("idRoom"));
				message.setText(rs.getString("msg"));
				message.setSender(rs.getString("sender"));
				messages.add(message);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		Set<Message> filter = new HashSet<Message>();
		filter.addAll(messages);

		return new ArrayList<Message>(filter);
	}
}
