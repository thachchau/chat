package cntc.dao;

import java.util.ArrayList;

import cntc.dto.Avatar;
import cntc.dto.User;

public interface IUserDAO extends IBaseDAO{
	public User findUser(String username, String password);
	public boolean isInsertUser(String email, String username, String password);
	public boolean changeStatusUser(String username, boolean status);
	public ArrayList<User> getAllUserOnline();
	public ArrayList<Avatar> getAvatars();
	public void updateAvatar(String username, String image);
	public Avatar getAvatar(String username);
}
