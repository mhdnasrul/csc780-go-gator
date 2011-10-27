package main.routing.algo;

public class RouteDictionary {
	private static String[][] routeDictionary = {
		{"pt1","37.721187","-122.475503","pt2"},
		{"pt2","37.721285","-122.475919","pt1,pt3"},
		{"pt3","37.721458","-122.476189","pt2,pt4,HSSentrance"},
		{"pt4","37.721644","-122.476656","pt3,pt5,AdminEntrance,HSSentrance"},
		{"pt5","37.721713","-122.477152","pt4,pt6,AdminEntrance,BUSentrance"},
		{"pt6","37.721827","-122.477345","pt5,pt7,pt8,BUSentrance"},
		{"pt7","37.72191","-122.47771","pt6,pt8,pt9,JPaulEntrance"},
		{"pt8","37.722","-122.477496","pt6,pt7,pt11"},
		{"pt9","37.721978","-122.478029","pt7,pt10,JPaulEntrance,CCentrance"},
		{"pt10","37.72234","-122.478011","pt9,pt11,CCentrance"},
		{"pt11","37.722376","-122.477584","pt8,pt10,pt12,pt14"},
		{"pt12","37.722443","-122.477756","pt11,pt13"},
		{"pt13","37.72261","-122.478003","pt15,pt16,pt12,pt10,SFSUbookstore"},
		{"pt14","37.72256","-122.477413","pt11,pt17"},
		{"pt15","37.722863","-122.478193","pt16,pt13,SFSUbookstore"},
		{"pt16","37.72287","-122.477791","pt15,pt18,pt11,pt13"},
		{"pt17","37.72271","-122.477209","pt14,pt19"},
		{"pt18","37.722999","-122.477482","pt16,SCIentrance"},
		{"pt19","37.72286","-122.476916","pt17,SCIentrance"},
		{"AdminEntrance","37.721549","-122.476911","pt4,pt5"},
		{"HSSentrance","37.72171","-122.476259","pt3,pt4"},
		{"BUSentrance","37.72187","-122.476911","pt5,pt6"},
		{"JPaulEntrance","37.721838","-122.47789","pt7,pt9"},
		{"CCentrance","37.72222","-122.478448","pt9,pt10"},
		{"SFSUbookstore","37.722702","-122.478252","pt13,pt15"},
		{"SCIentrance","37.72301","-122.476758","pt18,pt19"},
		{"BUSentrance2","37.722282","-122.476852","pt5"}
	};

	/**
	 * @param routeDictionary the routeDictionary to set
	 */
	public void setRouteDictionary(String[][] routeDictionary) {
		RouteDictionary.routeDictionary = routeDictionary;
	}

	/**
	 * @return the routeDictionary
	 */
	public static String[][] getRouteDictionary() {
		return routeDictionary;
	}
}
