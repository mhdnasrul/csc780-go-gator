package screen.level2;

import java.util.Collections;

import main.data.CafeItems;
import main.overlay.MyOverlayItem;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import screen.level3.MyCampusDetailActivity;
import screen.main.R;

public class MyCafeActivity extends ListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setListAdapter(new ArrayAdapter<MyOverlayItem>(this,
				R.layout.building_list, CafeItems.getCafeItems()));
	}

	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		Intent intent = new Intent(MyCafeActivity.this,
				MyCampusDetailActivity.class);
		MyOverlayItem cafeItem = (MyOverlayItem) this.getListAdapter().getItem(
				position);
		intent.putExtra("index", cafeItem.getId() + "");
		intent.putExtra("type", "cafe");
		intent.putExtra("desc", cafeItem.getSnippet());
		intent.putExtra("geolat", cafeItem.getPoint().getLatitudeE6() + "");
		intent.putExtra("geolong", cafeItem.getPoint().getLongitudeE6() + "");
		startActivity(intent);
	}
}