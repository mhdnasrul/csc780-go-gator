package main.routing.algo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraEngine
{
    /**
     * Infinity value for distances.
     */
    public static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

    /**
     * Some value to initialize the priority queue with.
     */
    private static final int INITIAL_CAPACITY = 8;
    
    /**
     * This comparator orders cities according to their shortest distances,
     * in ascending fashion. If two cities have the same shortest distance,
     * we compare the cities themselves.
     */
    private final Comparator<MyGeoPoint> shortestDistanceComparator = new Comparator<MyGeoPoint>()
        {
            public int compare(MyGeoPoint left, MyGeoPoint right)
            {
                // note that this trick doesn't work for huge distances, close to Integer.MAX_VALUE
                float result = getShortestDistance(left) - getShortestDistance(right);
                
                return (int)((result == 0) ? left.compareTo(right) : result);
            }
        };
    
    /**
     * The graph.
     */
    private final RoutesMap map;
    
    /**
     * The working set of cities, kept ordered by shortest distance.
     */
    private final PriorityQueue<MyGeoPoint> unsettledNodes = new PriorityQueue<MyGeoPoint>(INITIAL_CAPACITY, shortestDistanceComparator);
    
    /**
     * The set of cities for which the shortest distance to the source
     * has been found.
     */
    private final Set<MyGeoPoint> settledNodes = new HashSet<MyGeoPoint>();
    
    /**
     * The currently known shortest distance for all cities.
     */
    private final Map<MyGeoPoint, Float> shortestDistances = new HashMap<MyGeoPoint, Float>();

    /**
     * Predecessors list: maps a mygp to its predecessor in the spanning tree of
     * shortest paths.
     */
    private final Map<MyGeoPoint, MyGeoPoint> predecessors = new HashMap<MyGeoPoint, MyGeoPoint>();
    
    /**
     * Constructor.
     */
    public DijkstraEngine(RoutesMap map)
    {
        this.map = map;
    }

    /**
     * Initialize all data structures used by the algorithm.
     * 
     * @param start the source node
     */
    private void init(MyGeoPoint start)
    {
        settledNodes.clear();
        unsettledNodes.clear();
        
        shortestDistances.clear();
        predecessors.clear();
        
        // add source
        setShortestDistance(start, 0);
        unsettledNodes.add(start);
    }
    
    /**
     * Run Dijkstra's shortest path algorithm on the map.
     * The results of the algorithm are available through
     * {@link #getPredecessor(MyGeoPoint)}
     * and 
     * {@link #getShortestDistance(MyGeoPoint)}
     * upon completion of this method.
     * 
     * @param start the starting mygp
     * @param destination the destination mygp. If this argument is <code>null</code>, the algorithm is
     * run on the entire graph, instead of being stopped as soon as the destination is reached.
     */
    public void execute(MyGeoPoint start, MyGeoPoint destination)
    {
        init(start);
        
        // the current node
        MyGeoPoint u;
        
        // extract the node with the shortest distance
        while ((u = unsettledNodes.poll()) != null)
        {
            assert !isSettled(u);
            
            // destination reached, stop
            if (u == destination) break;
            
            settledNodes.add(u);
            
            relaxNeighbors(u);
        }
    }

    /**
	 * Compute new shortest distance for neighboring nodes and update if a shorter
	 * distance is found.
	 * 
	 * @param u the node
	 */
    private void relaxNeighbors(MyGeoPoint u)
    {
        for (MyGeoPoint v : map.getDestinations(u))
        {
            // skip node already settled
            if (isSettled(v)) continue;
            
            float shortDist = getShortestDistance(u) + map.getDistance(u, v);
            
            if (shortDist < getShortestDistance(v))
            {
            	// assign new shortest distance and mark unsettled
                setShortestDistance(v, shortDist);
                                
                // assign predecessor in shortest path
                setPredecessor(v, u);
            }
        }        
    }

	/**
	 * Test a node.
	 * 
     * @param v the node to consider
     * 
     * @return whether the node is settled, ie. its shortest distance
     * has been found.
     */
    private boolean isSettled(MyGeoPoint v)
    {
        return settledNodes.contains(v);
    }

    /**
     * @return the shortest distance from the source to the given mygp, or
     * {@link DijkstraEngine#INFINITE_DISTANCE} if there is no route to the destination.
     */    
    public float getShortestDistance(MyGeoPoint mygp)
    {
        Float d = shortestDistances.get(mygp);
        return (d == null) ? INFINITE_DISTANCE : d;
    }

	/**
	 * Set the new shortest distance for the given node,
	 * and re-balance the queue according to new shortest distances.
	 * 
	 * @param mygp the node to set
	 * @param distance new shortest distance value
	 */        
    private void setShortestDistance(MyGeoPoint mygp, float distance)
    {
        /*
         * This crucial step ensures no duplicates are created in the queue
         * when an existing unsettled node is updated with a new shortest 
         * distance.
         * 
         * Note: this operation takes linear time. If performance is a concern,
         * consider using a TreeSet instead instead of a PriorityQueue. 
         * TreeSet.remove() performs in logarithmic time, but the PriorityQueue
         * is simpler. (An earlier version of this class used a TreeSet.)
         */
        unsettledNodes.remove(mygp);

        /*
         * Update the shortest distance.
         */
        shortestDistances.put(mygp, distance);
        
		/*
		 * Re-balance the queue according to the new shortest distance found
		 * (see the comparator the queue was initialized with).
		 */
		unsettledNodes.add(mygp);        
    }
    
    /**
     * @return the mygp leading to the given mygp on the shortest path, or
     * <code>null</code> if there is no route to the destination.
     */
    public MyGeoPoint getPredecessor(MyGeoPoint mygp)
    {
        return predecessors.get(mygp);
    }
    
    private void setPredecessor(MyGeoPoint a, MyGeoPoint b)
    {
        predecessors.put(a, b);
    }

}
