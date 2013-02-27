package com.example.specchiowebsandbox.data;

import java.io.Serializable;

public class SpectrumData implements Serializable{
	
	private String filename;
	private int spectrum_id;
	private int measurement_unit;
	
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
	@Override
    public String toString() {
        return getFilename();
    }
	
	
	

}
