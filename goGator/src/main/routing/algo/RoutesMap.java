package main.routing.algo;

import java.util.List;

public interface RoutesMap
{
	/**
	 * Enter a new segment in the graph.
	 */
	public void addDirectRoute(MyGeoPoint start, MyGeoPoint end, float f);
	
	/**
	 * Get the value of a segment.
	 */
	public float getDistance(MyGeoPoint start, MyGeoPoint end);
	
	/**
	 * Get the list of geopoints that can be reached from the given geopoint.
	 */
	public List<MyGeoPoint> getDestinations(MyGeoPoint mygp); 
	
	/**
	 * Get the list of geopoints that lead to the given geopoint.
	 */
	public List<MyGeoPoint> getPredecessors(MyGeoPoint mygp);
	
	/**
	 * @return the transposed graph of this graph, as a new RoutesMap instance.
	 */
	public RoutesMap getInverse();
}
