package main.routing.algo;

import gatorDB.DataBaseHelper;

public class RouteDictionary {
	private static String[][] routeDictionary;
	
	private static String[][] THDictionary;
//	= {
//		{ "thStairs", "37.723482", "-122.47682", "thentrance" },
//		{ "thentrance", "37.723617", "-122.476903", "thStairs,th3p1" },
////		{ "th3p1", "37.7238721", "-122.4737268", "thentrance,th3pl" },
//		{ "th3pl", "37.7235022", "-122.476945", "th3p1,thDean" },
////		{ "thDean", "37.723758375", "-122.4769193", "th3pl,thback" },
////		{ "thback", "37.72377826", "-122.47691932", "thDean" },
//		{ "thLift", "37.7237647714", "-122.4770455", "th3pl" },
//		{ "th9lift", "37.72373926", "-122.47700974", "th9entrance" },
//		{ "th9entrance", "37.7236271", "-122.4796261", "th9lift,thNiki" },
//		{ "thNiki", "37.7236279", "-122.4796261", "th9entrance,thPuder" },
//		{ "thPuder", "37.72373344", "-122.47741884", "thNiki" },
////		{ "th6lift", "37.723606", "-122.4771943", "th604,th641" },
////		{ "th604", "37.723526", "-122.477307", "th6lift" },
////		{ "th641", "37.7236864", "-122.47694385", "th6lift,thBridge" },
////		{ "thBridge", "37.72346655", "-122.476596", "th641" } 
//		};

	public RouteDictionary(){
		RouteDictionary.routeDictionary = DataBaseHelper.queryDict();
		RouteDictionary.THDictionary = DataBaseHelper.queryBldg("th");
	}
	/**
	 * @param routeDictionary
	 *            the routeDictionary to set
	 */
	public void setRouteDictionary(String[][] routeDictionary) {
		RouteDictionary.routeDictionary = routeDictionary;
	}

	/**
	 * @return the routeDictionary
	 */
	public static String[][] getRouteDictionary() {
		return routeDictionary;
	}

	/**
	 * @param tHDictionary the tHDictionary to set
	 */
	public static void setTHDictionary(String[][] tHDictionary) {
		THDictionary = tHDictionary;
	}

	/**
	 * @return the tHDictionary
	 */
	public static String[][] getTHDictionary() {
		return THDictionary;
	}
}
