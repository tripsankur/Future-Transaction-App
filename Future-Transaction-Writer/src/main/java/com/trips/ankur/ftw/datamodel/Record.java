package com.trips.ankur.ftw.datamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.EqualsAndHashCode;

import java.util.Set;




//=============================================================================
@EqualsAndHashCode
public class Record {
	
	//-------------------------------------------------------------------------
	//---
	//--- Variables
	//---
	//-------------------------------------------------------------------------

	private Map<String, Object> map;
	
	//-------------------------------------------------------------------------
	//---
	//--- Constructor
	//---
	//-------------------------------------------------------------------------

	public Record() {
		this.map = new HashMap<String, Object>();
	}
	
	//-------------------------------------------------------------------------
	
	public Record(Map<String,Object> map) {
		this.map = new HashMap<String, Object>(map);
	}
	
	//-------------------------------------------------------------------------
	//---
	//--- API methods
	//---
	//-------------------------------------------------------------------------
	
	public Collection<String> getNames() { return map.keySet(); }
	
	//-------------------------------------------------------------------------
	
    public Object getValue(String name) { return map.get(name); }
	
	//-------------------------------------------------------------------------
    
    public void setValue(String name , Object value) { 
    	if(value == null)
    		value="";
    	map.put(name, value); }
	
	//-------------------------------------------------------------------------
    
    public Set<Entry<String, Object>> getEntries () { return map.entrySet(); }
    
	//-------------------------------------------------------------------------
    
    public void remove(String name) { map.remove(name); }

	@Override
	public String toString() {
		return "Record [map=" + map + "]";
	}
    
    
	
}
