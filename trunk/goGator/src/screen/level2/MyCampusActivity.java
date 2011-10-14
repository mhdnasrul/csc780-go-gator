package screen.level2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import screen.level1.CameraActivity;
import screen.level1.HomeActivity;
import screen.level1.MapsActivity;
import screen.level1.MoreActivity;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;
 
public class MyCampusActivity extends TabActivity {
 
    final private static String[] BUILDINGS = { "ADM", "BUS", "SCI", "TH" };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	        super.onCreate(savedInstanceState);
    	        setContentView(R.layout.main);
    	        
    	        Resources res = getResources(); // Resource object to get Drawables
    	        TabHost tabHost = getTabHost();  // The activity TabHost
    	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
    	        Intent intent;  // Reusable Intent for each tab

    	        // Create an Intent to launch an Activity for the tab (to be reused)
    	        intent = new Intent().setClass(this, MyCampusBuildingActivity.class);

    	        // Initialize a TabSpec for each tab and add it to the TabHost
    	        spec = tabHost.newTabSpec("building").setIndicator("Building",
    	                          res.getDrawable(R.drawable.ic_building))
    	                      .setContent(intent);
    	        tabHost.addTab(spec);

    	        // Do the same for the other tabs
    	        intent = new Intent().setClass(this, MyCampusDeptActivity.class);
    	        spec = tabHost.newTabSpec("dept").setIndicator("Department",
    	                          res.getDrawable(R.drawable.ic_department))
    	                      .setContent(intent);
    	        tabHost.addTab(spec);



    	        tabHost.setCurrentTab(0);
    	     
    	    }
}