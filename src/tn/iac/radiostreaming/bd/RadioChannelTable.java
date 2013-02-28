package tn.iac.radiostreaming.bd;

import java.util.LinkedList;
import java.util.List;
import tn.iac.radiostreaming.bd.MyBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RadioChannelTable {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "radiochannel.db";
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

	private SQLiteDatabase database;
	private MyBase mySQLiteBase;

	public RadioChannelTable(Context context) {
		mySQLiteBase = new MyBase(context, DB_NAME, null, DB_VERSION);
		this.open();
		this.fillInitialTable();
	}

	public void open() {
		database = mySQLiteBase.getWritableDatabase();
	}
	
	public void fillInitialTable() {

		RadioChannel mosaique, ifm, shems, express, fun, nrj, rtl2;

		mosaique = new RadioChannel("Mosaique FM", "mosaique",
				"http://radio.mosaiquefm.net:8000/mosalive", "nationale", 0);
		shems = new RadioChannel("Shems FM", "shems",
				"http://stream8.tanitweb.com/shems", "nationale", 0);
		ifm = new RadioChannel("IFM", "ifm",
				"http://radioifm.ice.infomaniak.ch/radioifm-128.mp3",
				"nationale", 1);
		express = new RadioChannel("Express FM", "express",
				"http://217.114.200.125/;stream.mp3", "nationale", 0);
		fun = new RadioChannel("Fun Radio", "fun",
				"http://streaming.radio.funradio.fr/fun-1-44-96?.wma", "internationale", 0);
		nrj = new RadioChannel("NRJ", "nrj",
				"mms://vipnrj.yacast.net/encodernrj", "internationale", 0);
		rtl2 = new RadioChannel("RTL2", "rtl2",
				"http://streaming.radio.rtl2.fr/rtl2-1-44-96?.wma", "internationale", 0);
		insertRadioChannel(mosaique);
		insertRadioChannel(ifm);
		insertRadioChannel(shems);
		insertRadioChannel(express);
		insertRadioChannel(fun);
		insertRadioChannel(nrj);
		insertRadioChannel(rtl2);
	}

	public long insertRadioChannel(RadioChannel radiochannel) {
		RadioChannel radioChannel = getRadioChannel(radiochannel.getTag());
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
	
	public RadioChannel getRadioChannel(String tag) {
		Cursor c = database.query(TABLE_RADIO_CHANNEL, new String[] { COL_ID,
				COL_NAME, COL_URL, COL_TAG, COL_TYPE, COL_FLAG }, COL_TAG
				+ "=?", new String[] { tag }, null, null, null);
		
		RadioChannel radioChannel = null;
		if (c.getCount() != 0) {
			c.moveToFirst();
			radioChannel = cursorToRadioChannel(c);
			c.close();
		}
		return radioChannel;
	}
	
	
	public RadioChannel getNameRadioChannel(String name) {
		Cursor c = database.query(TABLE_RADIO_CHANNEL, new String[] { COL_ID,
				COL_NAME, COL_URL, COL_TAG, COL_TYPE, COL_FLAG }, COL_NAME
				+ "=?", new String[] { name }, null, null, null);
		
		RadioChannel radioChannel = null;
		if (c.getCount() != 0) {
			c.moveToFirst();
			radioChannel = cursorToRadioChannel(c);
			c.close();
		}
		return radioChannel;
	}

	public List<RadioChannel> getAllRadioChannels() {
		Cursor c = database.query(TABLE_RADIO_CHANNEL, 
				new String[] { COL_ID,COL_NAME, COL_URL, COL_TAG, COL_TYPE, COL_FLAG },
				null, null, null, null, COL_FLAG + " DESC");
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
	
	public void unSetFavoriteChannel(String name){
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
