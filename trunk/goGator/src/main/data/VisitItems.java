package main.data;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;


public class VisitItems {
	private static ArrayList<MyOverlayItem> visitItems;
	private String[][] visitData = {{"37721274", "-122476868", "Visit 1", "Visit 1 Desc" },
									   {"37721435", "-122477453", "Visit 2", "Visit 2 Desc" },
									   {"37721410", "-122480020", "Visit 3", "Visit 3 Desc" },
									   {"37722021", "-122478550", "Visit 4", "Visit 4 Desc" },
									   {"37722091", "-122476868", "Visit 5", "Visit 5 Desc" }
												};
	
	public VisitItems(){
		visitItems = new ArrayList<MyOverlayItem>();
		
		for(int i=0;i<visitData[0].length;i++)
			visitItems.add(new MyOverlayItem(new GeoPoint
								(Integer.parseInt(visitData[i][0]), Integer.parseInt(visitData[i][1])), 
								visitData[i][2], visitData[i][3],i));
	}

	/**
	 * @param visitItems the visitItems to set
	 */
	public static void setVisitItems(ArrayList<MyOverlayItem> visitItems) {
		VisitItems.visitItems = visitItems;
	}

	/**
	 * @return the visitItems
	 */
	public static ArrayList<MyOverlayItem> getVisitItems() {
		return visitItems;
	}
	
	public static MyOverlayItem getVisitItem(int index) {
		return visitItems.get(index);
	}
}
