package screen.level2;

import screen.level1.RotatedMapView;
import screen.level1.overlay.BalloonItemizedOverlay;
import screen.level1.overlay.mapOverlay;
import screen.level1.overlay.markerOverlay;
import screen.level2.overlay.rideOverlay;
import screen.main.GoGatorActivity;
import screen.main.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.common.Utils;
import main.data.BuildingItems;
import main.data.CafeItems;
import main.data.DeptItems;
import main.data.VisitItems;
import main.overlay.MyMappedPath;
import main.overlay.MyOverlayItem;
import main.routing.algo.CampusMap;
import main.routing.algo.MyGeoPoint;
import main.routing.algo.RouteDictionary;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import main.routing.algo.DijkstraEngine;

public class MyRideActivity extends MapActivity implements SensorEventListener {

	private LocationManager lm;
	private LocationListener ll;
	private MapController mc;
	public GeoPoint currLocation, destLocation, prevRecLocation;
	private String provider;
	private float azimut;
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private static Context context;
	private boolean inBuilding;
	private int step;
	private List<Overlay> mapOverlays;
	private RotatedMapView rMapView;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private MapView mapView;
	private Drawable drawable;
	public markerOverlay mFlagOverlay;
	public List<rideOverlay> mPathOverlay;
	private ImageView showBuildingButton;
	private rideOverlay myParkedRide;
	private boolean rideParked;
	private boolean pathDrawn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ridetab);
		showBuildingButton = (ImageView) findViewById(R.id.reset);
		showBuildingButton.setVisibility(View.INVISIBLE);
		// Rotating Layout which has MapView loaded in it.
		rMapView = (RotatedMapView) findViewById(R.id.rotating_layout);
		// Setting MapView & its controllers
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);

		// Putting it over rotating layout for magnetometer to use.
		rMapView.setMapView(mapView);
		rMapView.setRotateEnabled(false);

		// setContext to used for dialog boxes to appear
		context = this;

		// Register the sensor listeners
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		// Initialize to get overlays over map e.g. Flags, Drawing Path etc.
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.ride);

		// To place current Location marker on Map
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		mapOverlays.add(myLocationOverlay);

		// Setup location service to get coordinates
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();

		// Set to receive coordinates from best Provider
		provider = lm.getBestProvider(criteria, true);
		
		// Uncomment below line for emulator SSH
		// provider = LocationManager.GPS_PROVIDER;

		// To check from which provider coordinates are being supplied.
		// System.out.println("Provider:" + provider);

		ll = new MyLocationListener();
		lm.requestLocationUpdates(provider, 0, 0, ll);

		// Get the current location in start-up
		try {
			currLocation = new GeoPoint(
					(int) (lm.getLastKnownLocation(provider).getLatitude() * 1000000),
					(int) (lm.getLastKnownLocation(provider).getLongitude() * 1000000));
		} catch (Exception e) {
			System.out.println("Current Location not set up yet.");
			// Default Location - Thornton Hall
			currLocation = new GeoPoint(37723730, -122476890);

		}
		// This is set to store previous provided coordinates. Will be helpful
		// to see from which point to which point user is travelling.
		prevRecLocation = currLocation;
		
		myParkedRide = null;
		rideParked = false;
		pathDrawn = false;
