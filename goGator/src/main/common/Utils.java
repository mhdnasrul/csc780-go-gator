package main.common;

import main.routing.algo.RouteDictionary;

import com.google.android.maps.GeoPoint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.util.FloatMath;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {
	private static Context context;
	private static String message;
	
	public static void alert(Context context, String message) {
		setContext(context);
		setMessage(message);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("Navigate", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Utils.navalert(Utils.getContext(),"Walk further to reach point "+RouteDictionary.getTHDictionary()[0]);
					}
				});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	protected static String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	protected static Context getContext() {
		// TODO Auto-generated method stub
		return context;
	}

	public static void navalert(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void nextalert(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void toast(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	//GeoPoint to Location
	public static Location geoToLoc(GeoPoint gp){
		Location loc = new Location("");
		loc.setLatitude(gp.getLatitudeE6());
		loc.setLongitude(gp.getLongitudeE6());
		return loc;
	}
	
	//Location to GeoPoint
	public static GeoPoint locToGeo(Location loc) {
		return (new GeoPoint((int)loc.getLatitude(),(int)loc.getLongitude()));
	}
	
	//SmallestIndex in floating array
	public static int smallestIndex (float[] array) {
		float currentValue = array[0]; 
		int smallestIndex = 0;
		for (int j=1; j < array.length; j++) {
			if (array[j] < currentValue) {
				currentValue = array[j];
				smallestIndex = j;
			}
		}
		return smallestIndex;
	}
	
	//SmallestIndex in floating array
	public static int smallestIndex (double[] array) {
		double currentValue = array[0]; 
		int smallestIndex = 0;
		for (int j=1; j < array.length; j++) {
			if (array[j] < currentValue) {
				currentValue = array[j];
				smallestIndex = j;
			}
		}
		return smallestIndex;
	}

	/**
	 * @param context the context to set
	 */
	public static void setContext(Context cont) {
		context = cont;
	}

	/**
	 * @param message the message to set
	 */
	public static void setMessage(String mess) {
		message = mess;
	}
	//Degrees to Radians
	public static double degToRad(double num){
	      num = num * 180 / Math.PI;
		  return num;
	}
	
	public static double gps2m(float lat_a, float lng_a, float lat_b, float lng_b) {
		float pk = (float) (180/3.14169);

		  float a1 = lat_a / pk;
		  float a2 = lng_a / pk;
		  float b1 = lat_b / pk;
		  float b2 = lng_b / pk;

		  float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*
		     FloatMath.cos(b1)*FloatMath.cos(b2);
		  float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*
		     FloatMath.cos(b1)*FloatMath.sin(b2);
		  float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
		  double tt = Math.acos(t1 + t2 + t3);
		  double res = 6366000*tt;
		  System.out.println(lat_a+" "+lng_a+" "+lat_b+" "+lng_b+" "+res);  
			
		  return 6366000*tt;
		}
	
	public static double gps2m(Location a, Location b) {
		
		  return gps2m((float)(a.getLatitude()/1E6),(float)(a.getLongitude()/1E6),(float)(b.getLatitude()/1E6),(float)(b.getLongitude()/1E6));
		}
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
	/* Vincenty Inverse Solution of Geodesics on the Ellipsoid (c) Chris Veness 2002-2011             */
	/*                                                                                                */
	/* from: Vincenty inverse formula - T Vincenty, "Direct and Inverse Solutions of Geodesics on the */
	/*       Ellipsoid with application of nested equations", Survey Review, vol XXII no 176, 1975    */
	/*       http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf                                             */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

	/**
	 * Calculates geodetic distance between two points specified by latitude/longitude using 
	 * Vincenty inverse formula for ellipsoids
	 *
	 * @param   {Number} lat1, lon1: first point in decimal degrees
	 * @param   {Number} lat2, lon2: second point in decimal degrees
	 * @returns (Number} distance in metres between points
	 */
	public static double distVincenty(double lat1, double lon1, double lat2,double lon2) {
	  double a = 6378137, b = 6356752.314245,  f = 1/298.257223563;  // WGS-84 ellipsoid params
	  double L = degToRad(lon2 - lon1);
	  
	  
	  double U1 = Math.atan((1-f) * Math.tan(degToRad(lat1)));
	  double U2 = Math.atan((1-f) * Math.tan(degToRad(lat2)));
	  double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
	  double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
	  
	  double lambda = L, lambdaP;
	  int iterLimit = 100;
	  double cosSigma=0.0;
	  double cosSqAlpha=0.0;
	  double sinSigma=0.0;
	  double cos2SigmaM=0.0;
	  double sigma = 0.0;
	  
	  do {
		  double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
		  sinSigma = Math.sqrt((cosU2*sinLambda) * (cosU2*sinLambda) + 
	      (cosU1*sinU2-sinU1*cosU2*cosLambda) * (cosU1*sinU2-sinU1*cosU2*cosLambda));
	    if (sinSigma==0) 
	    	return 0.0;  // co-incident points
	    
	    cosSigma = sinU1*sinU2 + cosU1*cosU2*cosLambda;
	    sigma = Math.atan2(sinSigma, cosSigma);
	    double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
	    cosSqAlpha = 1 - sinAlpha*sinAlpha;
	    cos2SigmaM = cosSigma - 2*sinU1*sinU2/cosSqAlpha;
	    if (cos2SigmaM == Double.NaN) cos2SigmaM = 0;  // equatorial line: cosSqAlpha=0 (§6)
	    double C = f/16*cosSqAlpha*(4+f*(4-3*cosSqAlpha));
	    lambdaP = lambda;
	    lambda = L + (1-C) * f * sinAlpha *
	      (sigma + C*sinSigma*(cos2SigmaM+C*cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)));
	  } while (Math.abs(lambda-lambdaP) > 1e-12 && --iterLimit>0);

	  if (iterLimit==0) return Double.NaN;  // formula failed to converge

	  double uSq = cosSqAlpha * (a*a - b*b) / (b*b);
	  double A = 1 + uSq/16384*(4096+uSq*(-768+uSq*(320-175*uSq)));
	  double B = uSq/1024 * (256+uSq*(-128+uSq*(74-47*uSq)));
	  double deltaSigma = B*sinSigma*(cos2SigmaM+B/4*(cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)-
	    B/6*cos2SigmaM*(-3+4*sinSigma*sinSigma)*(-3+4*cos2SigmaM*cos2SigmaM)));
	  double s = b*A*(sigma-deltaSigma);
	  
	  
//	  s = Math.round(s); // round to 1mm precision
	  return s;
	  
	  // note: to return initial/final bearings in addition to distance, use something like:
//	  double fwdAz = Math.atan2(cosU2*sinLambda,  cosU1*sinU2-sinU1*cosU2*cosLambda);
//	  double revAz = Math.atan2(cosU1*sinLambda, -sinU1*cosU2+cosU1*sinU2*cosLambda);
//	  return { distance: s, initialBearing: fwdAz.toDeg(), finalBearing: revAz.toDeg() };
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */

}
