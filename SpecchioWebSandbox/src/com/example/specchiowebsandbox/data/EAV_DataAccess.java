package com.example.specchiowebsandbox.data;

import java.util.ArrayList;

import eav_db.*;

public class EAV_DataAccess {
	
	public void getEAVdata(int spec_id){
		EAVDBServices eav_db_service = EAVDBServices.getInstance();
		
		eav_db_service.set_primary_x_eav_tablename("spectrum_x_eav","spectrum_id", "spectrum");
		
		ArrayList<Integer> spec_ids = new ArrayList<Integer>();
		spec_ids.add(spec_id);
		ArrayList<Object> elements = eav_db_service.get_distinct_list_of_metaparameter_vals(spec_ids, "Lin@400nm", "double_val");
		
		
		
		
	}

}
