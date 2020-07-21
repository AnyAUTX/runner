package com.AnyAUT;

import java.awt.Point;
import java.util.HashMap;

public class arrayStructure {
	private HashMap<Point, String> map = new HashMap<Point, String>();
	private int maxRow=0, maxCol = 0;
	
	public arrayStructure() {
		
	}
	
	public void add(int row, int col, String str){
		map.put(new Point(row, col), str);
		maxRow = Math.max(row, maxRow);
		maxCol = Math.max(col, maxCol);
		
 	}
	
	public String[][] toArray() {
		String[][] result = new String[maxRow+1][maxCol+1];
		
		for (int i = 0; i <=maxRow; i++){
			for (int j = 0; j <=maxCol; j++) {
				Point p = new Point(i,j);
				result[i][j] = map.containsKey(p) ? map.get(p) : "" ;
			}
		}
		return result;
	}
}
