package screen.level1;

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

public class MapsActivity extends MapActivity implements SensorEventListener {

	private LocationManager lm;
	private LocationListener ll;
	private MapController mc;
	public GeoPoint currLocation, destLocation, prevRecLocation;
	private String provider;
	float azimut;
	private SensorManager mSensorManager;
	Sensor accelerometer;
	Sensor magnetometer;
	private static Context context;
	private boolean inBuilding;
	private int step;
	private List<Overlay> mapOverlays;
	private RotatedMapView rMapView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maptab);
		
		//Rotating Layout which has MapView loaded in it.
		rMapView = (RotatedMapView) findViewById(R.id.rotating_layout);
		
		MapView mapView = (MapView) findViewById(R.id.mapview);
		
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		rMapView.setMapView(mapView);
		
		rMapView.setRotateEnabled(false);
		
		
		setContext(this);
		
		// Register the sensor listeners
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.flag);

		// To place current Location marker on Map
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		mapOverlays.add(myLocationOverlay);
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = lm.getBestProvider(criteria, true);
//		provider = LocationManager.GPS_PROVIDER; //Uncomment this for emulator SSH
//		 Location location = lm.getLastKnownLocation(provider);
		System.out.println("Provider:" + provider);
		
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
		prevRecLocation = currLocation;
		
		Bundle flowbundle = getIntent().getExtras();
		String from = flowbundle.getString("from");

		// Check from where MapsAcitivy is called and then layout the overlays
		// accordingly.
		if (from.equalsIgnoreCase("tab")) {
			// To add all building markers on Map
			mapOverlays.add(new markerOverlay(drawable, this, BuildingItems
					.getBuildingItems()).getItemizedoverlay());

			// For default Arrow Direction TODO: Might want to comment this line after testing
			destLocation = new GeoPoint(37722734, -122478966);
		} else if (from.equalsIgnoreCase("mapitbutton")) {
			// Place Destination Marker

			// Find the right object to place Marker
			MyOverlayItem myItem = new MyOverlayItem();
			int index = Integer.parseInt(flowbundle.getString("index"));
			if (flowbundle.getString("type").equalsIgnoreCase("bldg"))
				myItem = BuildingItems.getBuildingItem(index);
			else if (flowbundle.getString("type").equalsIgnoreCase("dept"))
				myItem = DeptItems.getDeptItem(index);
			else if (flowbundle.getString("type").equalsIgnoreCase("cafe"))
				myItem = CafeItems.getCafeItem(index);
			else if (flowbundle.getString("type").equalsIgnoreCase("visit"))
				myItem = VisitItems.getVisitItem(index);
			
			// Add Destination Marker
			mapOverlays.add(new markerOverlay(drawable, this, myItem)
					.getItemizedoverlay());

			// Get Destination GeoLocation
			double dest_lat = Double
					.parseDouble(flowbundle.getString("geolat"));
			double dest_long = Double.parseDouble(flowbundle
					.getString("geolong"));
			destLocation = new GeoPoint((int) (dest_lat), (int) (dest_long));
			
//			ProgressDialog dialog = ProgressDialog.show(MapsActivity.this, "", 
//                    "Loading. Please wait...", true);
//			new DrawPathTask().execute(dialog);
			
//			 new Thread(new Runnable() {
//		            public void run() {
		            	ProgressDialog dialog = ProgressDialog.show(MapsActivity.this, "", 
		                        "Loading. Please wait...", true);
		            	// Draw Path from source to Destination
		    			DrawPath(currLocation, destLocation, Color.GREEN);
		    			dialog.cancel();
//		            }
//		          }).start();
			 
//			// Draw Path from source to Destination
//			DrawPath(currLocation, destLocation, Color.GREEN, mapView);
		}

		inBuilding = false;
		step = 0;
		
		mc = mapView.getController();
		mc.animateTo(currLocation);
		mc.setZoom(17);
		
		
	}
	
	public void rotateMap(View v) {
	    if(rMapView.isRotateEnabled())
	    	rMapView.setRotateEnabled(false);
	    else
	    	rMapView.setRotateEnabled(true);
	}
	
	 private class DrawPathTask extends AsyncTask<ProgressDialog, String, String> {
	     protected String doInBackground(ProgressDialog... dialog) {
	    	 DrawPath(currLocation, destLocation, Color.GREEN);
	    	 dialog[0].cancel();
	         return "Its Done!!1";
	     }

	     protected void onProgressUpdate(String... progress) {
	     }

	     protected void onPostExecute(String result) {
	         System.out.println(result);
	     }
	 }
