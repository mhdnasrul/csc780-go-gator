package main.data;

import gatorDB.DataBaseHelper;

import java.util.ArrayList;

import screen.main.GoGatorActivity;

import main.overlay.MyOverlayItem;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.maps.GeoPoint;

public class DeptItems {
	private static ArrayList<MyOverlayItem> deptItems;

	public DeptItems() {
		deptItems = DataBaseHelper.queryDB("type like 'Dept%'");
	}
	/**
	 * @param buildingItems
	 *            the buildingItems to set
	 */
	public static void setDeptItems(ArrayList<MyOverlayItem> deptItems) {
		DeptItems.deptItems = deptItems;
	}

	/**
	 * @return the buildingItems
	 */
	public static ArrayList<MyOverlayItem> getDeptItems() {
		return deptItems;
	}

	public static MyOverlayItem getDeptItem(int index) {
		return deptItems.get(index);
	}
}
