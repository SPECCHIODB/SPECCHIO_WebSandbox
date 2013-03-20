package com.example.specchiowebsandbox.data;

import java.io.Serializable;

public class SpectrumData implements Serializable{
	
	private String filename;
	private int spectrum_id;
	private int measurement_unit;
	private long campaign_id;
	private String campaign_name;
	private Object parent;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSpectrum_id() {
		return spectrum_id;
	}
	public void setSpectrum_id(int spectrum_id) {
		this.spectrum_id = spectrum_id;
	}
	
	public int getMeasurement_unit() {
		return measurement_unit;
	}
	public void setMeasurement_unit(int measurement_unit) {
		this.measurement_unit = measurement_unit;
	}
	public long getCampaignId() {
		return campaign_id;
	}
	public void setCampaignId(long campaign_id) {
		this.campaign_id = campaign_id;
	}
	public String getCampaignName() {
		return campaign_name;
	}
	public void setCampaignName(String campaign_name) {
		this.campaign_name = campaign_name;
	}
	public Object getParent() {
		return parent;
	}
	public void setParent(Object parent) {
		this.parent = parent;
	}
	@Override
    public String toString() {
        return getFilename();
    }
	
	
	

}
