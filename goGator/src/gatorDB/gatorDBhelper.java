package gatorDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class gatorDBhelper extends SQLiteOpenHelper{
	
	public SQLiteDatabase DB;
	public String DBPath;
	public static String DBName = "gatorcampus";
	public static final int version = '1';
	public static Context currentContext;
	public static String tableName = "RouteDictionary";
	

	public gatorDBhelper(Context context) {
		super(context, DBName, null, version);
		currentContext = context;
		DBPath = "/data/data/" + context.getPackageName() + "/databases";
		createDatabase();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	private void createDatabase() {
		boolean dbExists = checkDbExists();
		
		if (dbExists) {
			// do nothing
		} else {
			DB = currentContext.openOrCreateDatabase(DBName, 0, null);
			DB.execSQL("CREATE TABLE IF NOT EXISTS " +
        			tableName +
        			" (Latitude VARCHAR, Longitude VARCHAR" +
        			" Neighbors VARCHAR, Name VARCHAR,Type VARCHAR, Description VARCHAR);");
        	System.out.println("DB created....");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Cafe','Cafe 1 Desc');");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Cafe','Cafe 1 Desc');");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Cafe','Cafe 1 Desc');");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Campus','Cafe 1 Desc');");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Department','Cafe 1 Desc');");
        	DB.execSQL("INSERT INTO " +
        			tableName +
        			" Values ('37721274','-122476868','p2|p3|p4|p8|p9|p10','Cafe 1','Building','Cafe 1 Desc');");
		}
		
		
	}

	private boolean checkDbExists() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DBPath + DBName;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}
}
