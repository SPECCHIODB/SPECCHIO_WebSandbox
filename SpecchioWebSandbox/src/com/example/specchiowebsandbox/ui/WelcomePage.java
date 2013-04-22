package com.example.specchiowebsandbox.ui;

import java.io.File;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class WelcomePage extends VerticalLayout{
	
	public WelcomePage(SpecchiowebsandboxApplication app){
		
//		System.out.println(System.getProperty("user.dir"));
//		Embedded e = new Embedded("Test",new FileResource(new File("/usr/local/apache-tomcat-7.0.32/webapps/SpecchioWebSandbox/graphics/SPECCHIO_Icon_Mid_Res_small.jpg"),app));
//		
//		addComponent(e);
//		
		
		setWidth("100%");
		Label welcom_label = new Label("<h1>Welcome to SpecchioWeb Sandbox Application</h1>",Label.CONTENT_XHTML);
		welcom_label.addStyleName("h1 color");
		
		Label beta = new Label("<h2>This application is still in beta phase<h2>",Label.CONTENT_XHTML);
		Label beta2 = new Label("<h2>There are still some open issues!<h2>", Label.CONTENT_XHTML);
		
		
		addComponent(welcom_label);
		setComponentAlignment(welcom_label, Alignment.MIDDLE_CENTER);
		
		addComponent(beta);
		setComponentAlignment(beta,Alignment.MIDDLE_CENTER);
		
		addComponent(beta2);
		setComponentAlignment(beta2,Alignment.MIDDLE_CENTER);
		
		Label text = new Label("<h3>Please select a campaign and a spectrum from the navigation tree on the left<h3>", Label.CONTENT_XHTML);
		Label text2 = new Label("<h3>For the Data Exploration Tab you need to select at least two spectra<h3>", Label.CONTENT_XHTML);
		
		addComponent(text);
		setComponentAlignment(text, Alignment.MIDDLE_CENTER);
		addComponent(text2);
		setComponentAlignment(text2, Alignment.MIDDLE_CENTER);
		
		
		
	}
	
	

}
