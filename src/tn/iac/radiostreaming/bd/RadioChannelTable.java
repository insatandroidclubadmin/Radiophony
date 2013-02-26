package tn.iac.radiostreaming.bd;


import tn.iac.radiostreaming.bd.MyBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;




public class RadioChannelTable{

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "radiochannel.db";
	private static final String TABLE_RADIO_CHANNEL = "radiochannel";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	private static final String COL_NAME = "name";
	private static final int NUM_COL_NAME = 1;
	private static final String COL_URL = "url";
	private static final int NUM_COL_URL = 2;
	private static final String COL_TYPE = "type";
	private static final int NUM_COL_TYPE = 3;
	private static final String COL_FLAG = "favoris";
	private static final int NUM_COL_FLAG = 4;
	
	private SQLiteDatabase bdd;
	private MyBase mySQLiteBase;
	
	
	public RadioChannelTable(Context context){
		mySQLiteBase = new MyBase(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){ 
		bdd = mySQLiteBase.getWritableDatabase(); 
		
		
	}
 
	public void close(){
		bdd.close();
		
	}
 
	public RadioChannel getRadioChannel(String name){
		
		Cursor c = bdd.query(TABLE_RADIO_CHANNEL, new String[] {COL_ID, COL_NAME, COL_URL,COL_TYPE,COL_FLAG}, COL_NAME + "=? ", new String[] {name}, null, null, null, null);   
		return cursorToRadioChannel(c); 
	}
 
	
	private RadioChannel cursorToRadioChannel(Cursor c){   
		 if (c.getCount() == 0)    
			 return null;     	 
		 c.moveToFirst();   
		
		 RadioChannel radiochannel=new RadioChannel();   
		    
		 radiochannel.setId(c.getInt(NUM_COL_ID));   
		 radiochannel.setName(c.getString(NUM_COL_NAME));   
		 radiochannel.setUrl(c.getString(NUM_COL_URL));   
		 radiochannel.setType(c.getString(NUM_COL_TYPE));   
		 radiochannel.setFlag(c.getInt(NUM_COL_FLAG));   
  
		 c.close();       
		 return radiochannel; 
		 } 
	
	public long insertRadioChannel(RadioChannel radiochannel){
		
		ContentValues values = new ContentValues(); 
		values.put(COL_NAME, radiochannel.getName());   
		values.put(COL_TYPE, radiochannel.getType());   
		values.put(COL_FLAG, radiochannel.getFlag());  
		values.put(COL_URL, radiochannel.getUrl());  
		return bdd.insert(TABLE_RADIO_CHANNEL, null, values);
	}
	
	public int removeRadioWithID(int id){
		return bdd.delete(TABLE_RADIO_CHANNEL,COL_ID+"="+id,null);
		
	}
	
	
	public void fillInitialTable(){
		
		RadioChannel mosaique,ifm,shems,express;
		
		mosaique = new RadioChannel("mosaique","http://radio.mosaiquefm.net:8000/mosalive","nationale", 0);
		shems = new RadioChannel("shems", "http://stream8.tanitweb.com/shems","nationale",0);
		ifm = new RadioChannel("ifm", "http://radioifm.ice.infomaniak.ch/radioifm-128.mp3","nationale",0);
		express = new RadioChannel("express", "http://217.114.200.125/;stream.mp3","nationale",0);
		
		
		insertRadioChannel(mosaique);
		insertRadioChannel(ifm);
		insertRadioChannel(shems);
		insertRadioChannel(express);
	}
	

 
	
	
	


}
