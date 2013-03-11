package com.example.specchiowebsandbox.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import specchio.MetaDatatype;
import specchio.Spectrum;

public class SpectrumMetadata {
	
	private Spectrum spectrum;
	
	public Date capture_date;
	public Date loading_date;
	public double longitude;
	public double latitude;
	public double altitude;
	public String location;
	public String sensor_zenith;
	public String sensor_azimuth;
	public String illumination_zenith;
	public String illumination_azimuth;
	public double sensor_distance;
	public String measurement_unit;
	public String illum_source;
	public String landcover;
	public String beam_geometry;
	public String sampling_env;
	
	
	
	public SpectrumMetadata(Spectrum spec){
		spectrum = spec;

	}
	
	public void fillMetadata(){
		
		ArrayList<MetaDatatype> md = spectrum.md_attribute_list;
		
		
		ListIterator<MetaDatatype> li = md.listIterator();
		
		while(li.hasNext())
		{
			MetaDatatype mp = li.next();
			
			if (mp.name.equalsIgnoreCase("Capture date") && mp.value != null){
				capture_date = (Date)mp.value;
			}else if(mp.name.equalsIgnoreCase("Loading date")&& mp.value != null){
				loading_date = (Date)mp.value;
			}else if(mp.name.equalsIgnoreCase("Longitude") && mp.value != null){
				longitude = (Double)mp.value;
			}else if(mp.name.equalsIgnoreCase("Latitude") && mp.value != null){
				latitude = (Double)mp.value;
			}else if(mp.name.equalsIgnoreCase("Altitude") && mp.value != null){
				altitude = (Double)mp.value;
			}else if(mp.name.equalsIgnoreCase("Location") && mp.value != null){
				location = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Sensor zenith") && mp.value != null){
				sensor_zenith = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Sensor azimuth") && mp.value != null){
				sensor_azimuth = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Illumination azimuth") && mp.value != null){
				illumination_azimuth = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Illumination zenith") && mp.value != null){
				illumination_zenith = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Sensor distance") && mp.value != null){
				sensor_distance = (Double)mp.value;
			}else if(mp.name.equalsIgnoreCase("Measurement unit") && mp.value != null){
				measurement_unit = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Illumination source") && mp.value != null){
				illum_source = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Landcover") && mp.value != null){
				landcover = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Beam Geometry") && mp.value != null){
				beam_geometry = (String)mp.value;
			}else if(mp.name.equalsIgnoreCase("Sampling environment") && mp.value != null){
				sampling_env = (String)mp.value;
			}
			
				
			
		}
		
	}

}
