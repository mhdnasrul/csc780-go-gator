package main.data;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;


public class CafeItems {
	private static ArrayList<MyOverlayItem> cafeItems;
	private String[][] cafeData = {{"37721274", "-122476868", "Cafe 1", "Cafe 1 Desc" },
									   {"37721435", "-122477453", "Cafe 2", "Cafe 2 Desc" },
									   {"37721410", "-122480020", "Cafe 3", "Cafe 3 Desc" },
									   {"37722021", "-122478550", "Cafe 4", "Cafe 4 Desc" },
									   {"37722091", "-122476868", "Cafe 5", "Cafe 5 Desc" }
												};
	
	public CafeItems(){
		cafeItems = new ArrayList<MyOverlayItem>();
		
		for(int i=0;i<cafeData[0].length;i++)
			cafeItems.add(new MyOverlayItem(new GeoPoint
								(Integer.parseInt(cafeData[i][0]), Integer.parseInt(cafeData[i][1])), 
								cafeData[i][2], cafeData[i][3],i));
	}

	/**
	 * @param cafeItems the cafeItems to set
	 */
	public static void setCafeItems(ArrayList<MyOverlayItem> cafeItems) {
		CafeItems.cafeItems = cafeItems;
	}

	/**
	 * @return the cafeItems
	 */
	public static ArrayList<MyOverlayItem> getCafeItems() {
		return cafeItems;
	}
	
	public static MyOverlayItem getCafeItem(int index) {
		return cafeItems.get(index);
	}
}
