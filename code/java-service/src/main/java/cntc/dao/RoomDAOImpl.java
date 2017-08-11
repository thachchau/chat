package cntc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomDAOImpl extends BaseDAO implements IRoomDAO {

	@Override
	public boolean isSaveRoom(String nameRoom, String senderName,
			String recieverName) {
		try {
			Statement sts = getConnection().createStatement();
			String sql_insert_room = "INSERT INTO room(roomName, fromUser, toUser) "
					+ "VALUES('"
					+ nameRoom
					+ "', "
					+ "'"
					+ senderName
					+ "', "
					+ "'" + recieverName + "')";
			sts.executeUpdate(sql_insert_room);
			System.out.println("Insert room success");
		} catch (SQLException e) {
			System.out.println("Insert room fail");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<String> getContactUsername(String username) {
		List<String> contactUsers = new ArrayList<String>();
		PreparedStatement ps = null;
		String sql_getContactUser = "select room.toUser from room where ((room.fromUser = '"
				+ username
				+ "' or room.toUser = '"
				+ username
				+ "') and room.toUser != '"
				+ username
				+ "') order by room.cr_date";
		try {
			ps = getConnection().prepareStatement(sql_getContactUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return contactUsers;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return contactUsers;
		}
		try {
			while(rs.next()){
				String contactUser = new String();
				contactUser = rs.getString("toUser");
				contactUsers.add(contactUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return contactUsers;
		}
		Set<String> fillter = new HashSet<String>();
		fillter.addAll(contactUsers);
		return new ArrayList<String>(fillter);
	}

}
