package com.example.specchiowebsandbox.data;

import java.util.ArrayList;

import eav_db.*;
import eav_db.Attributes.attribute;

public class EAV_DataAccess {
	
//	private ArrayList<Object> entries_val;
//	private ArrayList<String> entries_names;
	private ArrayList<EAV_Attribute> entries;
	
	public ArrayList<EAV_Attribute> getEAVdata(int spec_id){
		EAVDBServices eav_db_service = EAVDBServices.getInstance();
		
		eav_db_service.set_primary_x_eav_tablename("spectrum_x_eav","spectrum_id", "spectrum");
		
		ArrayList<Integer> spec_ids = new ArrayList<Integer>();
		spec_ids.add(spec_id);
//		ArrayList<Object> elements = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, "Lin@400nm", "double_val");
		
		Attributes attributes = Attributes.getInstance();
		ArrayList<attribute> attr = attributes.get_attributes("system");
		
		entries = new ArrayList<EAV_Attribute>();
		
//		entries_val = new ArrayList<Object>();
//		entries_names = new ArrayList<String>();
		
	
		
		for(int i = 0; i < attr.size(); i++){
			if(attr.get(i).get_default_storage_field() != null){
				
				String name = attr.get(i).getName();
				String field = attr.get(i).get_default_storage_field();
				Object val = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, name, field);
				
				EAV_Attribute attribute = new EAV_Attribute(name, field, val);
				
				entries.add(attribute);
			
//				entries_names.add(attr.get(i).getName());
//				entries_val.add(eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, attr.get(i).getName(), attr.get(i).get_default_storage_field()));
			}
		}
		
		return entries;
		
		
		
		
	}

}
