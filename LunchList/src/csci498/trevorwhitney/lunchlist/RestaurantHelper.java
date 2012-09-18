package csci498.trevorwhitney.lunchlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

class RestaurantHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="lunchlist.db";
	private static final int SCHEMA_VERSION=1;
	
	public RestaurantHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE restaurants" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
				"address TEXT, type TEXT, notes TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		/* won't be needed until more than one schema versions exist */
	}
	
	public void insert(String name, String address, String type,
			String notes) {
		ContentValues values = new ContentValues();
		
		values.put("name", name);
		values.put("address", address);
		values.put("type", type);
		values.put("notes", notes);
		
		getWritableDatabase().insert("restaurants", "name", values);
	}
}