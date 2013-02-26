package tn.iac.radiostreaming.bd;


import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.bd.MyBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;




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
	private static final String COL_TAG = "tag";
	private static final int NUM_COL_TAG = 3;
	private static final String COL_TYPE = "type";
	private static final int NUM_COL_TYPE = 4;
	private static final String COL_FLAG = "favoris";
	private static final int NUM_COL_FLAG = 5;
	
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
 
	public RadioChannel getRadioChannel(String tag){
		Cursor c = bdd.query(TABLE_RADIO_CHANNEL, new String[] {COL_ID, COL_NAME, COL_URL, COL_TAG, COL_TYPE,COL_FLAG}, COL_TAG + "=?", new String[] {tag}, null, null, null);
		c.moveToFirst();
		RadioChannel radioChannel = cursorToRadioChannel(c);
		c.close();
		getAllRadioChannels();
		return radioChannel; 
	}
	
	private RadioChannel cursorToRadioChannel(Cursor c){   
	 
		 RadioChannel radiochannel=new RadioChannel();   
		    
		 radiochannel.setId(c.getInt(NUM_COL_ID));   
		 radiochannel.setName(c.getString(NUM_COL_NAME)); 
		 radiochannel.setUrl(c.getString(NUM_COL_URL));
		 radiochannel.setTag(c.getString(NUM_COL_TAG));    
		 radiochannel.setType(c.getString(NUM_COL_TYPE));   
		 radiochannel.setFlag(c.getInt(NUM_COL_FLAG));   
  
		 Log.d("radio", radiochannel.getName());
       
		 return radiochannel; 
		 } 
	
	public List<RadioChannel> getAllRadioChannels(){
		Cursor c = bdd.query(TABLE_RADIO_CHANNEL, new String[] {COL_ID, COL_NAME, COL_URL, COL_TAG, COL_TYPE,COL_FLAG}, null, null, null, null, COL_FLAG + " DESC");
		List<RadioChannel> radioChannels = new LinkedList<RadioChannel>();
		if(c.getCount() != 0){
			Log.d("count", ""+c.getCount());
			c.moveToFirst();
			for (int i = 0; i<c.getCount(); i++){
				radioChannels.add(cursorToRadioChannel(c));
				c.moveToNext();
			}
		}
		c.close();
		return radioChannels; 
	}
	
	public long insertRadioChannel(RadioChannel radiochannel){
		ContentValues values = new ContentValues(); 
		values.put(COL_NAME, radiochannel.getName());
		values.put(COL_TAG, radiochannel.getTag());
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
		
		mosaique = new RadioChannel("Mosaique FM","mosaique","http://radio.mosaiquefm.net:8000/mosalive","nationale", 0);
		shems = new RadioChannel("Shems FM","shems", "http://stream8.tanitweb.com/shems","nationale",0);
		ifm = new RadioChannel("IFM","ifm", "http://radioifm.ice.infomaniak.ch/radioifm-128.mp3","nationale",1);
		express = new RadioChannel("Express FM","express", "http://217.114.200.125/;stream.mp3","nationale",0);
		
		
		insertRadioChannel(mosaique);
		insertRadioChannel(ifm);
		insertRadioChannel(shems);
		insertRadioChannel(express);
	}
	

 
	
	
	


}
