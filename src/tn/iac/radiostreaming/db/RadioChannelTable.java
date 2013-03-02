package tn.iac.radiostreaming.db;

import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioChannelDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;

public class RadioChannelTable {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "radiochannel.db";
	private static final String TABLE_RADIO_CHANNEL = "radiochannel";
	private static final String COL_ID = "ID";
	private static final int NUM_COL_ID = 0;
	public static final String COL_NAME = "name";
	private static final int NUM_COL_NAME = 1;
	private static final String COL_URL = "url";
	private static final int NUM_COL_URL = 2;
	public static final String COL_TYPE = "type";
	private static final int NUM_COL_TYPE = 3;
	public static final String COL_FLAG = "favoris";
	private static final int NUM_COL_FLAG = 4;

	private SQLiteDatabase database;
	private RadioChannelDB mySQLiteBase;

	public RadioChannelTable(Context context) {
		mySQLiteBase = new RadioChannelDB(context, DB_NAME, null, DB_VERSION);
		this.open();
		this.fillInitialTable();
	}

	public void open() {
		database = mySQLiteBase.getWritableDatabase();
	}
	
	public void fillInitialTable() {
		insertRadioChannel(new RadioChannel("Mosaique FM", "http://radio.mosaiquefm.net:8000/mosalive", 1, 0));
		insertRadioChannel(new RadioChannel("Shems FM", "http://stream8.tanitweb.com/shems", 1, 0));
		insertRadioChannel(new RadioChannel("ifm", "http://radioifm.ice.infomaniak.ch/radioifm-128.mp3", 1, 0));
		insertRadioChannel(new RadioChannel("Jawhara FM", "http://streaming2.toutech.net:8000/jawharafm", 1, 0));
		insertRadioChannel(new RadioChannel("Express FM", "http://217.114.200.125/;stream.mp3", 1, 0));
		insertRadioChannel(new RadioChannel("Sabra FM", "http://188.165.248.163:8000/;stream.mp3", 1, 0));
		insertRadioChannel(new RadioChannel("Oasis FM", "http://stream8.tanitweb.com/Oasis", 1, 0));
		insertRadioChannel(new RadioChannel("Cap FM", "http://stream8.tanitweb.com/capfm", 1, 0));
		insertRadioChannel(new RadioChannel("Fun Radio", "http://streaming.radio.funradio.fr/fun-1-44-96?.wma", 2, 0));
		insertRadioChannel(new RadioChannel("NRJ", "http://mp3.live.tv-radio.com/nrj/all/nrj_113225.mp3", 2, 0));
		insertRadioChannel(new RadioChannel("RTL2", "http://streaming.radio.rtl2.fr/rtl2-1-44-96?.wma", 2, 0));
		insertRadioChannel(new RadioChannel("Europe 1", "http://vipicecast.yacast.net/europe1", 2, 0));
		
	}

	public long insertRadioChannel(RadioChannel radiochannel) {
		RadioChannel radioChannel = getRadioChannelByCol(COL_NAME,radiochannel.getName());
		long count = 0;
		if (radioChannel == null) {
			ContentValues values = new ContentValues();
			values.put(COL_NAME, radiochannel.getName());
			values.put(COL_URL, radiochannel.getUrl());
			values.put(COL_TYPE, radiochannel.getType());
			values.put(COL_FLAG, radiochannel.getFlag());
			count = database.insert(TABLE_RADIO_CHANNEL, null, values);
		}

		return count;
	}
	
	public RadioChannel getRadioChannelByCol(String col, String value){
		Cursor c = database.query(TABLE_RADIO_CHANNEL, new String[] { COL_ID,
				COL_NAME, COL_URL, COL_TYPE, COL_FLAG }, col
				+ "=?", new String[] { value }, null, null, null);
		
		RadioChannel radioChannel = null;
		if (c.getCount() != 0) {
			c.moveToFirst();
			radioChannel = cursorToRadioChannel(c);
			c.close();
		}
		return radioChannel;
	}

	public List<RadioChannel> getAllRadioChannels(String col, String value) {
		Cursor c = database.query(TABLE_RADIO_CHANNEL, 
				new String[] { COL_ID,COL_NAME, COL_URL, COL_TYPE, COL_FLAG },
				col + "=?", new String[]{value}, null, null, COL_FLAG + " DESC");
		Log.d("debugg", col + " " + value + " " + c.getCount());	///////////////////////////////////////////////
		List<RadioChannel> radioChannels = new LinkedList<RadioChannel>();
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				radioChannels.add(cursorToRadioChannel(c));
				c.moveToNext();
			}
		}
		c.close();
		return radioChannels;
	}
	
	public List<String> getAllRadioChannelNames(String col, String value) {
		List<RadioChannel> radioChannels = getAllRadioChannels(col, value);
		List<String> channelNames = new LinkedList<String>();
		Log.d("debug", ""+radioChannels.size());    ///////////////////////////////////////////////////////////
		for(int i=0 ; i<radioChannels.size() ; i++){
			channelNames.add(radioChannels.get(i).getName());
		}
		return channelNames;
	}
	
	private RadioChannel cursorToRadioChannel(Cursor c) {
		RadioChannel radioChannel = new RadioChannel();

		radioChannel.setId(c.getInt(NUM_COL_ID));
		radioChannel.setName(c.getString(NUM_COL_NAME));
		radioChannel.setUrl(c.getString(NUM_COL_URL));
		radioChannel.setType(c.getInt(NUM_COL_TYPE));
		radioChannel.setFlag(c.getInt(NUM_COL_FLAG));
		Log.d("debuug", ""+radioChannel.getType());	////////////////////////////////////////////////////////////
		return radioChannel;
	}

	public void setFavoriteChannel(String name){
		ContentValues values = new ContentValues();
		values.put(COL_FLAG, 1);
		database.update(TABLE_RADIO_CHANNEL, values, COL_NAME + "=?", new String[] {name});
	}
	
	public void unsetFavoriteChannel(String name){
		ContentValues values = new ContentValues();
		values.put(COL_FLAG, 0);
		database.update(TABLE_RADIO_CHANNEL, values, COL_NAME + "=?", new String[] {name});
	}
	
	public int removeRadioWithID(int id) {
		return database.delete(TABLE_RADIO_CHANNEL, COL_ID + "=" + id, null);
	}
	
	public void close() {
		database.close();
	}

}
