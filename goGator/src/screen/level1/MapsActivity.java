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
        	
        GeoPoint ADMpoint = new GeoPoint(37721274, -122476868);
        OverlayItem overlayitem = new OverlayItem(ADMpoint, "Administration Building", null);
        itemizedoverlay.addOverlay(overlayitem);
        
        GeoPoint Libpoint = new GeoPoint(37721435, -122477453);
        OverlayItem overlayitem2 = new OverlayItem(Libpoint, "SFSU Library!", null);
        itemizedoverlay.addOverlay(overlayitem2);
        
        GeoPoint Mcpoint = new GeoPoint(37721410, -122480020);
        OverlayItem overlayitem3 = new OverlayItem(Mcpoint, "McKenna Theatre", null);
        itemizedoverlay.addOverlay(overlayitem3);
        
        GeoPoint CCpoint = new GeoPoint(37722021, -122478550);
        OverlayItem overlayitem4 = new OverlayItem(CCpoint, "Ceasar Chavez", null);
        itemizedoverlay.addOverlay(overlayitem4);
        
        GeoPoint BUSpoint = new GeoPoint(37722091, -122476868);
        OverlayItem overlayitem5 = new OverlayItem(BUSpoint, "Business", null);
        itemizedoverlay.addOverlay(overlayitem5);
        
        GeoPoint HSSpoint = new GeoPoint(37721626, -122476584);
        OverlayItem overlayitem6 = new OverlayItem(HSSpoint, "HSS", null);
        itemizedoverlay.addOverlay(overlayitem6);
        
        GeoPoint HUMpoint = new GeoPoint(37722180, -122480910);
        OverlayItem overlayitem7 = new OverlayItem(HUMpoint, "Humanities", null);
        itemizedoverlay.addOverlay(overlayitem7);
        
        GeoPoint Vilpoint = new GeoPoint(37722991, -122480561);
        OverlayItem overlayitem8 = new OverlayItem(Vilpoint, "The Village", null);
        itemizedoverlay.addOverlay(overlayitem8);
        
        GeoPoint SSpoint = new GeoPoint(37723353, -122480532);
        OverlayItem overlayitem9 = new OverlayItem(SSpoint, "Student Services", null);
        itemizedoverlay.addOverlay(overlayitem9);
        
        GeoPoint THpoint = new GeoPoint(37724070, -122476876);
        OverlayItem overlayitem10 = new OverlayItem(THpoint, "Thornton Hall", null);
        itemizedoverlay.addOverlay(overlayitem10);
        
        GeoPoint HHpoint = new GeoPoint(377235210, -122475809);
        OverlayItem overlayitem11 = new OverlayItem(HHpoint, "Hensill Hall", null);
        itemizedoverlay.addOverlay(overlayitem11);
        
        GeoPoint SCIpoint = new GeoPoint(37722863, -122476262);
        OverlayItem overlayitem12 = new OverlayItem(SCIpoint, "Science Building", null);
        itemizedoverlay.addOverlay(overlayitem12);
        
        GeoPoint Burkpoint = new GeoPoint(37722734, -122478966);
        OverlayItem overlayitem13 = new OverlayItem(Burkpoint, "Burk Hall", null);
        itemizedoverlay.addOverlay(overlayitem13);
        
        GeoPoint FApoint = new GeoPoint(37722541, -122479776);
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