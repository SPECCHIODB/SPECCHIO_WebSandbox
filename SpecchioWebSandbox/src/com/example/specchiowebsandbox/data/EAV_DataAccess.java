package com.example.specchiowebsandbox.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.types.ConflictTable;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;

import com.example.specchiowebsandbox.SpecchiowebsandboxApplication;
import com.vaadin.data.util.BeanItemContainer;


@SuppressWarnings("serial")
public class EAV_DataAccess extends BeanItemContainer<EAV_Attribute> implements Serializable{
	
//	private ArrayList<Object> entries_val;
//	private ArrayList<String> entries_names;
	private ArrayList<EAV_Attribute> entries;
	
	private static final String ATTR_FIELD = "attrField";
	private static final String ATTR_NAME = "attrName";
	private static final String ATTR_VALUE = "attrValue";
	
	public EAV_DataAccess() throws InstantiationException, IllegalAccessException {
		super(EAV_Attribute.class);
	}
	
	
	
	public static final Object[] NATURAL_COL_ORDER = new Object[] {ATTR_NAME, ATTR_VALUE, ATTR_FIELD};
	
	public static final String[] COL_HEADERS = new String[] {"Attribute Name", "Value", "Field"};
	
	public static BeanItemContainer<EAV_Attribute> createEAVDataContainer(Spectrum s, ConflictTable conflicts, SpecchiowebsandboxApplication app){
		
		
//		EAVDBServices eav_db_service = EAVDBServices.getInstance();
//		
//		eav_db_service.set_primary_x_eav_tablename("spectrum_x_eav","spectrum_id", "spectrum");
		
//		ArrayList<Integer> spec_ids = new ArrayList<Integer>();
//		spec_ids.add(spec_id);
////		ArrayList<Object> elements = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, "Lin@400nm", "double_val");
		
//		Attributes attributes = Attributes.getInstance();
//		ArrayList<attribute> attr = attributes.get_attributes("system");
		
//		specchio_client.get
		
		BeanItemContainer<EAV_Attribute> container = null;
		
//		try{
			container = new BeanItemContainer<EAV_Attribute>(EAV_Attribute.class);
			
				
				for(EAV_Attribute attr : app.eav_attributes){
					
					container.addBean(attr);		
				}
				
				
				
			
			
			
			
//		for(int i = 0; i < attr.size(); i++){
//			if(attr.get(i).get_default_storage_field() != null){
//		
//			
//			
//			String name = attr.get(i).getName();
//			String field = attr.get(i).get_default_storage_field();
//			Object val = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, name, field);
//			
//			EAV_Attribute attribute = new EAV_Attribute();
//			attribute.setAttrName(name);
//			attribute.setAttrValue(val);
//			attribute.setAttrField(field);
//			
//			container.addBean(attribute);
//		}
//		}
//		} catch (InstantiationException e){
//			e.printStackTrace();
//		} catch (IllegalAccessException e){
//			e.printStackTrace();
//		}
		
		return container;
		
	}
	
//	public ArrayList<EAV_Attribute> getEAVdata(int spec_id){
//		EAVDBServices eav_db_service = EAVDBServices.getInstance();
//		
//		eav_db_service.set_primary_x_eav_tablename("spectrum_x_eav","spectrum_id", "spectrum");
//		
//		ArrayList<Integer> spec_ids = new ArrayList<Integer>();
//		spec_ids.add(spec_id);
////		ArrayList<Object> elements = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, "Lin@400nm", "double_val");
//		
//		Attributes attributes = Attributes.getInstance();
//		ArrayList<attribute> attr = attributes.get_attributes("system");
//		
//		entries = new ArrayList<EAV_Attribute>();
//		
////		entries_val = new ArrayList<Object>();
////		entries_names = new ArrayList<String>();
//		
//	
//		
//		for(int i = 0; i < attr.size(); i++){
//			if(attr.get(i).get_default_storage_field() != null){
//				
//				String name = attr.get(i).getName();
//				String field = attr.get(i).get_default_storage_field();
//				Object val = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, name, field);
//				
//				EAV_Attribute attribute = new EAV_Attribute(name, field, val);
//				
//				entries.add(attribute);
//			
////				entries_names.add(attr.get(i).getName());
////				entries_val.add(eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, attr.get(i).getName(), attr.get(i).get_default_storage_field()));
//			}
//		}
//		
//		return entries;
//		
//		
//		
//		
//	}

}
