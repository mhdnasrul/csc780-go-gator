package main.overlay;

import java.util.ArrayList;

import main.common.Utils;

import com.google.android.maps.GeoPoint;

import android.location.Location;

public class MyMappedPath {
	private static ArrayList<Location> mypath;

	/**
	 * @param mypath the mypath to set
	 */
	public static void setMypath(ArrayList<Location> mypath) {
		MyMappedPath.mypath = mypath;
	}

	/**
	 * @return the mypath
	 */
	public static ArrayList<Location> getMypath() {
		return mypath;
	}
	
	public static Location getSourceGeoPoint(){
		return mypath.get(0);
	}
	
	public static Location getDestGeoPoint(){
		return mypath.get(mypath.size()-1);
	}
	
	public static Location getNextGeoPoint(Location locprev){
		int prevIndex = mypath.indexOf(locprev);
		
		if(prevIndex >= (mypath.size()-1))
			return locprev;
		else
			return mypath.get(prevIndex+1);
	}
	
	public static Location getClosestGeoPoint(GeoPoint currgp){
		Location currloc = Utils.geoToLoc(currgp);
		
		float[] dist = new float[mypath.size()];
		int i=0;
		for(Location mypathloc: mypath){
			dist[i++] = currloc.distanceTo(mypathloc);
		}
		
		return mypath.get(Utils.smallestIndex(dist));
	}
	
	//This function is required to make sure, that arrow do not point to last traversed geolocation
	public static Location getNextClosestGeoPoint(GeoPoint currgp){
		Location closestLoc = getClosestGeoPoint(currgp);
		return getNextGeoPoint(getNextGeoPoint(closestLoc));
	}
	
//	//GeoPoint to Location
//	public static Location geoToLoc(GeoPoint gp){
//		Location loc = new Location("");
//		loc.setLatitude(gp.getLatitudeE6());
//		loc.setLongitude(gp.getLongitudeE6());
//		return loc;
//	}
//	
//	public static GeoPoint locToGeo(Location loc) {
//		return (new GeoPoint((int)loc.getLatitude(),(int)loc.getLongitude()));
//	}
//	
//	public static int smallestIndex (float[] array) {
//		float currentValue = array[0]; 
//		int smallestIndex = 0;
//		for (int j=1; j < array.length; j++) {
//			if (array[j] < currentValue) {
//				currentValue = array[j];
//				smallestIndex = j;
//			}
//		}
//		return smallestIndex;
//	}
	
	
}
