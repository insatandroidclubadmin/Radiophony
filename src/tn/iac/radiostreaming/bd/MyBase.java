package tn.iac.radiostreaming.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyBase extends SQLiteOpenHelper {
	
	private static final String TABLE_RADIO_CHANNEL = "radiochannel";
	private static final String COL_ID = "ID";
	private static final String COL_NAME = "name";
	private static final String COL_URL = "url";
	private static final String COL_TYPE = "type";
	private static final String COL_FLAG = "favoris";
	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_RADIO_CHANNEL + " ("  + COL_ID + " INTEGER PRIMARY KEY NOT NULL, " + COL_NAME + " TEXT NOT NULL, "  + COL_TYPE + " TEXT NOT NULL, "+COL_FLAG+" TEXT NOT NULL );";   
	
	
	public MyBase(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);  
		}   
	
	
	
	@Override  
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD); 
		}  
	
	
	
	
	@Override  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   
		db.execSQL("DROP TABLE " + TABLE_RADIO_CHANNEL + ";");   
		onCreate(db); 
	}


}