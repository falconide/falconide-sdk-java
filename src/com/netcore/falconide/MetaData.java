package com.netcore.falconide;

import java.util.ArrayList;
import java.util.HashMap;

public class MetaData implements java.io.Serializable {
	private HashMap<String, String> attributes;
	private String XAPIHeader;
	private ArrayList<String> recipientCcs;
	private ArrayList<String> recipientCCsXapis;
	private HashMap<String, Table> trigData;

	public HashMap<String, String> getSubstitutes() {
		return attributes;
	}

	public void addCC(String ccEmail, String ccxApi) {
		if (recipientCcs == null) {
			recipientCcs = new ArrayList<String>();
			recipientCCsXapis = new ArrayList<String>();
		}
		if (ccEmail != null)
			recipientCcs.add(ccEmail);
		if (ccxApi != null)
			recipientCCsXapis.add(ccxApi);
	}

	public void addSubstitue(String name, String value) {
		if (attributes == null) {
			attributes = new HashMap<String, String>();
		}
		attributes.put(name, value);
	}

	public String getXAPIHeader() {
		return XAPIHeader;
	}

	public void setXAPIHeader(String xAPIHeader) {
		XAPIHeader = xAPIHeader;
	}

	public ArrayList<String> getRecipientCcs() {
		return recipientCcs;
	}

	public ArrayList<String> getRecipientCCsXapis() {
		return recipientCCsXapis;
	}

	public HashMap<String, Table> getTrigData() {
		return trigData;
	}

	public Table createTable(String varName) {
		Table table = new Table();
		if (trigData == null) {
			trigData = new HashMap<String, Table>();
		}
		trigData.put(varName, table);
		return table;

	}

	public void clearAllTables() {
		if (trigData != null) {
			trigData.clear();
		}
	}

	public void clearTable(String varName) {
		if (trigData != null) {
			trigData.put(varName, null);
		}
	}
}
