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
 
public class MyCampusDeptActivity extends ListActivity {
 
    final private static String[] DEPT = { "CS Dept", "EE Dept", "Business Dept", "Arts Dept" };
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        FrameLayout frame = (FrameLayout) findViewById(R.id.tabcontent);
//        frame.removeAllViews();
        

        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.building_list, DEPT));
        
        final ListView listView = getListView();
//        frame.addView(listView); 
        listView.setOnItemClickListener(new OnItemClickListener() {
 
            /**
             * Callback that will be invoked whenever an item from the list has
             * been selected. 'parent' is the AdapterView where the click
             * happened. 'view' is the view within the AdapterView that was
             * clicked (this will be a view provided by the adapter). 'position'
             * is the position of the view in the adapter. 'id' is the row id of
             * the item that was clicked.
             */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                /**
//                 * Whenever an item of the ListActivity is selected, show an
//                 * AlertDialog to inform which item was selected. The way the
//                 * AlertDialog is built up is an example of a fluent interface.
//                 */
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyCampusActivity.this);
//                builder.setTitle("Selection").setMessage(((TextView) view).getText())
//                        .setCancelable(false).setPositiveButton("Ok", null);
//                /**
//                 * Create and show the model AlertDialog.
//                 */
//                builder.create().show();
            	launchDesc(listView);
            }
 
        });
 
    }
    
    public void launchDesc(View view){
		Intent intent = new Intent(this, MyCampusDetailActivity.class);
		startActivity(intent);	
	}
}