package tn.iac.radiostreaming;

import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioChannelTable;
import tn.iac.radiostreaming.listener.ListArrayAdapter;
import tn.iac.radiostreaming.listener.UrlListArrayAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AboutActivity extends TabActivity{
	
	RadioChannelTable radioChannels;
	List<String> channelUrls;
	ListView listView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TabHost tabHost = getTabHost();
		

        TabSpec tab1 = tabHost.newTabSpec("Internationales");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.globe));
        Intent InternationalIntent = new Intent(this, InternationalChanActivity.class);
        tab1.setContent(InternationalIntent);
 

        TabSpec tab2 = tabHost.newTabSpec("Nationales");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.national));
        Intent songsIntent = new Intent(this, NationalChanActivity.class);
        tab2.setContent(songsIntent);
 

        tabHost.addTab(tab1); 
        tabHost.addTab(tab2);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

}
