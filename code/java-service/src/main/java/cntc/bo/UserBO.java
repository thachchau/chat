package cntc.bo;

import java.util.ArrayList;

import cntc.dao.IUserDAO;
import cntc.dao.UserDAOImpl;
import cntc.dto.Avatar;
import cntc.dto.User;

public class UserBO extends BaseBO {
	IUserDAO userDAO;
	public UserBO(){
		userDAO = new UserDAOImpl();
	}
	public User findUser(String username, String password) {
		userDAO.setConnection(getConnection());
		return userDAO.findUser(username, password);
	}

	public boolean isInsertUser(String email, String username, String password) {
		userDAO.setConnection(getConnection());
		return userDAO.isInsertUser(email, username, password);
	}

	public boolean changeStatusUser(String username, boolean status){
		userDAO.setConnection(getConnection());
		return userDAO.changeStatusUser(username, status);
	}
	
	public ArrayList<User> getAllUserOnline(){
		userDAO.setConnection(getConnection());
		return userDAO.getAllUserOnline();
	}
	
	public ArrayList<Avatar> getAvatars(){
		userDAO.setConnection(getConnection());
		return userDAO.getAvatars();
	}
	
	public void updateAvatar(String username, String image){
		userDAO.setConnection(getConnection());
		userDAO.updateAvatar(username, image);
		
	}
	public Avatar getAvatar(String username){
		userDAO.setConnection(getConnection());
		return userDAO.getAvatar(username);
	}
}
