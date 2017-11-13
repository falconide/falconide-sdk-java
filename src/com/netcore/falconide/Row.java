package com.netcore.falconide;

import java.util.HashMap;

public class Row extends HashMap<String, String> {
	public void addColumnData(String colName, String value) {
		put(colName, value);
	}
	public void clearColumnData(String colName){
		put(colName,null);
	}
	public void clearAllData(){
		clear();
	}
}
