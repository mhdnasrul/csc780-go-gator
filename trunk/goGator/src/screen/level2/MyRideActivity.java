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

package screen.level2;

import java.util.List;

import screen.main.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This application uses the Google API to display a map of San Francisco. This
 * API is not part of the core Android API but is Google-specific. The Google
 * API offers its own MapActivity from which the application can be derived.
 * Note that certain permissions need to be set in AndroidManifest.xml to make
 * this application work. Furthermore, it is required that every application
 * using the Google Maps API has to register with Google to obtain a Map API
 * Key. The key needs to be included in the android:apiKey attribute of the
 * layout resource referencing the map. See the following link for details:
 * http://code.google.com/android/add-ons/google-apis/mapkey.html
 */
public class MyRideActivity extends com.google.android.maps.MapActivity {
        /** Called when the activity is first created. */

         private MapView mapView;
         private LocationManager lm;
         private LocationListener ll;
         private MapController mc;
         GeoPoint p = new GeoPoint(37723410, -122478930);
         Drawable defaultMarker = null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.maptab);

            mapView = (MapView)findViewById(R.id.mapview);
            //show zoom in/out buttons
            mapView.setBuiltInZoomControls(true);
            //Standard view of the map(map/sat)
            mapView.setSatellite(false);
            //get controller of the map for zooming in/out
            mc = mapView.getController();
            // Zoom Level
            mc.setZoom(18); 

            MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
            List<Overlay> list = mapView.getOverlays();
            list.add(myLocationOverlay);

            lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            ll = new MyLocationListener();

            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    ll);

            //Get the current location in start-up
            p = new GeoPoint(
                   (int)(lm.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER)
                    .getLatitude()*1000000),
                   (int)(lm.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER)
                    .getLongitude()*1000000));
                   mc.animateTo(p);







        }






            protected class MyLocationOverlay extends com.google.android.maps.Overlay {

                @Override
                public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
                    Paint paint = new Paint();

                    super.draw(canvas, mapView, shadow);
                    // Converts lat/lng-Point to OUR coordinates on the screen.
                    Point myScreenCoords = new Point();

                    mapView.getProjection().toPixels(p, myScreenCoords);

                    paint.setStrokeWidth(1);
                    paint.setARGB(255, 255, 255, 255);
                    paint.setStyle(Paint.Style.STROKE);

                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pointer);

                    canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
                   // canvas.drawText("I am here...", myScreenCoords.x, myScreenCoords.y, paint);
                    return true;
                }
            }


        private class MyLocationListener implements LocationListener{

              public void onLocationChanged(Location argLocation) {
               // TODO Auto-generated method stub
               p = new GeoPoint(
                (int)(argLocation.getLatitude()*1000000),
                (int)(argLocation.getLongitude()*1000000));
               /*
                * it will show a message on 
                * location change
               Toast.makeText(getBaseContext(),
                       "New location latitude [" +argLocation.getLatitude() +
                       "] longitude [" + argLocation.getLongitude()+"]",
                       Toast.LENGTH_SHORT).show();
                */

               mc.animateTo(p);

              }

              public void onProviderDisabled(String provider) {
               // TODO Auto-generated method stub
              }

              public void onProviderEnabled(String provider) {
               // TODO Auto-generated method stub
              }

              public void onStatusChanged(String provider,
                int status, Bundle extras) {
               // TODO Auto-generated method stub
              }
             }    
        protected boolean isRouteDisplayed() {
            return false;
        }
}