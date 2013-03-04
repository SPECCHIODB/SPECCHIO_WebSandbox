package com.example.specchiowebsandbox.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EAV_Attribute implements Serializable{
	
	private String attrName = "";
	private String attrField = "";
	private Object attrValue = null;
	
	public EAV_Attribute(){
		
	}
	
	public EAV_Attribute(String name, String field, Object val){
		attrName = name;
		attrField = field;
		attrValue = val;
	}
	
	
	public String getAttrName(){
		return attrName;
	}
	
	public void setAttrName(String name){
		this.attrName = name;
	}
	
	public String getAttrField(){
		return attrField;
	}
	
	public void setAttrField(String field){
		this.attrField = field;
	}
	
	public Object getAttrValue(){
		return attrValue;
	}
	
	public void setAttrValue(Object val){
		this.attrValue = val;
	}
	

}
