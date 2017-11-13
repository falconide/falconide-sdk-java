package com.netcore.falconide;

import java.util.ArrayList;

public class Table extends ArrayList<Row> {
	public void addRow(Row row) {
		add(row);
	}

	public void clearAllRows() {
		clear();
	}
}
