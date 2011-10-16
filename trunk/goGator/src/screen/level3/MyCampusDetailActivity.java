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
    private String geolat;
    private String geolong;
    private String index;
    private String type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.desc);
        Bundle cafeBundle = getIntent().getExtras();
        String desc = cafeBundle.getString("desc");
        geolat = cafeBundle.getString("geolat");
        geolong = cafeBundle.getString("geolong");
        index = cafeBundle.getString("index");
        type = cafeBundle.getString("type");
        TextView  desctv = (TextView) findViewById(R.id.descTextView);
        desctv.setText(desc);
	}

    public void mapIt(View view){
		Intent intent = new Intent(this, MapsActivity.class);
		intent.putExtra("from", "mapitbutton");
		intent.putExtra("index", index);
        intent.putExtra("type", type);
		intent.putExtra("geolat", geolat);
		intent.putExtra("geolong", geolong);
		startActivity(intent);
		finish();
	}

}
