// http://developer.android.com/resources/tutorials/views/hello-mapview.html
package screen.level1;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class mapOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	private Context context;
	
	public mapOverlay(Drawable defaultMarker) {
			  super(boundCenterBottom(defaultMarker));
			}
	
	public mapOverlay(Drawable defaultMarker, Context context) {
		  this(defaultMarker);
		  this.context = context;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}
		
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	} 
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    this.populate();
	}
	
}