//	public void QRScan(){
//    	Intent  intent = new Intent("com.google.zxing.client.android.SCAN");
//        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//        startActivityForResult(intent, 0);
//    }
//    
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//		System.out.println("Here");   
//		if (requestCode == 0) {
//		      if (resultCode == RESULT_OK) {
//		         String contents = intent.getStringExtra("SCAN_RESULT");
//		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
//		     	System.out.println(contents); 
////		         System.out.println(contents);
////		         Utils.toast(this, contents);
//		         // Handle successful scan
//		      } else if (resultCode == RESULT_CANCELED) {
//		    		System.out.println("Else"); 
//		         // Handle cancel
//		      }
//		   }
//		}
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, magnetometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	float[] mGravity;
	float[] mGeomagnetic;

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
//				azimut -= 30;			//Manual Adjustment as arrow is initially pointing on east.
//				System.out.println(azimut);
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

			try { // Exception handling if NextClosestGeoPoint is not set.
					// Especially in the case of MapTab.
				MyMappedPath.getClosestGeoPoint(gp1);
				gp2 = Utils.locToGeo(MyMappedPath
						.getNextClosestGeoPoint(gp1));
			} catch (NullPointerException e) {
				gp2 = destLocation;
			}
			Point point1 = new Point();
			Point point2 = new Point();

			// Get GeoLocation projects on plane and save it to Points.
			Projection projection = mapView.getProjection();
			projection.toPixels(gp1, point1);
			projection.toPixels(gp2, point2);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.arrow);
			
			//This angle if you want pointer to point where you 'should be' heading.
			// Find Angle between two planar points in radians
//			float angle = findAngle(point1, point2);
			
			//This angle if you want pointer to point where you 'are' heading.
//			float angle = azimut;
			
			// Put arrow in right location on Map and give it the calculated
			// angle
			Matrix matrix = new Matrix();
			matrix.postTranslate(-25, -25);
			matrix.postRotate(azimut);
			matrix.postTranslate(point1.x, point1.y);

			// Set paint parameters
			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);

			// Finally Draw it with calculated matrix
			canvas.drawBitmap(bmp, matrix, paint);
			
//			Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.needle);
//	        Matrix mat = new Matrix();
////	        matrix.postTranslate(-25, -25);
//	        mat.postRotate(azimut, bMap.getWidth()/2,bMap.getHeight()/2);
////	        matrix.postTranslate(25, 25);
////	        Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
//	        canvas.drawBitmap(bMap, mat, paint);
	        
	        if(rMapView.isRotateEnabled()){
	        	if(azimut>=0)
	        		rMapView.setCompassBearing(azimut);
	        	else
	        		rMapView.setCompassBearing(360+azimut);	//Because RotatedMapView 
	        }
