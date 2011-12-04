package main.overlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MyOverlayItem extends OverlayItem  implements Comparable<MyOverlayItem>{
	private int id;
	private String title;
	
	public MyOverlayItem(GeoPoint point, String title, String snippet, int id) {
		super(point, title, snippet);
		this.title = title;
		this.id=id;
	}
	
	public MyOverlayItem() {
		super(new GeoPoint(0,0),"","");
		//Auto-generated constructor stub
	}

	//Extended to mainly get toString()
	public String toString(){
		return this.getTitle();
	}
	
	public int getId(){
		return this.id;
	}

	@Override
	public int compareTo(MyOverlayItem another) {
		return this.title.compareToIgnoreCase(another.title);
	}

}
