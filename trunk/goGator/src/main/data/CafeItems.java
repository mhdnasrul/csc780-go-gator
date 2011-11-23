package main.data;

import gatorDB.DataBaseHelper;

import java.util.ArrayList;

import screen.main.GoGatorActivity;

import main.overlay.MyOverlayItem;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;


public class CafeItems {
	private static ArrayList<MyOverlayItem> cafeItems;
	
	public CafeItems(){
		cafeItems = DataBaseHelper.queryDB("type like 'Caf%'");
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
