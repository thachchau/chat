package cntc.dao;

import java.sql.Connection;

public interface IBaseDAO {
	public Connection getConnection();
	public void setConnection(Connection connection);
}
