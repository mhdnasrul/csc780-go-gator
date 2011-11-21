package screen.level2;

import gatorDB.gatorDBhelper;

import java.util.ArrayList;

import main.overlay.MyOverlayItem;

import screen.level3.MyCampusDetailActivity;
import screen.main.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyCafeActivity extends ListActivity {

    private ArrayList<String> results = new ArrayList<String>();
	private String tableName = gatorDBhelper.tableName;
	private SQLiteDatabase newDB;
 
     public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openAndQueryDatabase();
        displayResultList();
     }
	 
	 private void displayResultList() {
		       
        setListAdapter(new ArrayAdapter<String>(this,
                R.layout.building_list, results));
        getListView().setTextFilterEnabled(true);
		
	}
	
		private void openAndQueryDatabase() {
		try {
			gatorDBhelper dbHelper = new gatorDBhelper(this.getApplicationContext());
			newDB = dbHelper.getWritableDatabase();
			Cursor c = newDB.rawQuery("SELECT Name FROM " +
	    			tableName +
	    			" where Type = 'Cafe'", null);

	    	if (c != null ) {
	    		if  (c.moveToFirst()) {
	    			do {
	    				String Name = c.getString(c.getColumnIndex("Name"));
	    				results.add(Name);
	    			}while (c.moveToNext());
	    		} 
	    	}			
		} catch (SQLiteException se ) {
        	Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
        	if (newDB != null) 
        		newDB.execSQL("DELETE FROM " + tableName);
        		newDB.close();
        }

	}

		public void onListItemClick(){}
	
    	@Override
        public void onListItemClick( ListView parent, View v, int position, long id)  {
           Intent intent = new Intent(MyCafeActivity.this, MyCampusDetailActivity.class);
           MyOverlayItem cafeItem = (MyOverlayItem) this.getListAdapter().getItem(position);
           intent.putExtra("index", cafeItem.getId()+"");
           intent.putExtra("type", "cafe");
           intent.putExtra("desc", cafeItem.getSnippet());
           intent.putExtra("geolat", cafeItem.getPoint().getLatitudeE6()+"");
           intent.putExtra("geolong", cafeItem.getPoint().getLongitudeE6()+"");
           startActivity(intent);
        } 
}