//		SharedPreferences settings1 = getSharedPreferences("saveMe", 0);
//	      SharedPreferences.Editor editor = settings1.edit();
//	      editor.putBoolean("rideParked", false);
//	      // Commit the edits!
//	      editor.commit();
		// Restore preferences
	       SharedPreferences settings = getSharedPreferences("saveMe", 0);
	       if(settings.getBoolean("rideParked", false)){
	    	   int lat = settings.getInt("rideLoclat", 122);
	    	   int lon = settings.getInt("rideLoclon", -37);
	    	   destLocation = new GeoPoint(lat,lon);
	    	   simulateSaveloc(destLocation);
	       }
	       if(settings.getBoolean("pathDrawn", false)){
	    	   simulateSaveloc(destLocation);
	       }
	       


		System.out.println("OnCreateCalled");
		mc = mapView.getController();
		mc.animateTo(currLocation);
		mc.setZoom(17);
	}


	// Needle Image Click
	public void rotateMap(View v) {
		// To get focus to current location while using Magnetometer.
		mc.animateTo(currLocation);

		if (rMapView.isRotateEnabled())
			rMapView.setRotateEnabled(false);
		else
			rMapView.setRotateEnabled(true);
	}

	// To animate to Current location - Location Image Click
	public void locateMe(View v) {
		mc.animateTo(currLocation);
	}
	
	// Restore MapActivity to original state
	public void resetLayout(View v) {
		//Restart the activity
	      rideParked=false;
	      pathDrawn = false;
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
		
	// Save Current Location as Ride Location
		public void saveloc(View v) {
			// To get focus to current location while using Magnetometer.
			Button thisButton = (Button) findViewById(R.id.saveloc);
			mc.animateTo(currLocation);
			
			if(thisButton.getText().toString().equalsIgnoreCase("Save Ride Location")){
				destLocation = currLocation;
				myParkedRide = new rideOverlay(currLocation, destLocation, 4);
				mapOverlays.add(myParkedRide);
				rideParked = true;
				thisButton.setText("Where's My Ride?");
				showBuildingButton.setVisibility(View.VISIBLE);
			}
			else{
				DrawPath(currLocation, destLocation, Color.GREEN);
				pathDrawn = true;
			}
		}
		
		// Save Current Location as Ride Location
				public void simulateSaveloc(GeoPoint destLocation) {
					// To get focus to current location while using Magnetometer.
					Button thisButton = (Button) findViewById(R.id.saveloc);
//					mc.animateTo(currLocation);
					
					if(thisButton.getText().toString().equalsIgnoreCase("Save Ride Location")){
						myParkedRide = new rideOverlay(destLocation, destLocation, 4);
						mapOverlays.add(myParkedRide);
						rideParked = true;
						thisButton.setText("Where's My Ride?");
						showBuildingButton.setVisibility(View.VISIBLE);
					}
					else{
						DrawPath(currLocation, destLocation, Color.GREEN);
						pathDrawn = true;
					}
				}

		
	protected void onResume() {
		super.onResume();
		// Re-catch Magnetometer on Resume
		mSensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, magnetometer,
				SensorManager.SENSOR_DELAY_UI);
		System.out.println("OnResume Called");
//		if(myParkedRide != null)
//			mapOverlays.add(myParkedRide);
	}

	protected void onPause() {
		super.onPause();
		// Unregister Sensor
		System.out.println("OnPause Called");
		mSensorManager.unregisterListener(this);
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = getSharedPreferences("saveMe", 0);
	      SharedPreferences.Editor editor = settings.edit();
	      System.out.println("rideParked??"+rideParked);
	      editor.putBoolean("rideParked", rideParked);
	      editor.putBoolean("pathDrawn", pathDrawn);
	      
			if (rideParked) {
				editor.putInt("rideLoclat", destLocation.getLatitudeE6());
				editor.putInt("rideLoclon", destLocation.getLongitudeE6());
			}
		// Commit the edits!
	      editor.commit();
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}


	// Function that calculates Azimut angle from Magnetometer readings.
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			mGravity = event.values;
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			mGeomagnetic = event.values;
		if (mGravity != null && mGeomagnetic != null) {
			float R[] = new float[9];
			float I[] = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
					mGeomagnetic);
			if (success) {
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				azimut = orientation[0]; // orientation contains: azimut, pitch
											// and roll
				azimut = azimut * 360 / (2 * 3.14159f);
			}
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public class MyLocationOverlay extends com.google.android.maps.Overlay {

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);
			// CampusMap.generateCampusMap();
			Paint paint = new Paint();
			GeoPoint gp1 = currLocation;
			GeoPoint gp2;

//			try { // Exception handling if NextClosestGeoPoint is not set.
//					// Especially in the case of MapTab.
//				MyMappedPath.getClosestGeoPoint(gp1);
//				gp2 = Utils.locToGeo(MyMappedPath.getNextClosestGeoPoint(gp1));
//			} catch (NullPointerException e) {
//				gp2 = destLocation;
//			}
			
			Point point1 = new Point();
			Point point2 = new Point();

			Projection projection = mapView.getProjection();
			projection.toPixels(gp1, point1);
