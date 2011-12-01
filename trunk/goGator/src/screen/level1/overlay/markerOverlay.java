package screen.level1.overlay;

import java.util.ArrayList;

import screen.level1.MapsActivity;

import main.overlay.MyOverlayItem;

import com.google.android.maps.MapView;
import android.graphics.drawable.Drawable;

public class markerOverlay {
	private mapOverlay itemizedoverlay;
	
	public markerOverlay(Drawable defaultMarker, MyOverlayItem myItem, MapView mapView){
		this.itemizedoverlay = new mapOverlay(defaultMarker, mapView);
			itemizedoverlay.addOverlay(myItem);
	}
	
	public markerOverlay(Drawable defaultMarker, ArrayList<MyOverlayItem> myItems, MapView mapView){
		this.itemizedoverlay = new mapOverlay(defaultMarker, mapView);
		for(MyOverlayItem myItem: myItems)
			itemizedoverlay.addOverlay(myItem);
	}
	
	public markerOverlay(Drawable defaultMarker, ArrayList<MyOverlayItem> myItems, MapView mapView, MapsActivity mapAct){
		this.itemizedoverlay = new mapOverlay(defaultMarker, mapView, mapAct);
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
