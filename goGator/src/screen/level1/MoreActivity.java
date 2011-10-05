package screen.level1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MoreActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textview = new TextView(this);
        textview.setText("Feeling Lost in your own campus? \n\nTired of losing your paper Maps or carrying it around? \n\nForgot where you parked your ride? \n\n\nHere’s Gator to your aid. Get the best Campus Navigation app –GoGator. \n\nWith this app you can find your way to any destination on campus be it the bursar's office or a cafe. \n\nThe app also lets you find the location of your vehicle, tells you the working hours of important offices etc.\n\n -By Nilay & Nikita");
        setContentView(textview);

    }

}
