package screen.level2;

import main.data.BuildingItems;
import main.overlay.MyOverlayItem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;
 
public class MyCampusBuildingActivity extends ListActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<MyOverlayItem>(this, R.layout.building_list, BuildingItems.getBuildingItems()));
    }
        
   	public void onListItemClick( ListView parent, View v, int position, long id)  {
       	 Intent intent = new Intent(MyCampusBuildingActivity.this, MyCampusDetailActivity.class);
         MyOverlayItem bldgItem = (MyOverlayItem) this.getListAdapter().getItem(position);
         intent.putExtra("index", bldgItem.getId()+"");
         intent.putExtra("type", "bldg");
         intent.putExtra("desc", bldgItem.getSnippet());
         intent.putExtra("geolat", bldgItem.getPoint().getLatitudeE6()+"");
         intent.putExtra("geolong", bldgItem.getPoint().getLongitudeE6()+"");
         startActivity(intent);
   	}
   	
   	public void listClickSimulation(int position)  {
   		//TODO: Polishing this code is left yet.
      	 Intent intent = new Intent(MyCampusBuildingActivity.this, MyCampusDetailActivity.class);
      	 System.out.println("Reached here safely?");
        MyOverlayItem bldgItem = (MyOverlayItem) this.getListAdapter().getItem(position);
        intent.putExtra("index", bldgItem.getId()+"");
        intent.putExtra("type", "bldg");
        intent.putExtra("desc", bldgItem.getSnippet());
        intent.putExtra("geolat", bldgItem.getPoint().getLatitudeE6()+"");
        intent.putExtra("geolong", bldgItem.getPoint().getLongitudeE6()+"");
        startActivity(intent);
  	}
}
    