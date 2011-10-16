package main.data;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;


public class BuildingItems {
	private static ArrayList<MyOverlayItem> buildingItems;
	private String[][] buildingData = {{"37721274", "-122476868", "Administration Building", "Administration Building Desc" },
									   {"37721435", "-122477453", "SFSU Library!", "SFSU Library! Desc" },
									   {"37721410", "-122480020", "McKenna Theatre", "McKenna Theatre Desc" },
									   {"37722021", "-122478550", "Ceasar Chavez", "Ceasar Chavez Desc" },
									   {"37722091", "-122476868", "Business", "Business Desc" }
												};
	
	public BuildingItems(){
		buildingItems = new ArrayList<MyOverlayItem>();
		
		for(int i=0;i<buildingData[0].length;i++)
			buildingItems.add(new MyOverlayItem(new GeoPoint
					(Integer.parseInt(buildingData[i][0]), Integer.parseInt(buildingData[i][1])), 
					buildingData[i][2], buildingData[i][3],i));
	}

	/**
	 * @param buildingItems the buildingItems to set
	 */
	public static void setBuildingItems(ArrayList<MyOverlayItem> buildingItems) {
		BuildingItems.buildingItems = buildingItems;
	}

	/**
	 * @return the buildingItems
	 */
	public static ArrayList<MyOverlayItem> getBuildingItems() {
		return buildingItems;
	}
	
	public static MyOverlayItem getBuildingItem(int index) {
		return buildingItems.get(index);
	}
}
