/** Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package screen.level1;

import screen.main.R;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
public class MapsActivity extends MapActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maptab);

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.flag);
        mapOverlay itemizedoverlay = new mapOverlay(drawable, this);
        	
        GeoPoint ADMpoint = new GeoPoint(37721140, -122476450);
        OverlayItem overlayitem = new OverlayItem(ADMpoint, "Administration Building", null);
        itemizedoverlay.addOverlay(overlayitem);
        
        GeoPoint Libpoint = new GeoPoint(37721350, -122478190);
        OverlayItem overlayitem2 = new OverlayItem(Libpoint, "SFSU Library!", null);
        itemizedoverlay.addOverlay(overlayitem2);
        
        GeoPoint Mcpoint = new GeoPoint(37721410, -122480020);
        OverlayItem overlayitem3 = new OverlayItem(Mcpoint, "McKenna Theatre", null);
        itemizedoverlay.addOverlay(overlayitem3);
        
        GeoPoint CCpoint = new GeoPoint(37722280, -122478890);
        OverlayItem overlayitem4 = new OverlayItem(CCpoint, "Ceasar Chavez", null);
        itemizedoverlay.addOverlay(overlayitem4);
        
        GeoPoint BUSpoint = new GeoPoint(37722030, -122476770);
        OverlayItem overlayitem5 = new OverlayItem(BUSpoint, "Business", null);
        itemizedoverlay.addOverlay(overlayitem5);
        
        GeoPoint HSSpoint = new GeoPoint(37721960, -122476040);
        OverlayItem overlayitem6 = new OverlayItem(HSSpoint, "HSS", null);
        itemizedoverlay.addOverlay(overlayitem6);
        
        GeoPoint HUMpoint = new GeoPoint(37722520, -122480930);
        OverlayItem overlayitem7 = new OverlayItem(HUMpoint, "Humanities", null);
        itemizedoverlay.addOverlay(overlayitem7);
        
        GeoPoint Vilpoint = new GeoPoint(37722990, -122480790);
        OverlayItem overlayitem8 = new OverlayItem(Vilpoint, "The Village", null);
        itemizedoverlay.addOverlay(overlayitem8);
        
        GeoPoint SSpoint = new GeoPoint(37723430, -122480630);
        OverlayItem overlayitem9 = new OverlayItem(SSpoint, "Student Services", null);
        itemizedoverlay.addOverlay(overlayitem9);
        
        GeoPoint THpoint = new GeoPoint(37724080, -122476910);
        OverlayItem overlayitem10 = new OverlayItem(THpoint, "Thornton Hall", null);
        itemizedoverlay.addOverlay(overlayitem10);
        
        GeoPoint HHpoint = new GeoPoint(37723510, -122476440);
        OverlayItem overlayitem11 = new OverlayItem(HHpoint, "Hensill Hall", null);
        itemizedoverlay.addOverlay(overlayitem11);
        
        GeoPoint SCIpoint = new GeoPoint(37722940, -122476100);
        OverlayItem overlayitem12 = new OverlayItem(SCIpoint, "Science Building", null);
        itemizedoverlay.addOverlay(overlayitem12);
        
        GeoPoint Burkpoint = new GeoPoint(37722780, -122479130);
        OverlayItem overlayitem13 = new OverlayItem(Burkpoint, "Burk Hall", null);
        itemizedoverlay.addOverlay(overlayitem13);
        
        GeoPoint FApoint = new GeoPoint(37722490, -122478010);
        OverlayItem overlayitem14 = new OverlayItem(FApoint, "Fine Arts", null);
        itemizedoverlay.addOverlay(overlayitem14);
                
        mapOverlays.add(itemizedoverlay);
		
		MapController mapController = mapView.getController();
		
		mapController.animateTo(CCpoint);
		mapController.setZoom(17);
        
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

}