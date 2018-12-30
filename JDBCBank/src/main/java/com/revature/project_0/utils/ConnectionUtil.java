package com.revature.project_0.utils;
import java.io.FileInputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	private static Connection connectionInstance = null;
	private static Logger Log = LogManager.getLogger(ConnectionUtil.class);
	
	private ConnectionUtil() {
		
	}
	
	public static Connection getConnection() {
		if(ConnectionUtil.connectionInstance != null) {
			Log.traceEntry("Connection already established");
			return ConnectionUtil.connectionInstance;
		}
		
		Log.traceEntry("Establishing connection");
		
		InputStream in = null;
		
		try {
			// load information from properties file
            Properties props = new Properties();
            Log.info("Finding property list");
            in = new FileInputStream("src\\main\\resources\\connection.properties");
            Log.info("Storing property list");
            props.load(in);
            Log.info("Registering driver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = null;
            Log.info("Grabbing endpoint from property list");
            String endpoint = props.getProperty("jdbc.url");
            Log.info("Grabbing username from property list");
            String username = props.getProperty("jdbc.username");
            Log.info("Grabbing password from property list");
            String password = props.getProperty("jdbc.password");
            Log.info("Generating connection using properties");
            con = DriverManager.getConnection(endpoint, username, password);
            
            Log.traceExit("Connection established to " + con.getSchema() + " schema");
            return con;
			
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
}

