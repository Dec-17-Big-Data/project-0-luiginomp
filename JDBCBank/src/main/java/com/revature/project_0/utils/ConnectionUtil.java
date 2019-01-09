package com.revature.project_0.utils;
import java.io.FileInputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	private static Connection connectionInstance = null;
	private static Logger Log = LogManager.getLogger(ConnectionUtil.class);
	
	private ConnectionUtil() {
		
	}
	
	public static Connection getConnection() {
		if(ConnectionUtil.connectionInstance != null) {
			return ConnectionUtil.connectionInstance;
		}
		InputStream in = null;
		try {
			// load information from properties file
            Properties props = new Properties();
            in = new FileInputStream("src\\main\\resources\\connection.properties");
            props.load(in);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String endpoint = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            connectionInstance = DriverManager.getConnection(endpoint, username, password);
            Log.info("Connection established to " + connectionInstance.getSchema() + " schema");
            return connectionInstance;
			
		}catch (Exception e) {
			Log.error("Unable to connect to database");
		}finally {
			try {
				in.close();
			}catch (IOException e) {
				
			}
		}
		return null;
	}
	
	public static Boolean tryToClose(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
				return true;
			}catch (SQLException e) {
				Log.error("SQL Exception Occurred: ", e);
			}
		}
		return false;
	}
}

