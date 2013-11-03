package tn.iac.radiostreaming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RadioDB extends SQLiteOpenHelper {
	
	public static final String TABLE_RADIO_STATION = "radiostation";
	public static final String COL_ID = "ID";
	public static final String COL_NAME = "name";
	public static final String COL_URL = "url";
	public static final String COL_WEBSITE = "website";
	public static final String COL_LOGO = "logo";
	public static final String COL_TYPE = "type";
	public static final String COL_FLAG = "favoris";
	
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "radiophony.db";
	public static final int NUM_COL_ID = 0;
	public static final int NUM_COL_NAME = 1;
	public static final int NUM_COL_URL = 2;
	public static final int NUM_COL_WEBSITE = 3;
	public static final int NUM_COL_LOGO = 4;
	public static final int NUM_COL_TYPE = 5;
	public static final int NUM_COL_FLAG = 6;
	
	private static final String CREATE_DB = "CREATE TABLE " + TABLE_RADIO_STATION + " ("  + COL_ID + " INTEGER PRIMARY KEY NOT NULL, " + COL_NAME + " TEXT NOT NULL, "+ COL_URL+ " TEXT NOT NULL, "+ COL_WEBSITE+ " TEXT NOT NULL, "+ COL_LOGO + " TEXT NOT NULL, " + COL_TYPE + " INTEGER NOT NULL, "+COL_FLAG+" INTEGER NOT NULL );";   
	
	
	public RadioDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);  
	}   
	
	@Override  
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DB); 
	}  
	
	@Override  
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   
		db.execSQL("DROP TABLE " + TABLE_RADIO_STATION + ";");   
		onCreate(db); 
	}


}