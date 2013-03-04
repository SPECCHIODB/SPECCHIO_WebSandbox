package com.example.specchiowebsandbox.data;

public class EAV_Attribute {
	
	private String attr_name;
	private String attr_field;
	private Object attr_value;
	
	public EAV_Attribute(String name, String field, Object val){
		attr_name = name;
		attr_field = field;
		attr_value = val;
	}
	
	
	public String getAttrName(){
		return attr_name;
	}
	
	public void setAttrName(String name){
		attr_name = name;
	}
	
	public String getAttrField(){
		return attr_field;
	}
	
	public void setAttrField(String field){
		attr_field = field;
	}
	
	public Object getAttrValue(){
		return attr_value;
	}
	
	public void setAttrValue(Object val){
		attr_value = val;
	}
	

}