//			projection.toPixels(gp2, point2);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.arrow);

			// This angle if you want pointer to point where you 'should be'
			// heading in the path.
			// Find Angle between two planar points in radians
			// float angle = findAngle(point1, point2);

			// This angle if you want pointer to point where you 'are' heading.
			// float angle = azimut;

			// Put arrow in right location on Map and give it the calculated
			// angle
			Matrix matrix = new Matrix();
			//Adjustment for titlebar - present while App is running
			matrix.postTranslate(-25, -25);
			matrix.postRotate(azimut);
			matrix.postTranslate(point1.x, point1.y);

			// Set paint parameters
			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);

			// Finally Draw it with calculated matrix
			canvas.drawBitmap(bmp, matrix, paint);

			if (rMapView.isRotateEnabled()) {
				if (azimut >= 0)
					rMapView.setCompassBearing(azimut);
				else
					rMapView.setCompassBearing(360 + azimut); // Because
																// RotatedMapView
				
				//Code for rotating Needle to show always North, but didn't pass the test. - Future Enhancement
//				 Bitmap bMap = BitmapFactory.decodeResource(getResources(),
//				 R.drawable.needle);
//				 Matrix mat = new Matrix();
//				 mat.postTranslate(-25, -25);
//				 if (azimut >= 0)
//					 mat.postRotate(azimut, bMap.getWidth()/2,bMap.getHeight()/2);
//				 else
//					 mat.postRotate(360+azimut, bMap.getWidth()/2,bMap.getHeight()/2);
//				 mat.postTranslate(25, 25);
//				 // Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
//				 //bMap.getWidth(), bMap.getHeight(), mat, true);
//				 canvas.drawBitmap(bMap, mat, paint);
			}
			return true;
		}
		
		//Deprecated Method, It will help in finding Angle 
//		private float findAngle(Point point1, Point point2) {
//			double dlon = point2.x - point1.x;
//			double dlat = point2.y - point1.y;
//
//			// Formula 1
//			// Keeping this formula to switch between accuracy and fast
//			// response.
//			// double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) +
//			// Math.cos(gp1.getLatitudeE6()) * Math.cos(gp2.getLatitudeE6()) *
//			// (Math.sin(dlon / 2) * Math.sin(dlon / 2));
//			// double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//			// Formula 2
//			double angle = Math.atan2(dlat, dlon);
//
//			// Convert Degrees to Radians
//			angle = angle * 180 / Math.PI;
//
//			return (float) angle;
//		}

	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location argLocation) {
			currLocation = new GeoPoint(
					(int) (argLocation.getLatitude() * 1000000),
					(int) (argLocation.getLongitude() * 1000000));


						System.out.println("Location is changed!!!");

			// No need to reset view with location changed.
			// mc.animateTo(currLocation);
			// mc.setZoom(17);

		}

		public void onProviderDisabled(String provider) {
			// Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Auto-generated method stub
		}
	}

	public void DrawPath(GeoPoint src, GeoPoint dest, int color) {
		mPathOverlay = new ArrayList<rideOverlay>();
		
		// Dijkstra Part
		new MyGeoPoint();
		System.out.println("Max Number");

		ArrayList<MyGeoPoint> gps = CampusMap.findShortestPath(
				MyGeoPoint.getNearestMyGeoPoint(src),
				MyGeoPoint.getMyGeoPoint(dest));
		System.out.println(gps);

		ArrayList<Location> mappedpath = new ArrayList<Location>();
		// To draw a direct path between current location and the geopoint in
		// the Map we know.
		String path = (src.getLongitudeE6() / 1E6) + ","
				+ (src.getLatitudeE6() / 1E6) + ",0.000000 ";
		for (MyGeoPoint gp : gps) {
			path += (gp.getGploc().getLongitude() / 1E6) + ","
					+ (gp.getGploc().getLatitude() / 1E6) + ",0.000000 ";
		}
		System.out.println("Path:" + path);
		String[] pairs = path.split(" ");
		String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude
												// lngLat[1]=latitude
												// lngLat[2]=height
		// src
		GeoPoint startGP = new GeoPoint(
				(int) (Double.parseDouble(lngLat[1]) * 1E6),
				(int) (Double.parseDouble(lngLat[0]) * 1E6));
		mPathOverlay.add(new rideOverlay(startGP, startGP, 1));
//		mapOverlays.add(mPathOverlay[0]);
//		mapOverlays.add(new rideOverlay(startGP, startGP, 1));
		// Adding startpoint to mappedPath
		mappedpath.add(Utils.geoToLoc(startGP));

		GeoPoint gp1;
		GeoPoint gp2 = startGP;

		for (int i = 1; i < pairs.length; i++) { // the last one would be crash
			lngLat = pairs[i].split(",");
			gp1 = gp2;
			// watch out! For GeoPoint, first:latitude, second:longitude
			gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6),
					(int) (Double.parseDouble(lngLat[0]) * 1E6));
			mPathOverlay.add(new rideOverlay(gp1, gp2, 2, color));
