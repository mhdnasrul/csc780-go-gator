package main.data;

import java.util.ArrayList;

import main.common.Utils;
import main.overlay.MyOverlayItem;

import android.location.Location;

import com.google.android.maps.GeoPoint;


public class BuildingItems {
	private static ArrayList<MyOverlayItem> buildingItems;
	private String[][] buildingData = { {"37721270", "-122476860", "Administration Building", "Administration Building Desc" },
//										{"37722734", "-122478966", "Burk Hall", "Burk Hall Desc" },
										{"37721730", "-122476920", "Business", "Business Desc" },
										{"37722070", "-122478570", "Ceasar Chavez", "Ceasar Chavez Desc" },
//										{"37724104", "-122478493", "Cox Stadium", "Cox Stadium Desc" },
										{"37721840", "-122479790","Creative Arts", "Creative Arts Desc" },
//										{"37723341", "-122479271", "Ethnic Studies & Psychology", "Ethnic Studies & Psychology Desc"},
										{"37722120", "-122479940", "Fine Arts", "Fine Arts Desc" },
//										{"37723411", "-122479016", "Gymnasium", "Gymnasium Desc" },
//										{"37723521", "-122475809", "Hensill Hall", "Hensill Hall Desc" },
										{"37721590", "-122476240", "HSS", "HSS Desc" },
//										{"37722180", "-12248091","Humanities", "Humanities Desc" },
										{"37721560", "-12247793", "J.Paul Leonard Library", "J.Paul Leonard Library Desc" },
//										{"37726353", "-1224825522", "Library Annex", "Library Annex Desc" },
//										{"37725679", "-122481245", "Mail Services", "Mail Services Desc" },
//										{"3772409", "-122480803", "Parking Garage", "Parking Garage Desc" },
//										{"37723169", "-122478483", "Physical Therapy", "Physical Therapy Desc" },
//										{"37726003", "-122481962","Police Department", "Police Department Desc" },
										{"37723150", "-122477030", "Science Building", "Science Building Desc" },
										{"37722510", "-122478230", "SFSU Bookstore", "SFSU Bookstore Desc" },
//										{"37723355", "-122479757", "Student Health Center", "Student Health Center Desc" },
//										{"37723353", "-122480532", "Student Services", "Student Services Desc" },
//										{"37726548", "-122483692", "Tennis Courts", "Tennis Courts Desc" },
//										{"37723765","-122484051","The Towers", "The Towers Desc" },
//										{"37722991", "-122480561", "The Village", "The Village Desc" },
										{"37723482", "-122476820", "Thornton Hall", "Thornton Hall Desc" },
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
	
	public static MyOverlayItem getNearestBuildingItem(Location loc) {
		
		double[] dist = new double[buildingItems.size()];
		int i=0;
		
		for(MyOverlayItem item: buildingItems){
			dist[i] = loc.distanceTo(Utils.geoToLoc(item.getPoint()));
//			dist[i] /= 10000;
//			Location loc2 = Utils.geoToLoc(item.getPoint());
//			dist[i] = Utils.distVincenty(loc.getLatitude(), loc.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
			dist[i] /= 1E6;
//			System.out.println(loc.getLatitude()+" "+ loc.getLongitude()+" "+ loc2.getLatitude()+" "+loc2.getLongitude()+" "+dist[i]+" "+item.getTitle());
			i++;
		}
		
		return buildingItems.get(Utils.smallestIndex(dist));
	}
}
