package screen.level1.overlay;

import java.util.ArrayList;

import screen.level2.MyCampusBuildingActivity;
import screen.level3.MyCampusDetailActivity;

import main.overlay.MyOverlayItem;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class mapOverlay extends BalloonItemizedOverlay<MyOverlayItem> {

	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();

	private Context context;

//	public mapOverlay(Drawable defaultMarker) {
//		super(boundCenterBottom(defaultMarker));
//	}

//	public mapOverlay(Drawable defaultMarker, Context context) {
//		this(defaultMarker);
//		this.context = context;
//	}
	
	public mapOverlay(Drawable defaultMarker, Context context, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		context = mapView.getContext();
	}

	@Override
	protected MyOverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

//	@Override
//	protected boolean onTap(int index) {
//		OverlayItem item = mOverlays.get(index);
//		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//		dialog.setTitle(item.getTitle());
//		dialog.setMessage(item.getSnippet());
////		dialog.setPositiveButton("Navigate!!", new DialogInterface.OnClickListener() {
////	           public void onClick(DialogInterface interface_dialog, int id) {
////	        	 //TODO: Polishing this code is left yet.
//////	        	   new MyCampusBuildingActivity().listClickSimulation(0);
////	           }
////	       });
//	    dialog.setNegativeButton("OK!", new DialogInterface.OnClickListener() {
//	           public void onClick(DialogInterface interface_dialog, int id) {
//	        	   interface_dialog.cancel();
//	           }
//	       });
//		dialog.show();
//		return true;
//	}
//	
	public void addOverlay(MyOverlayItem overlay) {
		mOverlays.add(overlay);
		this.populate();
	}

}
