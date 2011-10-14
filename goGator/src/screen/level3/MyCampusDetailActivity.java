package screen.level3;

import screen.level1.MapsActivity;
import screen.main.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.EditText;

public class MyCampusDetailActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        TextView textview = new TextView(this);
//        textview.setText("This is the My Campus Detail tab");
        setContentView(R.layout.desc);
        
        Bundle cafeBundle = getIntent().getExtras();
        String value2 = cafeBundle.getString("keyword");
      //  EditText text2 = (EditText) findViewById(R.id.mycafeEditText);
        TextView  text2 = (TextView) findViewById(R.id.descTextView);
        text2.setText(value2);
		
	}

    public void mapIt(View view){
		Intent intent = new Intent(this, MapsActivity.class);
		startActivity(intent);	
	}

}
