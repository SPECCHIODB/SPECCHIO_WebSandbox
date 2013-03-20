package com.example.specchiowebsandbox.data;

import java.io.Serializable;
import java.util.ArrayList;

public class CampaignNode implements Serializable{
	
	private Object campaign_name;
	private int campaign_id;
	private ArrayList<SpectrumData> spec_data;
	private ArrayList<Object> subdir;
	private Object refl_subdir;
	private Object rad_subdir;
	private ReflectanceNode reflNode;
	private RadianceNode radNode;
	
	public Object getCampaignName() {
		return campaign_name;
	}
	public void setCampaignName(String campaign_name) {
		this.campaign_name = campaign_name;
	}
	public int getCampaignId() {
		return campaign_id;
	}
	public void setCampaignId(int campaign_id) {
		this.campaign_id = campaign_id;
	}
	public ArrayList<SpectrumData> getSpecData() {
		return spec_data;
	}
	public void setSpecData(ArrayList<SpectrumData> spec_data) {
		this.spec_data = spec_data;
	}
	public ArrayList<Object> getSubdir() {
		return subdir;
	}
	public void setSubdir(ArrayList<Object> subdir) {
		this.subdir = subdir;
	}
	public Object getReflSubdir() {
		return refl_subdir;
	}
	public void setReflSubdir(Object refl_subdir) {
		this.refl_subdir = refl_subdir;
	}
	public Object getRadSubdir() {
		return rad_subdir;
	}
	public void setRadSubdir(Object rad_subdir) {
		this.rad_subdir = rad_subdir;
	}
	public ReflectanceNode getReflNode() {
		return reflNode;
	}
	public void setReflNode(ReflectanceNode reflNode) {
		this.reflNode = reflNode;
	}
	public RadianceNode getRadNode() {
		return radNode;
	}
	public void setRadNode(RadianceNode radNode) {
		this.radNode = radNode;
	}

}
