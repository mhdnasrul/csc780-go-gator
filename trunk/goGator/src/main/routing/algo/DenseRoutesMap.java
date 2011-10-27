package main.routing.algo;

import java.util.ArrayList;
import java.util.List;

public class DenseRoutesMap implements RoutesMap
{
	private final float[][] distances;
	
	public DenseRoutesMap(int numMygp)
	{
		distances = new float[numMygp][numMygp];
	}
	
	/**
	 * Link two geopoints by a direct route with the given distance.
	 */
	public void addDirectRoute(MyGeoPoint start, MyGeoPoint end, float distance)
	{
		distances[start.getIndex()][end.getIndex()] = distance;
	}
	
	/**
	 * @return the distance between the two geopoints, or 0 if no path exists.
	 */
	public float getDistance(MyGeoPoint start, MyGeoPoint end)
	{
		return distances[start.getIndex()][end.getIndex()];
	}
	
	/**
	 * @return the list of all valid destinations from the given geopoint.
	 */
	public List<MyGeoPoint> getDestinations(MyGeoPoint mygp)
	{
		List<MyGeoPoint> list = new ArrayList<MyGeoPoint>();
		
		for (int i = 0; i < distances.length; i++)
		{
			if (distances[mygp.getIndex()][i] > 0)
			{
				list.add( MyGeoPoint.valueOf(i) );
			}
		}
		
		return list;
	}

	/**
	 * @return the list of all geopoints leading to the given geopoint.
	 */
	public List<MyGeoPoint> getPredecessors(MyGeoPoint mygp)
	{
		List<MyGeoPoint> list = new ArrayList<MyGeoPoint>();
		
		for (int i = 0; i < distances.length; i++)
		{
			if (distances[i][mygp.getIndex()] > 0)
			{
				list.add( MyGeoPoint.valueOf(i) );
			}
		}
		
		return list;
	}
	
	/**
	 * @return the transposed graph of this graph, as a new RoutesMap instance.
	 */
	public RoutesMap getInverse()
	{
		DenseRoutesMap transposed = new DenseRoutesMap(distances.length);
		
		for (int i = 0; i < distances.length; i++)
		{
			for (int j = 0; j < distances.length; j++)
			{
				transposed.distances[i][j] = distances[j][i];
			}
		}
		
		return transposed;
	}
}
