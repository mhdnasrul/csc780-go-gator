package main.data;

import gatorDB.DataBaseHelper;

import java.util.ArrayList;
import java.util.Collections;

import main.common.Utils;
import main.overlay.MyOverlayItem;

import android.location.Location;


public class BuildingItems {
	private static ArrayList<MyOverlayItem> buildingItems;
	
	public BuildingItems(){
		buildingItems = DataBaseHelper.queryDB("type = 'Building' AND desc!='todo'");
		Collections.sort(buildingItems);
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
			//dist[i] = loc.distanceTo(Utils.geoToLoc(item.getPoint()));
			dist[i] = Utils.gps2m(loc, Utils.geoToLoc(item.getPoint()));
//			dist[i] /= 10000;
//			Location loc2 = Utils.geoToLoc(item.getPoint());
//			dist[i] = Utils.distVincenty(loc.getLatitude(), loc.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
//			dist[i] /= 1E6;
//			System.out.println(loc.getLatitude()+" "+ loc.getLongitude()+" "+ loc2.getLatitude()+" "+loc2.getLongitude()+" "+dist[i]+" "+item.getTitle());
			System.out.println(item.getTitle()+"="+dist[i]);
			i++;
		}
		
		return buildingItems.get(Utils.smallestIndex(dist));
	}
}
