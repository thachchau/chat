package cntc.bo;

import java.sql.Connection;
import java.sql.SQLException;

import cntc.dao.DatabaseConnection;

public class BaseBO {
	public Connection getConnection(){
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = null;
		try {
			connection = databaseConnection.DBConnect();
		} catch (SQLException e1) {
		}
		return connection;
	}
}
