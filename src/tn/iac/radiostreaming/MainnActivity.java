package tn.iac.radiostreaming;
/*
import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioStationTable;
import tn.iac.radiostreaming.domain.RadioStation;
import tn.iac.radiostreaming.listener.ClickListener;
import tn.iac.radiostreaming.listener.ListArrayAdapter;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainnActivity extends Activity {

	private static final int NOTIFICATION_ID = 1;
	
	ImageView playerButton;
	NotificationManager notificationManager;
	RadioStationTable radioChannels;
	ClickListener clickListener;
	List<RadioStation> channels;
	List<String> channelNames;
	ArrayAdapter<String> adapter;
	TextView scrollingText;
	ListView listView;
	
	String column;
	String value;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTheme(R.style.WidgetBackground);
		
		setRadioChannelsToDisplay();
		
		radioChannels = new RadioChannelTable(this);
		clickListener = new ClickListener(MainnActivity.this, radioChannels);
	
		scrollingText = (TextView) findViewById(R.id.scrollingText);
		listView = (ListView) findViewById(R.id.list);				

		channelNames = new LinkedList<String>();
		channelNames = radioChannels.getAllRadioChannelNames(column, value);
		
		adapter = new ListArrayAdapter(this, channelNames, radioChannels);	
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(clickListener);
		registerForContextMenu(listView);
		
		scrollingText.setSelected(true);
	}
	
	private void setRadioChannelsToDisplay() {
		Bundle bundle = getIntent().getExtras();
		int list = (Integer) bundle.get("list");
		
		switch (list) {
		case RadioChannel.NATIONAL:
			column = RadioChannelTable.COL_TYPE;
			value = "1";
			setTitle(R.string.title_activity_national);
			break;
		case RadioChannel.INTERNATIONAL:
			column = RadioChannelTable.COL_TYPE;
			value = "2";
			setTitle(R.string.title_activity_international);
			break;
		case RadioChannel.FAVORITE:
			column = RadioChannelTable.COL_FLAG;
			value = "1";
			setTitle(R.string.title_activity_favorite);
			break;
		}
		
	}

	@Override  
	public void onCreateContextMenu(ContextMenu menu, View view,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, view, menuInfo); 
	    menu.setHeaderTitle(getString(R.string.option_menu_title)); 
	    menu.add(0, view.getId(), 0, getString(R.string.option_set_favorite));  
	    menu.add(0, view.getId(), 0, getString(R.string.option_unset_favorite)); 
	    menu.add(0, view.getId(), 0, getString(R.string.option_visit_website));
	} 
	
	 @Override  
	 public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    long id = this.listView.getItemIdAtPosition(info.position);
	    String radioName = listView.getItemAtPosition((int)id).toString();
	    
	    if(item.getTitle()==getString(R.string.option_set_favorite)){
	        radioChannels.setFavoriteChannel(radioName);
	    }  
	    else if(item.getTitle()==getString(R.string.option_unset_favorite)){
	    	radioChannels.unsetFavoriteChannel(radioName);
	    }  
	    else if(item.getTitle()==getString(R.string.option_visit_website)){
	    	String uri = radioChannels.getRadioChannelWebsite(radioName);
	    	Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	    	startActivity(browser);
	    } 
	    else {
	    	return false;
	    }  
	 return true;  
	 } 
	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.menu, menu);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId())
	        {
	            case R.id.menu_about:
	                Intent intent = new Intent(MainnActivity.this, AboutActivity.class);
	                startActivityIfNeeded(intent, -1);
	                return true;
	            default:
	            	return super.onOptionsItemSelected(item);
	        }
		}
	 
	@Override
	protected void onStop() {
		super.onStop();
		if(clickListener.isPlaying())
			createNotification();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		deleteNotification();
	}
	
	@Override
    public void onBackPressed() {
        super.onBackPressed(); 
        clickListener.stopMediaPlayer();
        finish();
    }
	
	private final void createNotification(){
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        String MyText = getString(R.string.notif_title);;
        Notification mNotification = new Notification( R.drawable.ic_stat_play, MyText, System.currentTimeMillis()       ); // the time for the notification


        String MyNotifyTitle =  getString(R.string.notif_title);
        String MyNotifiyText  = getString(R.string.notif_text);
        Intent MyIntent = new Intent( this, MainnActivity.class );
        MyIntent.putExtra("extendedTitle", MyNotifyTitle);
        MyIntent.putExtra("extendedText" , MyNotifiyText);
        PendingIntent StartIntent = PendingIntent.getActivity(  getApplicationContext(),0,MyIntent,0);


        mNotification.setLatestEventInfo(   getApplicationContext(), MyNotifyTitle, MyNotifiyText, StartIntent);

  

        notificationManager.notify( NOTIFICATION_ID , mNotification );            

     }
	
	private void deleteNotification(){
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	notificationManager.cancel(NOTIFICATION_ID);
    }

   }
*/