package com.example.specchiowebsandbox.data;

import java.sql.SQLException;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class SpectrumDetailContainer {
	
	private JDBCConnectionPool connectionPool = null;
	
	
	private SQLContainer spectrumDetailsContainer = null;
	
	
	
	public SpectrumDetailContainer(SpecchiowebsandboxApplication app, int spec_id) {
		connectionPool = app.getDbHelp().getConnection();
		initSpectrumDetailsContainer(spec_id);
	}
	
	
	
	private void initSpectrumDetailsContainer(int spec_id){
		try{
			FreeformQuery query = new FreeformQuery("SELECT spectrum_id, illumination_source_id, measurement_unit_id, date, file_name, sensor_id, campaign_id, loading_date, user_id FROM spectrum WHERE spectrum_id=" + spec_id , connectionPool, "spectrum_id");
			
			spectrumDetailsContainer = new SQLContainer(query);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public SQLContainer getSpectrumDetailsContainer(){
		return spectrumDetailsContainer;
	}

}
