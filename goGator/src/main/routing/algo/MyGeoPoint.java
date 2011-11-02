package main.routing.algo;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

import android.location.Location;

public final class MyGeoPoint implements Comparable<MyGeoPoint>
{
	private static String[][] routeDictionary;
	/**
	 * The largest possible number of geopoints.
	 */
	private static int MAX_NUMBER; // Need to have number of DB entries line here.
	
	public static int getPathFrags() {
		return pathFrags;
	}

	private static int pathFrags;
	
	private static MyGeoPoint[] mygp;

	public static MyGeoPoint[] getMygp() {
		return mygp;
	}
	
	static
	{
        // initialize all GeoPoint objects
		//TODO Initialize from DB instead
//		for (char c = 'A'; c <= 'Z'; c++)
//		{
//			mygp[getIndexForName(c)] = new MyGeoPoint(c);
//		}
		routeDictionary = RouteDictionary.getRouteDictionary();
		MAX_NUMBER = routeDictionary.length;
		pathFrags=MAX_NUMBER;
		mygp = new MyGeoPoint[MAX_NUMBER]; 
		for (int i=0;i<MAX_NUMBER;i++){ 
			GeoPoint gp = new GeoPoint((int)(Double.parseDouble(routeDictionary[i][1])*1E6),(int)(Double.parseDouble(routeDictionary[i][2])*1E6));
			Location loc = geoToLoc(gp);
			mygp[i] = new MyGeoPoint(routeDictionary[i][0],loc,"");
		}
		for (int i=0;i<MAX_NUMBER;i++){ 
			mygp[i].addNeighbors(routeDictionary[i][3]);
		}
	}
		
	private static int getIndexForName(String name)
	{
		//TODO save Entry Ids here.
		for(int i=0;i<MAX_NUMBER;i++){
			if(name.trim().equalsIgnoreCase(routeDictionary[i][0].trim())){
				return i;
			}
		}
		return -1;
	}	
	
	private MyGeoPoint addNeighbors(String neig) {
		String[] neighbors =  neig.split(",");
		pathFrags = pathFrags+neighbors.length;
		for(int i=0;i<neighbors.length;i++){
			this.myngp.add(valueOf(neighbors[i].trim()));
		}
		return null;
	}

	private static String getNameForIndex(int index)
	{
		//TODO Retrieve using id from DB.
		return routeDictionary[index][0];
	}	
	
//	public static final MyGeoPoint A = MyGeoPoint.valueOf("Administration Building");
//	public static final MyGeoPoint Business = MyGeoPoint.valueOf("Business");
//	public static final MyGeoPoint HSS = MyGeoPoint.valueOf("HSS");
//	public static final MyGeoPoint LIB = MyGeoPoint.valueOf("J.Paul Leonard Library");
//	public static final MyGeoPoint in1 = MyGeoPoint.valueOf("in1");
//    public static final MyGeoPoint in2 = MyGeoPoint.valueOf("in2");
	
	public Location getGploc() {
		return gploc;
	}

	public static MyGeoPoint valueOf(String name)
	{
//		//TODO Remove this exception handling
//		if (name < 'A' || name > 'Z')
//		{
//			throw new IllegalArgumentException("Invalid geopoint name: " + name);	
//		}
		//TODO currently returning the name of GeoPoint but for app, we will need to get GeoPoint.
		return mygp[getIndexForName(name)];
	}
	
    /*
     * Package members only. To get name from Index.
     *	//TODO get GeoPoint with Index here
     */
	public static MyGeoPoint valueOf(int n)
	{	
		//TODO Remove this exception handling
//		if (n < 0 || n > 25)
//		{
//			throw new IllegalArgumentException("Invalid geopoint number: " + n);
//		}
		
		return valueOf( getNameForIndex(n) );		
	}

	private String name;
	private Location gploc;
	private List<MyGeoPoint> myngp;
    /**
     * Private constructor.
     * @param name
     */
	private MyGeoPoint(String name,Location gploc,String neig)
	{
		this.name = name;
		this.gploc = gploc;
		//Add neighboring GeoPoints
		this.myngp = new ArrayList<MyGeoPoint>();
		if(!neig.equalsIgnoreCase("")){
			String[] neighbors =  neig.split(",");
			pathFrags = pathFrags+neighbors.length;
			for(int i=0;i<neighbors.length;i++){
				this.myngp.add(valueOf(neighbors[i].trim()));
			}
		}
	}
	
	
	public MyGeoPoint(){
		this.name = "";
		this.gploc = new Location("");
		this.myngp = new ArrayList<MyGeoPoint>();
	}
	
	public List<MyGeoPoint> getMyngp() {
		return myngp;
	}

	public MyGeoPoint getMyGeoPoint()
	{
		return this;	
	}
	
	public static MyGeoPoint getMyGeoPoint(GeoPoint gp)
	{
		Location loc = geoToLoc(gp);
		for(int i=0;i<MAX_NUMBER;i++){
			if(LOCcompare(loc,MyGeoPoint.mygp[i].getGploc()))
				return mygp[i];
		}
		return mygp[0];	
	}
	
    private static boolean LOCcompare(Location loc, Location loc2) {
//    	System.out.println((loc.getLatitude()/1E6)+"="+(loc2.getLatitude()/1E6));
		if((loc.getLatitude()/1E6)== (loc2.getLatitude()/1E6) && (loc.getLongitude()/1E6) == (loc2.getLongitude()/1E6))
			return true;
		return false;
	}
    
    public static MyGeoPoint getNearestMyGeoPoint(GeoPoint gp) {
    	Location loc = geoToLoc(gp);
    	float[] dist = new float[MAX_NUMBER];
		for(int i=0;i<MAX_NUMBER;i++){
			dist[i]=(float) (loc.distanceTo(MyGeoPoint.mygp[i].getGploc())/1E6);
		}
//		System.out.println(main.overlay.MyMappedPath.smallestIndex(dist));
		return mygp[main.overlay.MyMappedPath.smallestIndex(dist)];
	}

	/*
     * Package members only.
     */
	public int getIndex()
	{
		return getIndexForName(name);
	}	
	
    /**
     * @see java.lang.Object#toString()
     */
	public String toString()
	{
		return String.valueOf(name);
	}

    /**
     * Two mygp are considered equal if they are the same object,
     * or their names are the same.
     * 
     * @see java.lang.Object#equals(Object)
     */    
    public boolean equals(Object o)
    {
        return this == o || equals((MyGeoPoint) o);
    }
    
    private boolean equals(MyGeoPoint c)
    {
        return this.name == c.name;
    }

    /**
     * Compare two mygp by name.
     * 
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(MyGeoPoint c)
    {
        return getIndexForName(this.name) - getIndexForName(c.name);
    }
    
  //GeoPoint to Location
	public static Location geoToLoc(GeoPoint gp){
		Location loc = new Location("");
		loc.setLatitude(gp.getLatitudeE6());
		loc.setLongitude(gp.getLongitudeE6());
		return loc;
	}

	public static float getDistance(MyGeoPoint mygp1, MyGeoPoint mygp2) {
		return mygp1.gploc.distanceTo(mygp2.gploc);
	}

}
