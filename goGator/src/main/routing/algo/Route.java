package main.routing.algo;


import java.util.ArrayList;

public final class Route implements Cloneable
{
	// we need its concrete type to successfully clone this field
	private ArrayList<MyGeoPoint> mygpts = new ArrayList<MyGeoPoint>();
	
	private int distance = 0;
	
	/**
	 * Instances of this class are created by the {@link RouteBuilder}.
	 */	
	public Route()
	{
	}
	
	public Route clone()
	{
		Route newInstance = null;
		
		try
		{
			 newInstance = (Route) super.clone();	
		}
		catch (CloneNotSupportedException cnfe)
		{
			// we are Cloneable: this should never happen
			assert false : cnfe;
		}
		
		newInstance.mygpts = (ArrayList<MyGeoPoint>) mygpts.clone();
		
		return newInstance;
	}
	
	/**
	 * Add a new stop to this route with the given distance.
	 * If this is the first stop (i.e. the starting point), the
	 * <code>distance</code> argument is meaningless.
	 * 
	 * @param stop the next mygp on this route.
	 * @param distance the distance between the previous mygp and this one.
	 */
	public void addStop(MyGeoPoint stop, int distance)
	{
		if (!mygpts.isEmpty())
		{
			this.distance += distance;		
		}
		
		mygpts.add(stop);
	}
	
	/**
	 * @return the total distance of this route.
	 */
	public int getDistance()
	{
		return distance;	
	}
	
	/**
	 * @return the number of stops on this route. The starting mygp is not
	 * considered a stop and thus is not counted.
	 */
	public int getLength()
	{
		return (mygpts.isEmpty()) ? 0 : mygpts.size() - 1;
	}
	
	/**
	 * @return the last stop on this route. The last stop may be the
	 * starting point if there are no other stops, or NULL is this route
	 * has no stops.
	 */
	public MyGeoPoint getLastStop()
	{
		if (mygpts.isEmpty())
		{
			return null;
		}
		else
		{
			return mygpts.get(mygpts.size() - 1);
		}
	}
	
	/**
	 * @return whether this route goes through the given mygp.
	 */
	public boolean hasCity(MyGeoPoint mygp)
	{
		return mygpts.contains(mygp);
	}
	
	public String toString()
	{
		StringBuffer temp = new StringBuffer();
		
		temp.append("l=").append( getLength() )
			.append(" d=").append( getDistance() )
			.append(" ").append(mygpts);
			
		return temp.toString();
	}
}
