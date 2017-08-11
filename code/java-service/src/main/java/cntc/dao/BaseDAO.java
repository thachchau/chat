package cntc.dao;

import java.sql.Connection;

public class BaseDAO implements IBaseDAO{
	Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
}
