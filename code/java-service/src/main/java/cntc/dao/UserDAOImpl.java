package cntc.dao;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cntc.bo.UserBO;
import cntc.dto.Avatar;
import cntc.dto.User;

public class UserDAOImpl extends BaseDAO implements IUserDAO {

	public User findUser(String username, String password) {
		User user = new User();
		PreparedStatement ps = null;
		try {
			ps = getConnection().prepareStatement(
					"select * from users where username = '" + username
							+ "' and pwd = '" + password + "'");
		} catch (SQLException e) {
			System.out.println("Select Fail");
			e.printStackTrace();
			return user;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.print("Query fail\n");
			e.printStackTrace();
			return user;
		}
		try {
			if (rs.next()) {
				user.setiD(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setOnline(rs.getBoolean("isOnline"));
			}
		} catch (SQLException e) {
			System.out.println("Fetch data fail");
			return user;
		}
		return user;
	}

	public boolean isInsertUser(String email, String username, String password) {
		try {
			Statement sts = getConnection().createStatement();
			String sql_insert_user = "INSERT INTO users(email, username, pwd) "
					+ "VALUES('" + email + "', " + "'" + username + "', " + "'"
					+ password + "')";
			sts.executeUpdate(sql_insert_user);
			System.out.println("Insert success");
		} catch (SQLException e) {
			System.out.println("ERROR create statment");
			System.out.println("Insert false, row is existed");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// public boolean getStatus(){
	// boolean status = true;
	//
	// return status;
	// }

	public boolean changeStatusUser(String username, boolean status) {
		try {
			Statement sts = getConnection().createStatement();
			String sqlUpdateUserIsOnline = "UPDATE users SET isOnline = '"
					+ status + "' WHERE username = '" + username + "'";
			int tmp = sts.executeUpdate(sqlUpdateUserIsOnline);
			if (tmp != 0) {
				System.out.println("OK");
				return true;
			} else {
				System.out.println("NOT_OK");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("ERROR: create statment");
			System.out.println("ERROR: updated failed");
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<User> getAllUserOnline() {
		ArrayList<User> userOnlineList = new ArrayList<>();
		PreparedStatement ps = null;
		String sqlGetAllUserOnline = "SELECT * FROM users WHERE isOnline = 1";
		try {
			ps = getConnection().prepareStatement(sqlGetAllUserOnline);
		} catch (SQLException e) {
			System.out.println("Select fail !");
			e.printStackTrace();
			return userOnlineList;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("Query fail !");
			e.printStackTrace();
			return userOnlineList;
		}
		try {
			while (rs.next()) {
				User userOnline = new User();
				userOnline.setiD(rs.getInt("id"));
				userOnline.setEmail(rs.getString("email"));
				userOnline.setUsername(rs.getString("username"));
				userOnline.setOnline(rs.getBoolean("isOnline"));
				userOnlineList.add(userOnline);
			}
		} catch (SQLException e) {
			System.out.println("No row");
			e.printStackTrace();
			return userOnlineList;
		}

		return userOnlineList;
	}

	@Override
	public ArrayList<Avatar> getAvatars() {
		ArrayList<Avatar> listAvatar = new ArrayList<>();
		PreparedStatement ps = null;
		String sqlGetAllAvatars = "SELECT * FROM avatar";
		try {
			ps = getConnection().prepareStatement(sqlGetAllAvatars);
		} catch (SQLException e) {
			System.out.println("NULL - 1");
			return listAvatar;
		}

		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("NULL - 2");
			return listAvatar;
		}
		try {
			while (rs.next()) {
				Avatar avt = new Avatar();
				avt.setId(rs.getInt("id"));
				avt.setUsername(rs.getString("username"));
				avt.setImage(rs.getString("img"));
				listAvatar.add(avt);
			}
		} catch (SQLException e) {
			System.out.println("NULL - 3");
			return listAvatar;
		}
		return listAvatar;
	}

	@Override
	public void updateAvatar(String username, String image) {
		try {
			Statement sts = getConnection().createStatement();
			UserBO userbo = new UserBO();
			ArrayList<Avatar> avatars = userbo.getAvatars();

			int count = 0;
			for (int i = 0; i < avatars.size(); i++) {
				if (avatars.get(i).getUsername().equals(username)) {
					count++;
					break;
				}
			}

			if (count != 0) {
				System.out.println("Update");
				String sqlUpdateImg = "UPDATE avatar SET [img] = (SELECT MyImage.* FROM Openrowset(Bulk 'E:\\uploaded\\Avatar\\"
						+ image
						+ "', Single_Blob) MyImage) WHERE avatar.username = '"
						+ username + "'";
				sts.executeUpdate(sqlUpdateImg);

			} else {
				System.out.println("Insert");
				String sqlInsertImg = "INSERT INTO avatar(username, img) SELECT '"
						+ username + "', BulkColumn FROM OPENROWSET(Bulk N'E:\\uploaded\\Avatar\\"
						+ image + "', SINGLE_BLOB) as avatar";
				sts.executeUpdate(sqlInsertImg);
			}
		} catch (SQLException e) {
			System.out.println("NULL-update-1");
			e.printStackTrace();
		}
	}

	@Override
	public Avatar getAvatar(String username) {
		Avatar avatar = new Avatar();
		byte b[];
		Blob blob;
		PreparedStatement ps = null;
		String sqlGetAvt = "SELECT img FROM avatar WHERE username = '"+ username + "'";
		try {
			ps = getConnection().prepareStatement(sqlGetAvt);
		} catch (SQLException e) {
			System.out.println("NULL - get avtar 1");
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			System.out.println("NULL - get avatar 2");
		}
		try {
			while(rs.next()){
				blob = rs.getBlob("img");
				b = blob.getBytes(1, (int) blob.length());
				avatar.setAvatar(b);
			}
		} 
		catch (SQLException e) {
			System.out.println("NULL - get avatar 3");
		}
		return avatar;
	}

}
