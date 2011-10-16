package screen.level2;

import main.data.VisitItems;
import main.overlay.MyOverlayItem;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;

public class MyVisitActivity extends ListActivity {
 
      @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<MyOverlayItem>(this, R.layout.building_list, VisitItems.getVisitItems()));
    }
        
   	public void onListItemClick( ListView parent, View v, int position, long id)  {
	    Intent intent = new Intent(MyVisitActivity.this, MyCampusDetailActivity.class);
	    MyOverlayItem visitItem = (MyOverlayItem) this.getListAdapter().getItem(position);
	    intent.putExtra("index", visitItem.getId()+"");
        intent.putExtra("type", "visit");
        intent.putExtra("desc", visitItem.getSnippet());
        intent.putExtra("geolat", visitItem.getPoint().getLatitudeE6()+"");
        intent.putExtra("geolong", visitItem.getPoint().getLongitudeE6()+"");
        startActivity(intent);
   	} 
}
    
 