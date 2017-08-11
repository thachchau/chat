package cntc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DatabaseConnection {
	private final String url = "jdbc:jtds:sqlserver://localhost:1433/app";
	private final String username = "sa";
	private final String password = "1234567";
	
	private BasicDataSource dataSource;
	private Connection connection;
	public Connection DBConnect() throws SQLException{
		if (dataSource == null) {
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: throws JDBC Driver ");
				e.printStackTrace();
			}
			dataSource = new BasicDataSource();
			dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setUrl(url);
		}
		if (connection == null) {
			connection = dataSource.getConnection();
		}
		return connection;
	}
	
	public void disconnect() throws SQLException{
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
}
