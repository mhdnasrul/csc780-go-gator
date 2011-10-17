package main.data;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;


public class DeptItems {
	private static ArrayList<MyOverlayItem> deptItems;
	private String[][] deptData = {{"37721274", "-122476868", "Dept 1", "Dept 1 Desc" },
									   {"37721435", "-122477453", "Dept 2", "Dept 2 Desc" },
									   {"37721410", "-122480020", "Dept 3", "Dept 3 Desc" },
									   {"37722021", "-122478550", "Dept 4", "Dept 4 Desc" },
									   {"37722091", "-122476868", "Dept 5", "Dept 5 Desc" }
												};
	
	public DeptItems(){
		deptItems = new ArrayList<MyOverlayItem>();
		
		for(int i=0;i<deptData.length;i++)
			deptItems.add(new MyOverlayItem(new GeoPoint
								(Integer.parseInt(deptData[i][0]), Integer.parseInt(deptData[i][1])), 
								deptData[i][2], deptData[i][3],i));
	}

	/**
	 * @param buildingItems the buildingItems to set
	 */
	public static void setDeptItems(ArrayList<MyOverlayItem> deptItems) {
		DeptItems.deptItems = deptItems;
	}

	/**
	 * @return the buildingItems
	 */
	public static ArrayList<MyOverlayItem> getDeptItems() {
		return deptItems;
	}
	
	public static MyOverlayItem getDeptItem(int index) {
		return deptItems.get(index);
	}
}
