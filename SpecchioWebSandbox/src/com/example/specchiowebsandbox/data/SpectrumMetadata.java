package com.example.specchiowebsandbox.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;

import ch.specchio.types.MetaDatatype;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;

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
	public String sensor_distance;
	public String measurement_unit;
	public String illum_source;
	public String landcover;
	public Long beam_geometry;
	public String sampling_env;
	
	
	
	public SpectrumMetadata(Spectrum spec){
		spectrum = spec;

	}
	
	public void fillMetadata(){
		
//		String test = spectrum.getMetadataAsString("Longitude");
		
		
		
		for(MetaParameter mp : spectrum.getMetadata().getEntries())
		{
			if (mp.getAttributeName().equalsIgnoreCase("Acquisition Time") && mp.getValue() != null){
				capture_date = (Date)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Loading Time")&& mp.getValue() != null){
				loading_date = (Date)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Longitude") && mp.getValue() != null){
				longitude = (Double)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Latitude") && mp.getValue() != null){
				latitude = (Double)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Altitude") && mp.getValue() != null){
				altitude = (Double)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Location") && mp.getValue() != null){
				location = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Sensor zenith") && mp.getValue() != null){
				sensor_zenith = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Sensor azimuth") && mp.getValue() != null){
				sensor_azimuth = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Illumination azimuth") && mp.getValue() != null){
				illumination_azimuth = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Illumination zenith") && mp.getValue() != null){
				illumination_zenith = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Sensor distance") && mp.getValue() != null){
				sensor_distance = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Measurement unit") && mp.getValue() != null){
				measurement_unit = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Illumination source") && mp.getValue() != null){
				illum_source = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Landcover") && mp.getValue() != null){
				landcover = (String)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Beam Geometry") && mp.getValue() != null){
				beam_geometry = (Long)mp.getValue();
			}else if(mp.getAttributeName().equalsIgnoreCase("Sampling environment") && mp.getValue() != null){
				sampling_env = (String)mp.getValue();
			}			
			
		}
			
	
		
//		capture_date = spectrum.getCaptureDate().get_value();
//		loading_date = spectrum.getLoadingDate().get_value();
//		longitude = spectrum.getLongitude().get_value();
//		latitude = spectrum.getLatitude().get_value();
//		altitude = spectrum.getAltitude().get_value();
//		location = spectrum.getLocationName().get_value();
//		sensor_zenith = spectrum.getSensorZenith().get_value();
//		sensor_azimuth = spectrum.getSensorAzimuth().get_value();
//		illumination_azimuth = spectrum.getIlluminationAzimuth().get_value();
//		illumination_zenith = spectrum.getIlluminationZenith().get_value();
//		sensor_distance = spectrum.getSensorDistance().get_value();
//		measurement_unit = spectrum.getMeasurementUnit().get_value();
//		illum_source = spectrum.getIlluminationSource().get_value();
//		landcover = spectrum.getLandcover().get_value();
//		beam_geometry = spectrum.getMeasurementType().get_value();
//		sampling_env = spectrum.getSamplingEnvironment().get_value();
		
		
//		ArrayList<MetaDatatype> md = spectrum.md_attribute_list;
//		
//		
//		ListIterator<MetaDatatype> li = md.listIterator();
//		
//		while(li.hasNext())
//		{
//			MetaDatatype mp = li.next();
//			
//			if (mp.name.equalsIgnoreCase("Capture date") && mp.value != null){
//				capture_date = (Date)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Loading date")&& mp.value != null){
//				loading_date = (Date)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Longitude") && mp.value != null){
//				longitude = (Double)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Latitude") && mp.value != null){
//				latitude = (Double)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Altitude") && mp.value != null){
//				altitude = (Double)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Location") && mp.value != null){
//				location = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Sensor zenith") && mp.value != null){
//				sensor_zenith = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Sensor azimuth") && mp.value != null){
//				sensor_azimuth = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Illumination azimuth") && mp.value != null){
//				illumination_azimuth = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Illumination zenith") && mp.value != null){
//				illumination_zenith = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Sensor distance") && mp.value != null){
//				sensor_distance = (Double)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Measurement unit") && mp.value != null){
//				measurement_unit = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Illumination source") && mp.value != null){
//				illum_source = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Landcover") && mp.value != null){
//				landcover = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Beam Geometry") && mp.value != null){
//				beam_geometry = (String)mp.value;
//			}else if(mp.name.equalsIgnoreCase("Sampling environment") && mp.value != null){
//				sampling_env = (String)mp.value;
//			}
//			
//				
//			
//		}
		
	}

}
