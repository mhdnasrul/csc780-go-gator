package gatorDB;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import screen.main.GoGatorActivity;

import com.google.android.maps.GeoPoint;

import main.overlay.MyOverlayItem;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH;

	private static String DB_NAME = "gatorDB";

	private static String tableName = "geoPointTable";

	private static SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			System.out.println("DB Exists");
			// do nothing - database already exist
		} else {
			System.out.println("DB do not Exist");
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
			 // checkDB = null; //Uncomment this line after you copy new gatorDB
			// in assets
		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transferring bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * @return the tableName
	 */
	public static String getTableName() {
		return tableName;
	}

	public static ArrayList<MyOverlayItem> queryDB(String where) {
		ArrayList<MyOverlayItem> resSet = new ArrayList<MyOverlayItem>();
		try {
			myDataBase = GoGatorActivity.getMyDbHelper().getReadableDatabase();
			Cursor c = myDataBase
					.rawQuery(
							"SELECT latitude, longitude, pointName, desc FROM "
									+ DataBaseHelper.getTableName() + " where "
									+ where, null);
			int i = 0;
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						int latitude = (int) (Double.parseDouble(c.getString(c
								.getColumnIndex("latitude"))) * 1E6);
						int longitude = (int) (Double.parseDouble(c.getString(c
								.getColumnIndex("longitude"))) * 1E6);
						String pointName = c.getString(c
								.getColumnIndex("pointName"));
						String desc = c.getString(c.getColumnIndex("desc"));
						resSet.add(new MyOverlayItem(new GeoPoint(latitude,
								longitude), pointName, desc, i++));
					} while (c.moveToNext());
				}
			}
			c.close();

		} catch (SQLException sqle) {
			throw sqle;

		} finally {

			myDataBase.close();
		}

		return resSet;

	}

	public static String[][] queryDict() {
		ArrayList<String[]> resSet = new ArrayList<String[]>();
		try {
			myDataBase = GoGatorActivity.getMyDbHelper().getReadableDatabase();
			Cursor c = myDataBase.rawQuery(
					"SELECT _id, latitude, longitude, neighbors FROM "
							+ DataBaseHelper.getTableName()
							+ " where neighbors!='todo'", null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String[] row = new String[4];
						row[0] = c.getString(c.getColumnIndex("_id"));
						row[1] = c.getString(c.getColumnIndex("latitude"));
						row[2] = c.getString(c.getColumnIndex("longitude"));
						row[3] = c.getString(c.getColumnIndex("neighbors")).replaceAll("\\|", ",");
						resSet.add(row);
					} while (c.moveToNext());
				}
			}
			c.close();

		} catch (SQLException sqle) {
			throw sqle;

		} finally {

			myDataBase.close();
		}

		String[][] res = new String[resSet.size()][4];
		for (int i = 0; i < resSet.size(); i++) {
			res[i] = resSet.get(i);
		}
		return res;
	}
	
	public static String[][] queryBldg(String bldg) {
		ArrayList<String[]> resSet = new ArrayList<String[]>();
		try {
			myDataBase = GoGatorActivity.getMyDbHelper().getReadableDatabase();
			Cursor c = myDataBase.rawQuery(
					"SELECT _id, latitude, longitude, neighbors FROM bldgGeoPointTable"
							+ " where neighbors!='todo' AND type='"+bldg+"'", null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String[] row = new String[4];
						row[0] = c.getString(c.getColumnIndex("_id"));
						row[1] = c.getString(c.getColumnIndex("latitude"));
						row[2] = c.getString(c.getColumnIndex("longitude"));
						row[3] = c.getString(c.getColumnIndex("neighbors")).replaceAll("\\|", ",");
						resSet.add(row);
					} while (c.moveToNext());
				}
			}
			c.close();

		} catch (SQLException sqle) {
			throw sqle;

		} finally {

			myDataBase.close();
		}

		String[][] res = new String[resSet.size()][4];
		for (int i = 0; i < resSet.size(); i++) {
			res[i] = resSet.get(i);
		}
		return res;
	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

}