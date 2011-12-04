package screen.level1;

import screen.level2.MyCafeActivity;
import screen.level2.MyCampusActivity;
import screen.level2.MyRideActivity;
import screen.level2.MyVisitActivity;
import screen.main.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	private float[] mGravity;
	private float[] mGeomagnetic;
	private float azimut;
	private Canvas canvas;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hometab);
        
     // Register the sensor listeners
     		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
     		accelerometer = mSensorManager
     				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
     		magnetometer = mSensorManager
     				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
     		canvas = new Canvas();
    }
  
    public void MyCampus(View view){
		Intent intent = new Intent(this, MyCampusActivity.class);
		startActivity(intent);	
	}
    
    public void MyCafe(View view){
		Intent intent = new Intent(this, MyCafeActivity.class);
		startActivity(intent);	
	}
    
    public void MyVisit(View view){
		Intent intent = new Intent(this, MyVisitActivity.class);
		startActivity(intent);	
	}
    
    public void MyRide(View view){
		Intent intent = new Intent(this, MyRideActivity.class);
		startActivity(intent);	
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
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
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
		
//		Bitmap bMap = BitmapFactory.decodeResource(getResources(),
//				 R.drawable.wheel);
//				 Matrix mat = new Matrix();
//				 mat.postTranslate(-25, -25);
//				 if (azimut >= 0)
//					 mat.postRotate(azimut, bMap.getWidth()/2,bMap.getHeight()/2);
//				 else
//					 mat.postRotate(360+azimut, bMap.getWidth()/2,bMap.getHeight()/2);
//				 mat.postTranslate(25, 25);
//				// Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0,
//				 //bMap.getWidth(), bMap.getHeight(), mat, true);
//				 canvas.drawBitmap(bMap, mat, new Paint());
	}
      
}
