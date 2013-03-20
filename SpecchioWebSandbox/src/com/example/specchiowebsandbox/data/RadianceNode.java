package com.example.specchiowebsandbox.data;

import java.util.ArrayList;

public class RadianceNode {
	
	private String name = "Radiances";
	private int id;
	private ArrayList<SpectrumData> specDat;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<SpectrumData> getSpecDat() {
		return specDat;
	}
	public void setSpecDat(ArrayList<SpectrumData> specDat) {
		this.specDat = specDat;
	}
	
	@Override
    public String toString() {
        return getName();
    }

}
