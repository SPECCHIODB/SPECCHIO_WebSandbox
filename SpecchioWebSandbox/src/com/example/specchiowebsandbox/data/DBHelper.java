package com.example.specchiowebsandbox.data;

import java.sql.SQLException;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class DBHelper {
	private JDBCConnectionPool connectionPool = null;
	
	private SQLContainer campaignContainer = null;
	private SQLContainer spectrumDetailsContainer = null;
	private SpecchiowebsandboxApplication app = null;
	
	public DBHelper(SpecchiowebsandboxApplication application) {
		app = application;
		initConnectionPool();
		initContainers();
	}
	
	private void initConnectionPool() {
		try{
			connectionPool = new SimpleJDBCConnectionPool(
					"com.mysql.jdbc.Driver",
					"jdbc:mysql://specchio-pub.geo.uzh.ch:3306/cost_es0903","sdb_admin", "5p3cch10", 2, 5);
//					"jdbc:mysql://db.specchio.ch:4406/specchio","DKueken","mEdezA", 2, 5);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	private void initContainers() {
		try{
			TableQuery q1 = new TableQuery("campaign", connectionPool);
			q1.setVersionColumn("VERSION");
			campaignContainer = new SQLContainer(q1);
			
//			FreeformQuery query = new FreeformQuery("SELECT * FROM SPECTRUM WHERE spectrum_id=" + app.)
			
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void initSpectrumDetailsContainer(int spec_id){
		try{
			FreeformQuery query = new FreeformQuery("SELECT * FROM spectrum WHERE spectrum_id=" + spec_id , connectionPool, "spectrum_id");
			
			spectrumDetailsContainer = new SQLContainer(query);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public SQLContainer getCampaignContainer(){
		return campaignContainer;
	}
	
	public SQLContainer getSpectrumDetailsContainer(){
		return spectrumDetailsContainer;
	}
	
	public JDBCConnectionPool getConnection(){
		return connectionPool;
	}

}
