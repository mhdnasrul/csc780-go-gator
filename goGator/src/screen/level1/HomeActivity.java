package screen.level1;

import screen.level2.MyCafeActivity;
import screen.level2.MyCampusActivity;
import screen.level2.MyRideActivity;
import screen.level2.MyVisitActivity;
import screen.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
		Intent intent = new Intent(this, MyVisitActivity.class);
		startActivity(intent);	
	}
    
    public void MyRide(View view){
		Intent intent = new Intent(this, MyRideActivity.class);
		startActivity(intent);	
	}
      
}
