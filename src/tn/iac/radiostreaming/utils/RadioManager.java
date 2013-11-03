package tn.iac.radiostreaming.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.domain.RadioStation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RadioManager {

	private static SQLiteDatabase database;
	private static RadioDB mySQLiteBase;
	
	public static void init(Context context){
		mySQLiteBase = new RadioDB(context, RadioDB.DB_NAME, null, RadioDB.DB_VERSION);
		fillInitialTable();
	}
	
	private static void fillInitialTable() {
		open();
		insert(new RadioStation("Mosaique FM", 	"http://radio.mosaiquefm.net:8000/mosalive","http://www.mosaiquefm.net/", 				"mosaiquefm" , RadioStation.NATIONAL, RadioStation.FAVORITE));
		insert(new RadioStation("Shems FM", 	"http://stream8.tanitweb.com/shems","http://www.shemsfm.net/",	 						"shemsfm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("ifm", 			"http://radioifm.ice.infomaniak.ch/radioifm-128.mp3","http://www.ifm.tn/",			 	"ifm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Jawhara FM", 	"http://streaming2.toutech.net:8000/jawharafm","http://www.jawharafm.net/", 			"jawharafm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Express FM", 	"http://stream6.tanitweb.com/expressfm","http://www.radioexpressfm.com/‎",			 	"expressfm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));  
		insert(new RadioStation("Sabra FM", 	"http://188.165.248.163:8000/;stream.mp3","http://www.radiosabrafm.net/",	 			"sabrafm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Oasis FM", 	"http://stream8.tanitweb.com/Oasis","http://www.oasisfm.tn/", 							"oasisfm" , RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Cap FM", 		"http://stream8.tanitweb.com/capfm", "http://www.capradio.tn/", 						"capfm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Oxygene FM", 	"http://oxygenefm.ice.infomaniak.ch/oxygenefm.mp3", "http://oxygene.fm/",	 			"oxygenefm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Mfm", 			"http://radiomfmtunisie.net:8000/live?1375275689405.mp3", "http://www.radiomfm.tn/", 	"mfm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Karama FM", 	"http://serveur.wanastream.com:64900/;?1375275938431.mp3", "http://www.karamafm.net/", 	"karamafm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Ulysse FM", 	"http://62.75.182.135:8001/;stream.mp3", "http://ulysse-fm.com/", 						"ulyssefm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Radio Touma", 	"http://streaming203.radionomy.com/charts_station", "http://www.radio2ma.com/", 		"radiotouma" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Radio Nabeul", "http://streaming208.radionomy.com/RadionoMiX", "http://www.radionabeul.com/", 			"radionabeul" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));  
		insert(new RadioStation("Radio Bizerte","http://173.236.50.60/;stream.nsv", "http://www.radiobizerte.net/", 					"radiobizerte" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		
		insert(new RadioStation("Radio Bledi", 	"http://radiobledi.com:9996/stream/1/", "http://www.radiobledi.tn/", 					"radiobledi" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Radio Kalima", "http://radio.kalima-tunisie.info:8787/Live", "http://www.kalima-tunisie.info/fr", 		"radiokalima" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Zitouna FM", 	"http://stream8.tanitweb.com/zitounafm", "http://www.zitounafm.net/", 					"zitounafm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Radio 6", 		"http://94.23.230.160:8000/stream.mp3", "http://radio6tunis.net/", 						"radio6" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		insert(new RadioStation("Mines FM", 	"http://shoutcast.minesfm.com:8086/;stream.mp3", "http://www.minesfm.com/", 			"minesfm" ,RadioStation.NATIONAL, RadioStation.NOT_FAVORITE)); 
		
		insert(new RadioStation("Fun Radio", "http://streaming.radio.funradio.fr/fun-1-44-96?.wma","http://www.funradio.fr/", "funradio" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("NRJ", "http://mp3.live.tv-radio.com/nrj/all/nrj_113225.mp3","http://www.nrj.fr/", "nrj" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("RTL2", "http://streaming.radio.rtl2.fr/rtl2-1-44-96?.wma","http://www.rtl2.fr/", "rtl2" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Europe 1", "http://vipicecast.yacast.net/europe1","http://www.europe1.fr/", "europe1" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
	
		insert(new RadioStation("France Inter", "http://95.81.147.3/franceinter/all/franceinterhautdebit.mp3","http://www.franceinter.fr/", "franceinter" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("RMC", 			"http://vipicecast.yacast.net/rmc",		   "http://www.rmc.fr/", 									"rmc" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Virgin Radio",	"http://vipicecast.yacast.net/virginradio_128", "http://www.virginradio.fr/", 				"virginradio" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Cherie FM", 	"http://95.81.147.3/cherie_fm/all/che_124310.mp3", "http://www.cheriefm.fr/", 					"cheriefm" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("Nostalgie", 	"http://95.81.146.2/nostalgie/all/nos_113812.mp3", "http://www.nostalgie.fr/", 					"nostalgie" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));

		insert(new RadioStation("Skyrock", 		"http://95.81.146.6/4603/sky_120728.mp3", "http://www.skyrock.fm/radio/", 				"skyrock" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE)); ////
		insert(new RadioStation("Rire et chansons","http://95.81.146.2/rire_et_chansons/all/rir_124629.mp3","http://www.rireetchansons.fr/", 			"rireetchansons" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));
		insert(new RadioStation("RTL",	"http://streaming.radio.rtl.fr/rtl-1-44-96", "http://radio.rtl.fr/", 				"rtl" , RadioStation.INTERNATIONAL, RadioStation.NOT_FAVORITE));

		
		
		close();
	}

	public static long insert(RadioStation station) {
		RadioStation existingStation = find(RadioDB.COL_NAME,station.getName());
		long count = 0;
		if (existingStation == null) {
			ContentValues values = new ContentValues();
			values.put(RadioDB.COL_NAME, station.getName());
			values.put(RadioDB.COL_URL, station.getUrl());
			values.put(RadioDB.COL_WEBSITE, station.getWebsite());
			values.put(RadioDB.COL_LOGO, station.getLogo());
			values.put(RadioDB.COL_TYPE, station.getType());
			values.put(RadioDB.COL_FLAG, station.getFlag());
			open();
			count = database.insert(RadioDB.TABLE_RADIO_STATION, null, values);
			close();
		}
		return count;
	}
	
	public static RadioStation find(String column, String value){
		open();
		Cursor c = database.query(RadioDB.TABLE_RADIO_STATION, new String[] { RadioDB.COL_ID,
				RadioDB.COL_NAME, RadioDB.COL_URL,RadioDB.COL_WEBSITE,RadioDB.COL_LOGO, RadioDB.COL_TYPE, RadioDB.COL_FLAG }, column
				+ "=?", new String[] { value }, null, null, null);
		
		RadioStation radioChannel = null;
		if (c.getCount() != 0) {
			c.moveToFirst();
			radioChannel = cursorToRadioStation(c);
			c.close();
		}
		close();
		return radioChannel;
	}

	public static List<RadioStation> findAll(String column, String value) {
		open();
		Cursor c = database.query(RadioDB.TABLE_RADIO_STATION, 
				new String[] { RadioDB.COL_ID,RadioDB.COL_NAME, RadioDB.COL_URL,RadioDB.COL_WEBSITE,RadioDB.COL_LOGO, RadioDB.COL_TYPE, RadioDB.COL_FLAG },
				column + "=?", new String[]{value}, null, null, RadioDB.COL_FLAG + " DESC");
		
		List<RadioStation> stations = new LinkedList<RadioStation>();
		if (c.getCount() != 0) {
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				stations.add(cursorToRadioStation(c));
				c.moveToNext();
			}
		}
		c.close();
		close();
		return stations;
	}
		
	private static RadioStation cursorToRadioStation(Cursor c) {
		RadioStation radioChannel = new RadioStation();

		radioChannel.setId(c.getInt(RadioDB.NUM_COL_ID));
		radioChannel.setName(c.getString(RadioDB.NUM_COL_NAME));
		radioChannel.setUrl(c.getString(RadioDB.NUM_COL_URL));
		radioChannel.setWebsite(c.getString(RadioDB.NUM_COL_WEBSITE));
		radioChannel.setLogo(c.getString(RadioDB.NUM_COL_LOGO));
		radioChannel.setType(c.getInt(RadioDB.NUM_COL_TYPE));
		radioChannel.setFlag(c.getInt(RadioDB.NUM_COL_FLAG));
		return radioChannel;
	}
	
	
	
	///////////////////////////// Useful public methods
	
	public static List<String> findAllStationNames(String col, String value) {
		List<RadioStation> radioChannels = findAll(col, value);
		List<String> channelNames = new ArrayList<String>();
		for(int i=0 ; i<radioChannels.size() ; i++){
			channelNames.add(radioChannels.get(i).getName());
		}
		return channelNames;
	}
	
	public static void setFavorite(String name, boolean favorite){
		if(favorite)
			update(name, RadioDB.COL_FLAG ,RadioStation.FAVORITE);
		else
			update(name, RadioDB.COL_FLAG ,RadioStation.NOT_FAVORITE);
	}
		
	public static int update(String name, String column, String value) {
		ContentValues values = new ContentValues();
		values.put(column, value);
		open();
		int count = database.update(RadioDB.TABLE_RADIO_STATION, values, RadioDB.COL_NAME + "=?", new String[]{name});
		close();
		return count;
	}
	
	public static int update(String name, String column, int value) {
		ContentValues values = new ContentValues();
		values.put(column, value);
		open();
		int count = database.update(RadioDB.TABLE_RADIO_STATION, values, RadioDB.COL_NAME + "=?", new String[]{name});
		close();
		return count;
	}
	
	public static int removeStationWithID(int id) {
		open();
		int count = database.delete(RadioDB.TABLE_RADIO_STATION, RadioDB.COL_ID + "=" + id, null);
		close();
		return count;
	}
	
	
	//////////////////////////////////////////////////////// Open and close
	
	private static void open() {
		database = mySQLiteBase.getWritableDatabase();
	}
	
	private static void close() {
		database.close();
	}

}
