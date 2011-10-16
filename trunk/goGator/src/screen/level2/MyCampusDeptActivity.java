package screen.level2;

import main.data.DeptItems;
import main.overlay.MyOverlayItem;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;
 
public class MyCampusDeptActivity extends ListActivity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<MyOverlayItem>(this, R.layout.building_list, DeptItems.getDeptItems()));
    }
        
   	public void onListItemClick( ListView parent, View v, int position, long id)  {
	    Intent intent = new Intent(MyCampusDeptActivity.this, MyCampusDetailActivity.class);
	    MyOverlayItem deptItem = (MyOverlayItem) this.getListAdapter().getItem(position);
	    intent.putExtra("index", deptItem.getId()+"");
        intent.putExtra("type", "dept");
        intent.putExtra("desc", deptItem.getSnippet());
	    intent.putExtra("geolat", deptItem.getPoint().getLatitudeE6()+"");
	    intent.putExtra("geolong", deptItem.getPoint().getLongitudeE6()+"");
	     startActivity(intent);
    } 
}
    
 