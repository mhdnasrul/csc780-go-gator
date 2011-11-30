package screen.level1;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.Size;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import screen.main.R;

import java.io.IOException;
import java.util.List;

import com.google.android.maps.GeoPoint;

import main.common.Utils;
import main.data.BuildingItems;
import main.routing.algo.RouteDictionary;

//import screen.main.SocketCamera;

// ----------------------------------------------------------------------

public class CameraActivity extends Activity implements SensorEventListener {
	private Preview mPreview;
	private LayoutInflater controlInflater = null;
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float azimut;
	private LocationManager lm;
	private LocationListener ll;
	private String provider;
	private GeoPoint currLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Create our Preview view and set it as the content of our activity.
		mPreview = new Preview(this);
		setContentView(mPreview);
		controlInflater = LayoutInflater.from(getBaseContext());
		View viewControl = controlInflater.inflate(R.layout.camera, null);
		LayoutParams layoutParamsControl = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.addContentView(viewControl, layoutParamsControl);
		
		/******************** For Magnetometer *********************/
		// Register the sensor listeners
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		magnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		/***********************************************************/

		/************************* For GPS ************************/
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
		/**********************************************************/
	}

	// Point And Tell Image Click
	public void tellMe(View v) {
		try {
			Utils.toast(
					this,
					"Azimut=" + azimut + "\nLatitude="
							+ currLocation.getLatitudeE6() + "\nLongitude="
							+ currLocation.getLongitudeE6());
		} catch (Exception e) {
			Utils.toast(this,
					"How would I know? I am still in Beta Version!! :( ");
		}
	}
	
	// Tell me nearest Building
	public void nearestToMe(View v) {
		try {
			Location currLoc = Utils.geoToLoc(currLocation);
			Utils.toast(
					this,
					"Nearest building is "+ BuildingItems.getNearestBuildingItem(currLoc).getTitle());
		} catch (Exception e) {
			Utils.toast(this,
					"How would I know? I am still in Beta Version!! :( ");
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	protected void onResume() {
		super.onResume();
		// Re-catch Magnetometer on Resume
		mSensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, magnetometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	protected void onPause() {
		super.onPause();
		// Unregister Sensor
		mSensorManager.unregisterListener(this);
	}

	@Override
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

	public class MyLocationListener implements LocationListener {

		public MyLocationListener() {
			// TODO Auto-generated constructor stub
		}

		public void onLocationChanged(Location argLocation) {
			currLocation = new GeoPoint(
					(int) (argLocation.getLatitude() * 1000000),
					(int) (argLocation.getLongitude() * 1000000));
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	}

}

// ----------------------------------------------------------------------

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	SurfaceHolder mHolder;
	Camera mCamera;

	Preview(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90); // To keep it tight on Portrait mode.
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException exception) {
			mCamera.release();
			mCamera = null;
			// TODO: add more exception handling logic here
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = mCamera.getParameters();

		List<Size> sizes = parameters.getSupportedPreviewSizes();
		Size optimalSize = getOptimalPreviewSize(sizes, w, h);
		parameters.setPreviewSize(optimalSize.width, optimalSize.height);

		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

}
