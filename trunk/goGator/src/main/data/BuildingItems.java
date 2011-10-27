package main.data;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;


public class BuildingItems {
	private static ArrayList<MyOverlayItem> buildingItems;
	private String[][] buildingData = {{"37721549", "-122476911", "Administration Building", "Administration Building Desc" },
//										{"37722734", "-122478966", "Burk Hall", "Burk Hall Desc" },
										{"37721870", "-122476911", "Business", "Business Desc" },
										{"37722220", "-122478448", "Ceasar Chavez", "Ceasar Chavez Desc" },
//										{"37724104", "-122478493", "Cox Stadium", "Cox Stadium Desc" },
//										{"37721607","-122478998","Creative Arts", "Creative Arts Desc" },
//										{"37723341", "-122479271", "Ethnic Studies & Psychology", "Ethnic Studies & Psychology Desc"},
//										{"37722541", "-122479776", "Fine Arts", "Fine Arts Desc" },
//										{"37723411", "-122479016", "Gymnasium", "Gymnasium Desc" },
//										{"37723521", "-122475809", "Hensill Hall", "Hensill Hall Desc" },
										{"37721710", "-122476259", "HSS", "HSS Desc" },
//										{"37722180","-12248091","Humanities", "Humanities Desc" },
										{"37721838", "-122477890", "J.Paul Leonard Library", "J.Paul Leonard Library Desc" },
//										{"37726353", "-1224825522", "Library Annex", "Library Annex Desc" },
//										{"37725679", "-122481245", "Mail Services", "Mail Services Desc" },
//										{"3772409", "-122480803", "Parking Garage", "Parking Garage Desc" },
//										{"37723169", "-122478483", "Physical Therapy", "Physical Therapy Desc" },
//										{"37726003","-122481962","Police Department", "Police Department Desc" },
										{"37723010", "-122476758", "Science Building", "Science Building Desc" },
										{"37722702", "-122478252", "SFSU Bookstore", "SFSU Bookstore Desc" },
//										{"37723355", "-122479757", "Student Health Center", "Student Health Center Desc" },
//										{"37723353", "-122480532", "Student Services", "Student Services Desc" },
//										{"37726548", "-122483692", "Tennis Courts", "Tennis Courts Desc" },
//										{"37723765","-122484051","The Towers", "The Towers Desc" },
//										{"37722991", "-122480561", "The Village", "The Village Desc" },
//										{"37724007", "-122476876", "Thornton Hall", "Thornton Hall Desc" },
//										{"37723992", "-122476383", "Trailers", "Trailers Desc" }
										};
	
	public BuildingItems(){
		buildingItems = new ArrayList<MyOverlayItem>();
		
		for(int i=0;i<buildingData.length;i++)
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
