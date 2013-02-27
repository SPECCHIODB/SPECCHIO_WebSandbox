package com.example.specchiowebsandbox.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	static final String URL = "jdbc:mysql://specchio-pub.geo.uzh.ch:3306/cost_es0903";
	static final String USER = "sdb_admin";
	static final String PASSWORD = "5p3cch10";
	static final String DRIVER = "com.mysql.jdbc.Driver";

	public Connection connect2db() throws Exception{
		try{
			Class.forName(DRIVER);
			connect = 	DriverManager.getConnection(URL, USER, PASSWORD);
			
			
		}catch(Exception e) {
		      throw e;
		}
		
		return connect;
		
		
	}
	
	private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {
	    	e.printStackTrace();

	    }
	  }

}
