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

public class InternationalChanActivity extends Activity {
	
	RadioChannelTable radioChannels;
	List<String> channelUrls;
	List <RadioChannel> channels;
	ListView listView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intenational_chan);
		
		channels = new LinkedList<RadioChannel>();
		radioChannels = new RadioChannelTable(this);	
		channelUrls = radioChannels.getAllRadioChannelWebsites(RadioChannelTable.COL_TYPE, "2");
		
		listView = (ListView) findViewById(R.id.listUrlsInt);	
		
		adapter = new UrlListArrayAdapter(this, channelUrls);	
		listView.setAdapter(adapter); 
		registerForContextMenu(listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_intenational_chan, menu);
		return true;
	}

}
