package screen.main;

import gatorDB.DataBaseHelper;

import java.io.IOException;

import main.common.Utils;
import main.data.BuildingItems;
import main.data.CafeItems;
import main.data.DeptItems;
import main.data.VisitItems;
import main.routing.algo.CampusMap;
import main.routing.algo.DijkstraEngine;
import main.routing.algo.RoutesMap;
import screen.level1.CameraActivity;
import screen.level1.HomeActivity;
import screen.level1.MapsActivity;
import screen.level1.MoreActivity;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TabHost;

public class GoGatorActivity extends TabActivity {

	/** Called when the activity is first created. */
	private static RoutesMap campusmap;
	private static DijkstraEngine engine;
	private static DataBaseHelper myDbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openAndQueryDatabase();
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, HomeActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("home")
				.setIndicator("Home", res.getDrawable(R.drawable.ic_home))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, MapsActivity.class);
		intent.putExtra("from", "tab");
		spec = tabHost.newTabSpec("map")
				.setIndicator("Map", res.getDrawable(R.drawable.ic_map))
				.setContent(intent);
		tabHost.addTab(spec);

		// intent = new Intent("com.google.zxing.client.android.SCAN");
		// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		// startActivityForResult(intent, 0);

		intent = new Intent().setClass(this, CameraActivity.class);
		spec = tabHost
				.newTabSpec("camera")
				.setIndicator("Point It!",
						res.getDrawable(R.drawable.ic_camera))
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, MoreActivity.class);
		spec = tabHost.newTabSpec("more")
				.setIndicator("About Us!", res.getDrawable(R.drawable.ic_info))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		// Setting the lists
		new BuildingItems();
		new DeptItems();
		new CafeItems();
		new VisitItems();

		new Thread(new Runnable() {
			public void run() {
				campusmap = CampusMap.generateCampusMap();
				// Generate Campus Map and feed it to Dijkstra Engine
				engine = new DijkstraEngine(campusmap);
			}
		}).start();
	}

	/**
	 * @return the campusmap
	 */
	public static RoutesMap getCampusmap() {
		return campusmap;
	}

	/**
	 * @param engine
	 *            the engine to set
	 */
	public static void setEngine(DijkstraEngine engine) {
		GoGatorActivity.engine = engine;
	}

	/**
	 * @return the engine
	 */
	public static DijkstraEngine getEngine() {
		return engine;
	}

	// public void onActivityResult(int requestCode, int resultCode, Intent
	// intent) {
	// System.out.println("Here");
	// if (requestCode == 0) {
	// if (resultCode == RESULT_OK) {
	// String contents = intent.getStringExtra("SCAN_RESULT");
	// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	// System.out.println(contents);
	// // System.out.println(contents);
	// // Utils.toast(this, contents);
	// // Handle successful scan
	// } else if (resultCode == RESULT_CANCELED) {
	// System.out.println("Else");
	// // Handle cancel
	// }
	// }
	// }

	private void openAndQueryDatabase() {

	  myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

//			myDbHelper.openDataBase();

		} catch (SQLException sqle) {
			throw sqle;

		}

	}
	
	public static DataBaseHelper getMyDbHelper() {
		return myDbHelper;
	}

}
