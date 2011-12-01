package screen.level1.overlay;

import java.util.ArrayList;

import screen.level1.MapsActivity;
import main.overlay.MyOverlayItem;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.google.android.maps.MapView;

public class mapOverlay extends BalloonItemizedOverlay<MyOverlayItem> {

	private ArrayList<MyOverlayItem> mOverlays = new ArrayList<MyOverlayItem>();
	private MapsActivity mapActivity;
	private MapView mapView;

	public mapOverlay(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
	}
	
	public mapOverlay(Drawable defaultMarker, MapView mapView, MapsActivity mapAct) {
		super(boundCenter(defaultMarker), mapView);
		mapActivity = mapAct;
		this.mapView = mapView;
	}

	@Override
	protected MyOverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}


	public void addOverlay(MyOverlayItem overlay) {
		mOverlays.add(overlay);
		this.populate();
	}
	
	@Override
	protected boolean onBalloonTap(int index, MyOverlayItem item) {
		mapActivity.DrawPath(mapActivity.currLocation, item.getPoint(), Color.GREEN);
		mapView.getOverlays().remove(1);
		return true;
	}

}
