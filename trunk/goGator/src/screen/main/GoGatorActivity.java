package screen.main;

import screen.level1.CameraActivity;
import screen.level1.HomeActivity;
import screen.level1.MapsActivity;
import screen.level1.MoreActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class GoGatorActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, HomeActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("home").setIndicator("Home",
                          res.getDrawable(R.drawable.ic_home))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, MapsActivity.class);
        spec = tabHost.newTabSpec("map").setIndicator("Map",
                          res.getDrawable(R.drawable.ic_map))
                      .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, CameraActivity.class);
        spec = tabHost.newTabSpec("camera").setIndicator("Point It!",
                          res.getDrawable(R.drawable.ic_camera))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, MoreActivity.class);
        spec = tabHost.newTabSpec("more").setIndicator("About Us!",
                          res.getDrawable(R.drawable.ic_info))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
     
    }
}
