package tn.iac.radiostreaming;

import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioChannel;
import tn.iac.radiostreaming.db.RadioChannelTable;
import tn.iac.radiostreaming.listener.UrlListArrayAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NationalChanActivity extends Activity {
	RadioChannelTable radioChannels;
	List<String> channelUrls;
	List <RadioChannel> channels;
	ListView listView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_national_chan);
		
		channels = new LinkedList<RadioChannel>();
		channelUrls = new LinkedList<String>();
		radioChannels = new RadioChannelTable(this);
		
		channels = radioChannels.getAllRadioChannels(RadioChannelTable.COL_TYPE, "1");
		for(int i=0;i<channels.size();i++){
			channelUrls.add(channels.get(i).getUrl());
		}
		
		
		listView = (ListView) findViewById(R.id.listUrlsNat);	
		
		adapter = new UrlListArrayAdapter(this, channelUrls);	
		listView.setAdapter(adapter); 
		registerForContextMenu(listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_national_chan, menu);
		return true;
	}

}
