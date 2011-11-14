package screen.level1;

import com.google.android.maps.GeoPoint;

import main.common.Utils;
import main.routing.algo.RouteDictionary;
import screen.level2.MyCafeActivity;
import screen.level2.MyCampusActivity;
import screen.level2.MyRideActivity;
import screen.level2.MyVisitActivity;
import screen.main.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
	private static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.hometab);
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
//		Intent intent = new Intent(this, MyVisitActivity.class);
//		startActivity(intent);	
    	Intent  intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
	}
    
    public void MyRide(View view){
		Intent intent = new Intent(this, MyRideActivity.class);
		startActivity(intent);	
	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
		      if (resultCode == RESULT_OK) {
		         String contents = intent.getStringExtra("SCAN_RESULT");
		         String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		     	System.out.println(contents); 
		     	String[] values = contents.split(",");
		     	int step = Integer.parseInt(values[0]);
		     	
		     	if(step == 0){
			     	Context context = MapsActivity.getContext();
					String message = "You have reached \"Thornton Hall\". Do you want to Navigate within the building?";
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage(message).setCancelable(false)
							.setPositiveButton("Navigate", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									Utils.navalert(HomeActivity.context,"Walk further to reach point "+RouteDictionary.getTHDictionary()[0][0]);
								}
							});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();
		     	}
		     	else{
						Utils.navalert(HomeActivity.context,"Walk further to reach point "+RouteDictionary.getTHDictionary()[step][0]);
		     	}
		         // Handle successful scan
		      } else if (resultCode == RESULT_CANCELED) {
		    		System.out.println("Else"); 
		         // Handle cancel
		      }
		   }
		}
}
