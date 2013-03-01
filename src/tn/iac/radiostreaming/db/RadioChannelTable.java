package tn.iac.radiostreaming.db;

import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioChannelDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	private static final String COL_TAG = "tag";
	private static final int NUM_COL_TAG = 3;
	public static final String COL_TYPE = "type";
	private static final int NUM_COL_TYPE = 4;
	public static final String COL_FLAG = "favoris";
	private static final int NUM_COL_FLAG = 5;

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

		RadioChannel mosaique, ifm, shems, express, fun, nrj, rtl2, skyrock, skyrocke;

		mosaique = new RadioChannel("Mosaique FM", "mosaique",
				"http://radio.mosaiquefm.net:8000/mosalive", "nationale", 0);
		shems = new RadioChannel("Shems FM", "shems",
				"http://stream8.tanitweb.com/shems", "nationale", 0);
		ifm = new RadioChannel("ifm", "ifm",
				"http://radioifm.ice.infomaniak.ch/radioifm-128.mp3", "nationale", 1);
		express = new RadioChannel("Express FM", "express",
				"http://217.114.200.125/;stream.mp3", "nationale", 0);
		fun = new RadioChannel("Fun Radio", "fun",
				"http://streaming.radio.funradio.fr/fun-1-44-96?.wma", "internationale", 0);
		nrj = new RadioChannel("NRJ", "nrj",
				"http://mp3.live.tv-radio.com/nrj/all/nrj_113225.mp3", "internationale", 0);
		rtl2 = new RadioChannel("RTL2", "rtl2",
				"http://streaming.radio.rtl2.fr/rtl2-1-44-96?.wma", "internationale", 0);
		skyrock = new RadioChannel("Skyrock", "skyrock",
				"http://player.skyrock.fm/V4/skyrock/skyrock.m3u", "internationale", 0);
		insertRadioChannel(mosaique);
		insertRadioChannel(ifm);
		insertRadioChannel(shems);
		insertRadioChannel(express);
		insertRadioChannel(fun);
		insertRadioChannel(nrj);
		insertRadioChannel(rtl2);
		insertRadioChannel(skyrock);
	}

	public long insertRadioChannel(RadioChannel radiochannel) {
		RadioChannel radioChannel = getRadioChannelByCol(COL_TAG,radiochannel.getTag());
		long count = 0;
		if (radioChannel == null) {
			ContentValues values = new ContentValues();
			values.put(COL_NAME, radiochannel.getName());
			values.put(COL_TAG, radiochannel.getTag());
			values.put(COL_TYPE, radiochannel.getType());
			values.put(COL_FLAG, radiochannel.getFlag());
			values.put(COL_URL, radiochannel.getUrl());
			count = database.insert(TABLE_RADIO_CHANNEL, null, values);
		}

		return count;
	}
	
	public RadioChannel getRadioChannelByCol(String col, String value){
		Cursor c = database.query(TABLE_RADIO_CHANNEL, new String[] { COL_ID,
				COL_NAME, COL_URL, COL_TAG, COL_TYPE, COL_FLAG }, col
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
				new String[] { COL_ID,COL_NAME, COL_URL, COL_TAG, COL_TYPE, COL_FLAG },
				col+ "=?", new String[] { value }, null, null, COL_FLAG + " DESC");
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
		radioChannel.setTag(c.getString(NUM_COL_TAG));
		radioChannel.setType(c.getString(NUM_COL_TYPE));
		radioChannel.setFlag(c.getInt(NUM_COL_FLAG));
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
