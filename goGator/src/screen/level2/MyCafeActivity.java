package screen.level2;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import screen.level1.MoreActivity;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;
import android.widget.ArrayAdapter;
import android.widget.Toast;

 
public class MyCafeActivity extends ListActivity {
 
     public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		String[] BUILDINGS = new String[] { "Cafe 1", "Cafe 2", "Cafe 3", "Cafe 4" };
		
       setListAdapter(new ArrayAdapter<String>(this, R.layout.building_list, BUILDINGS));
     }
       // final ListView listView = getListView();
        
        /**	@Override
        	protected void onListItemClick(ListView l, View v, int position, long id) {
        		super.onListItemClick(l, v, position, id);
        		// Get the item that was clicked
        		Object o = this.getListAdapter().getItem(position);
        		String keyword = o.toString();
        		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG).show();
        	} */
        	@Override
            public void onListItemClick( ListView parent, View v, int position, long id)  {
               Intent intent = new Intent(MyCafeActivity.this, MyCampusDetailActivity.class);
                Object o = this.getListAdapter().getItem(position);
              //  String keyword = o.toString();
                intent.putExtra("keyword", o.toString());
                startActivity(intent);
             //   startActivityForResult(intent, keyword);
                
             //   Intent myIntent = new Intent(v.getContext(), MyCampusDetailActivity.class);
             //   startActivityForResult(myIntent, 0);
             //   Toast.makeText(this,keyword, Toast.LENGTH_LONG).show();

                
                finish();
            } 
    }
  /**  
    public void launchDesc(View view){
		Intent intent = new Intent(this, MyCampusDetailActivity.class);
		startActivity(intent);	
	}
}*/