package screen.level1.overlay;

import java.util.ArrayList;

import main.data.BuildingItems;
import main.overlay.MyOverlayItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class markerOverlay {
	private mapOverlay itemizedoverlay;
	
	public markerOverlay(Drawable defaultMarker, Context context, MyOverlayItem myItem){
		this.itemizedoverlay = new mapOverlay(defaultMarker, context);
			itemizedoverlay.addOverlay(myItem);
	}
	
	public markerOverlay(Drawable defaultMarker, Context context, ArrayList<MyOverlayItem> myItems){
		this.itemizedoverlay = new mapOverlay(defaultMarker, context);
		for(MyOverlayItem myItem: myItems)
			itemizedoverlay.addOverlay(myItem);
	}

	/**
	 * @param itemizedoverlay the itemizedoverlay to set
	 */
	public void setItemizedoverlay(mapOverlay itemizedoverlay) {
		this.itemizedoverlay = itemizedoverlay;
	}

	/**
	 * @return the itemizedoverlay
	 */
	public mapOverlay getItemizedoverlay() {
		return itemizedoverlay;
	}
}
