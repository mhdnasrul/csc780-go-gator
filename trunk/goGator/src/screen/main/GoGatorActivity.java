package screen.main;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GoGatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tab_host = (TabHost) findViewById(R.id.tab_host);
        tab_host.setup();
        Resources r = getResources();
        
        TabSpec tabspec1 = tab_host.newTabSpec("home");
        tabspec1.setIndicator("Home",r.getDrawable(R.drawable.ic_dialog_email));
        tabspec1.setContent(R.id.first_tab);
        tab_host.addTab(tabspec1);

        TabSpec tabspec2 = tab_host.newTabSpec("map");
        tabspec2.setIndicator("Map",r.getDrawable(R.drawable.ic_dialog_map));
        tabspec2.setContent(R.id.second_tab);
        tab_host.addTab(tabspec2);

        TabSpec tabspec3 = tab_host.newTabSpec("camera");
        tabspec3.setIndicator("Camera", r.getDrawable(R.drawable.ic_dialog_dialer));
        tabspec3.setContent(R.id.third_tab);
        tab_host.addTab(tabspec3);
        
        TabSpec tabspec4 = tab_host.newTabSpec("more");
        tabspec4.setIndicator("More", r.getDrawable(R.drawable.ic_dialog_info));
        tabspec4.setContent(R.id.fourth_tab);
        tab_host.addTab(tabspec4);

        tab_host.setCurrentTab(0);
    }
}