//			mapOverlays.add(mPathOverlay[1]);
//			mapOverlays.add(new rideOverlay(gp1, gp2, 2, color));
			// Adding geopoints as location to mappedpath
			mappedpath.add(Utils.geoToLoc(gp2));
		}
		MyMappedPath.setMypath(mappedpath);
		mPathOverlay.add(new rideOverlay(dest, dest, 2));
		mapOverlays.addAll(mPathOverlay); // use the
															// default
		
		
		showBuildingButton.setVisibility(View.VISIBLE);
	}

// class markerOverlay {
//		private mapOverlay itemizedoverlay;
//		
//		public markerOverlay(Drawable defaultMarker, MyOverlayItem myItem, MapView mapView){
//			this.itemizedoverlay = new mapOverlay(defaultMarker, mapView);
//				itemizedoverlay.addOverlay(myItem);
//		}
//		
//		public markerOverlay(Drawable defaultMarker, ArrayList<MyOverlayItem> myItems, MapView mapView){
//			this.itemizedoverlay = new mapOverlay(defaultMarker, mapView);
//			for(MyOverlayItem myItem: myItems)
//				itemizedoverlay.addOverlay(myItem);
//		}
//		
//		public markerOverlay(Drawable defaultMarker, ArrayList<MyOverlayItem> myItems, MapView mapView, MapsActivity mapAct){
//			this.itemizedoverlay = new mapOverlay(defaultMarker, mapView, mapAct);
//			for(MyOverlayItem myItem: myItems)
//				itemizedoverlay.addOverlay(myItem);
//		}
//		
//		/**
//		 * @param itemizedoverlay the itemizedoverlay to set
//		 */
//		public void setItemizedoverlay(mapOverlay itemizedoverlay) {
//			this.itemizedoverlay = itemizedoverlay;
//		}
//
//		/**
//		 * @return the itemizedoverlay
//		 */
//		public mapOverlay getItemizedoverlay() {
//			return itemizedoverlay;
//		}
// }
// 
// public class mapOverlay extends BalloonItemizedOverlay<MyOverlayItem> {
//
//		private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
//		private MapsActivity mapActivity;
//		MapView mapView;
//
//		public mapOverlay(Drawable defaultMarker, MapView mapView) {
//			super(boundCenter(defaultMarker), mapView);
//		}
//		
//		public mapOverlay(Drawable defaultMarker, MapView mapView, MapsActivity mapAct) {
//			super(boundCenter(defaultMarker), mapView);
//			mapActivity = mapAct;
//			this.mapView =  mapView;
//		}
//
//		@Override
//		protected MyOverlayItem createItem(int i) {
//			return mOverlays.get(i);
//		}
//
//		@Override
//		public int size() {
//			return mOverlays.size();
//		}
//
//
//		public void addOverlay(MyOverlayItem overlay) {
//			mOverlays.add(overlay);
//			this.populate();
//		}
//		
//		@Override
//		protected boolean onBalloonTap(int index, MyOverlayItem item) {
//			DrawPath(currLocation, item.getPoint(), Color.GREEN);
//			mapView.getOverlays().remove(1);
//			return true;
//		}
//
//	}
}
