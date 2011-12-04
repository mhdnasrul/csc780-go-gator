package main.data;

import gatorDB.DataBaseHelper;

import java.util.ArrayList;
import java.util.Collections;

import screen.main.GoGatorActivity;

import main.overlay.MyOverlayItem;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;


public class VisitItems {
	private static ArrayList<MyOverlayItem> visitItems;
	
	public VisitItems(){
		visitItems = DataBaseHelper.queryDB("type = 'Visit'");
		Collections.sort(visitItems);
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