//			llneedle.
//			ImageView image = new ImageView(getContext());
//			
//	        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.needle);
//	        Matrix mat = new Matrix();
//	        mat.postRotate(azimut);
//	        Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);
//	        image.setImageBitmap(bMapRotate);
//			llneedle.addView(image);

			return true;
		}

		private float findAngle(Point point1, Point point2) {
			double dlon = point2.x - point1.x;
			double dlat = point2.y - point1.y;

			// Formula 1
			// Keeping this formula to switch between accuracy and fast
			// response.
			// double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) +
			// Math.cos(gp1.getLatitudeE6()) * Math.cos(gp2.getLatitudeE6()) *
			// (Math.sin(dlon / 2) * Math.sin(dlon / 2));
			// double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

			// Formula 2
			double angle = Math.atan2(dlat, dlon);

			// Convert Degrees to Radians
			angle = angle * 180 / Math.PI;

			return (float) angle;
		}

	}
	

	private class MyLocationListener implements LocationListener {
		
		
		
		public void onLocationChanged(Location argLocation) {
			// TODO Auto-generated method stub
			currLocation = new GeoPoint(
					(int) (argLocation.getLatitude() * 1000000),
					(int) (argLocation.getLongitude() * 1000000));
			
			Location currloc = Utils.geoToLoc(currLocation);
			MyOverlayItem nearestBuilding;
			float distance = 0;
			
			if(!inBuilding){
				step = 0;
				distance = currloc.distanceTo(Utils.geoToLoc(prevRecLocation));
				distance /= 1E6;
				System.out.println(distance);
			}
			
			if(distance > 10 && !inBuilding)
			{
				nearestBuilding = BuildingItems.getNearestBuildingItem(currloc);
				distance = currloc.distanceTo(Utils.geoToLoc(nearestBuilding.getPoint()));
	//			  it will show a message on location change
	//			  Utils.alert(MapsActivity.getContext(), "New location latitude ["
	//			  +argLocation.getLatitude() + "] longitude [" +
	//			  argLocation.getLongitude()+"]");
				distance /= 1E6;
				System.out.println("I am in if loop");
				prevRecLocation = currLocation;
				
				if(distance < 1){
					inBuilding = true;
//					Utils.alert(MapsActivity.getContext(), "You have reached "+nearestBuilding.getTitle()
//							+ "Do you want to Navigate within the building?");
					Context context = MapsActivity.getContext();
					String message = "You have reached \""+nearestBuilding.getTitle()
					+ "\". Do you want to Navigate within the building?";
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage(message).setCancelable(false)
							.setPositiveButton("Navigate", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Utils.navalert(MapsActivity.getContext(),"Walk further to reach point "+RouteDictionary.getTHDictionary()[step++][0]);
								}
							});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									inBuilding = false;
									step = 0;
									dialog.cancel();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();
				}
//				Utils.toast(MapsActivity.getContext(), distance+" New location latitude ["
//						  +argLocation.getLatitude() + "] longitude [" +
//						  argLocation.getLongitude()+"]");
			} 
			else if(inBuilding){
				Location nextPoint = Utils.geoToLoc(
						new GeoPoint((int)(Double.parseDouble(RouteDictionary.getTHDictionary()[step][1])*1E6),
								(int)(Double.parseDouble(RouteDictionary.getTHDictionary()[step][2])*1E6)));
				distance = currloc.distanceTo(nextPoint);
				distance /= 1E6;
				System.out.println("In building:"+distance);
				if(distance < 0.5){
					Utils.navalert(MapsActivity.getContext(),"Walk further to reach point "+RouteDictionary.getTHDictionary()[step++][0]);
				}
				
				Utils.toast(MapsActivity.getContext(), "If you feel lost in the building, use Point It! " +
						"Tab button below to scan QR Code.");
			}
			
			
			System.out.println("Location is changed!!!");
			
			mc.animateTo(currLocation);
			mc.setZoom(17);

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

	private void DrawPath(GeoPoint src, GeoPoint dest, int color) {
		
		// Dijkstra Part
		new MyGeoPoint();
		System.out.println("Max Number");

		MyGeoPoint[] mygp = MyGeoPoint.getMygp();

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
		mapOverlays.add(new rideOverlay(startGP, startGP, 1));
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
			mapOverlays.add(new rideOverlay(gp1, gp2, 2, color));
//			new rideOverlay(gp1, gp2, 2, color);
			// Log.d("xxx","pair:" + pairs[i]);
			// Adding geopoints as location to mappedpath
			mappedpath.add(Utils.geoToLoc(gp2));
		}
		MyMappedPath.setMypath(mappedpath);

		mapOverlays.add(new rideOverlay(dest, dest, 3)); // use the
																		// default
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public static Context getContext() {
		return context;
	}

}
