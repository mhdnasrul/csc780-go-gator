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

import screen.level1.overlay.markerOverlay;
import screen.level2.overlay.rideOverlay;
import screen.main.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.data.BuildingItems;
import main.data.CafeItems;
import main.data.DeptItems;
import main.data.VisitItems;
import main.overlay.MyOverlayItem;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapsActivity extends MapActivity {
	
	private LocationManager lm;
    private LocationListener ll;
    private MapController mc;
    public GeoPoint currLocation;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maptab);

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.flag);
        
         //To place current Location marker on Map
		 MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		 mapOverlays.add(myLocationOverlay);
		 
         lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         ll = new MyLocationListener();
         lm.requestLocationUpdates(
                 LocationManager.GPS_PROVIDER,
                 0,
                 0,
                 ll);

         //Get the current location in start-up
         currLocation = new GeoPoint(
                (int)(lm.getLastKnownLocation(
                 LocationManager.GPS_PROVIDER)
                 .getLatitude()*1000000),
                (int)(lm.getLastKnownLocation(
                 LocationManager.GPS_PROVIDER)
                 .getLongitude()*1000000));
         
         
         Bundle flowbundle = getIntent().getExtras();
         String from = flowbundle.getString("from");
         
         //Check from where MapsAcitivy is called and then layout the overlays accordingly.
         if(from.equalsIgnoreCase("tab")){
        	 //To add all building markers on Map
        	 mapOverlays.add(new markerOverlay(drawable,this,BuildingItems.getBuildingItems()).getItemizedoverlay());
         }
         else if(from.equalsIgnoreCase("mapitbutton")){
        	 //Place Destination Marker
        	 
        	 //Find the right object to place Marker
        	 MyOverlayItem myItem = new MyOverlayItem();
        	 int index = Integer.parseInt(flowbundle.getString("index"));
        	 if(flowbundle.getString("type").equalsIgnoreCase("bldg"))
        		 myItem = BuildingItems.getBuildingItem(index);
        	 else if(flowbundle.getString("type").equalsIgnoreCase("dept"))
        		 myItem = DeptItems.getDeptItem(index);
        	 else if(flowbundle.getString("type").equalsIgnoreCase("cafe"))
        		 myItem = CafeItems.getCafeItem(index);
        	 else if(flowbundle.getString("type").equalsIgnoreCase("visit"))
        		 myItem = VisitItems.getVisitItem(index);
        	 //Add Destination Marker
        	 mapOverlays.add(new markerOverlay(drawable,this,myItem).getItemizedoverlay());
        	 
        	 //Get GeoLocation
        	 double dest_lat =  Double.parseDouble(flowbundle.getString("geolat")); 
	         double dest_long = Double.parseDouble(flowbundle.getString("geolong")); 
	         GeoPoint destGeoPoint = new GeoPoint((int) (dest_lat),(int) (dest_long));
	         
	         //Draw Path from source to Destination
	         DrawPath(currLocation, destGeoPoint, Color.GREEN, mapView); 
         }
         
         mc = mapView.getController();
         mc.animateTo(currLocation);
         mc.setZoom(17);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    public class MyLocationOverlay extends com.google.android.maps.Overlay {

        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
            Paint paint = new Paint();

            super.draw(canvas, mapView, shadow);
            // Converts lat/lng-Point to OUR coordinates on the screen.
            Point myScreenCoords = new Point();

            mapView.getProjection().toPixels(currLocation, myScreenCoords);

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
      	  currLocation = new GeoPoint(
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

         mc.animateTo(currLocation);
         mc.setZoom(17);

        }
        public void onProviderDisabled(String provider) {
            // Auto-generated method stub
           }

           public void onProviderEnabled(String provider) {
            // Auto-generated method stub
           }

           public void onStatusChanged(String provider,
             int status, Bundle extras) {
            // Auto-generated method stub
           }
    }
    
    private void DrawPath(GeoPoint src,GeoPoint dest, int color, MapView mMapView01){ 
		// connect to map web service 
		StringBuilder urlString = new StringBuilder(); 
		urlString.append("http://maps.google.com/maps?f=d&hl=en"); 
		urlString.append("&saddr=");//from 
		urlString.append( Double.toString((double)src.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)src.getLongitudeE6()/1.0E6 )); 
		urlString.append("&daddr=");//to 
		urlString.append( Double.toString((double)dest.getLatitudeE6()/1.0E6 )); 
		urlString.append(","); 
		urlString.append( Double.toString((double)dest.getLongitudeE6()/1.0E6 )); 
		urlString.append("&dirflg=w&ie=UTF8&0&om=0&output=kml"); 
	//	Log.d("xxx","URL="+urlString.toString()); 
		// get the kml (XML) doc. And parse it to get the coordinates(direction route). 
		Document doc = null; 
		HttpURLConnection urlConnection= null; 
		URL url = null; 
		try{ 
			url = new URL(urlString.toString()); 
			urlConnection=(HttpURLConnection)url.openConnection(); 
			urlConnection.setRequestMethod("GET"); 
			urlConnection.setDoOutput(true); 
			urlConnection.setDoInput(true); 
			urlConnection.connect(); 
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			doc = db.parse(urlConnection.getInputStream()); 
		
			if(doc.getElementsByTagName("GeometryCollection").getLength()>0) 
			{ 
				//String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getNodeName(); 
				String path = doc.getElementsByTagName("GeometryCollection").item(0).getFirstChild().getFirstChild().getFirstChild().getNodeValue() ; 
				//	Log.d("xxx","path="+ path); 
				String [] pairs = path.split(" "); 
				String[] lngLat = pairs[0].split(","); // lngLat[0]=longitude lngLat[1]=latitude lngLat[2]=height 
				// src 
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
				mMapView01.getOverlays().add(new rideOverlay(startGP,startGP,1)); 
				GeoPoint gp1; 
				GeoPoint gp2 = startGP; 
				for(int i=1;i<pairs.length;i++){ // the last one would be crash 
					lngLat = pairs[i].split(","); 
					gp1 = gp2; 
					// watch out! For GeoPoint, first:latitude, second:longitude 
					gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6)); 
					mMapView01.getOverlays().add(new rideOverlay(gp1,gp2,2,color)); 
				//	Log.d("xxx","pair:" + pairs[i]); 
				} 
				mMapView01.getOverlays().add(new rideOverlay(dest,dest, 3)); // use the default color 
			} 
		} 
		catch (MalformedURLException e){ 
			e.printStackTrace(); 
		} 
		catch (IOException e){ 
			e.printStackTrace(); 
		} 
		catch (ParserConfigurationException e){ 
			e.printStackTrace(); 
		} 
		catch (SAXException e){ 
			e.printStackTrace(); 
		} 
	}

}


