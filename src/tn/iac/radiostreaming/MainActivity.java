package tn.iac.radiostreaming;

import java.util.LinkedList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioChannel;
import tn.iac.radiostreaming.db.RadioChannelTable;
import tn.iac.radiostreaming.listener.ClickListener;
import tn.iac.radiostreaming.listener.ListArrayAdapter;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int NOTIFICATION_ID = 1;
	
	ImageView pauseButton;
	NotificationManager notificationManager;
	RadioChannelTable radioChannels;
	ClickListener clickListener;
	List<RadioChannel> channels;
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
		clickListener = new ClickListener(MainActivity.this, radioChannels);
	
		scrollingText = (TextView) findViewById(R.id.scrollingText);
		pauseButton = (ImageView) findViewById(R.id.pause);
		listView = (ListView) findViewById(R.id.list);				

		channelNames = new LinkedList<String>();
		channelNames = radioChannels.getAllRadioChannelNames(column, value);
		
		adapter = new ListArrayAdapter(this, channelNames, radioChannels);	
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(clickListener);
		registerForContextMenu(listView);
		
		pauseButton.setOnClickListener(clickListener);
		scrollingText.setSelected(true);
	}
	
	private void setRadioChannelsToDisplay() {
		Bundle bundle = getIntent().getExtras();
		int list = (Integer) bundle.get("list");
		
		switch (list) {
		case RadioChannel.NATIONAL:
			column = RadioChannelTable.COL_TYPE;
			value = "1";
			break;
		case RadioChannel.INTERNATIONAL:
			column = RadioChannelTable.COL_TYPE;
			value = "2";
			break;
		case RadioChannel.FAVORITE:
			column = RadioChannelTable.COL_FLAG;
			value = "1";
			break;
		}
		
	}

	@Override  
	public void onCreateContextMenu(ContextMenu menu, View view,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, view, menuInfo); 
	    menu.setHeaderTitle(getString(R.string.favoriteMenu)); 
	    menu.add(0, view.getId(), 0, getString(R.string.setFavorite));  
	    menu.add(0, view.getId(), 0, getString(R.string.unsetFavorite));  
	} 
	
	 @Override  
	 public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    long id = this.listView.getItemIdAtPosition(info.position);
	    String radioName = listView.getItemAtPosition((int)id).toString();
	    
	    if(item.getTitle()==getString(R.string.setFavorite)){
	        radioChannels.setFavoriteChannel(radioName);
	    }  
	    else if(item.getTitle()==getString(R.string.unsetFavorite)){
	    	radioChannels.unsetFavoriteChannel(radioName);
	    }  
	    else {
	    	return false;
	    }  
	 return true;  
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

        /* Create a notification */
        String MyText = "Radio streaming ON";
        Notification mNotification = new Notification( R.drawable.ic_stat_play, MyText, System.currentTimeMillis()       ); // the time for the notification

        /* Starting an intent */
        String MyNotifyTitle =  getString(R.string.notifTitle);
        String MyNotifiyText  = getString(R.string.notifText);
        Intent MyIntent = new Intent( this, MainActivity.class );
        MyIntent.putExtra("extendedTitle", MyNotifyTitle);
        MyIntent.putExtra("extendedText" , MyNotifiyText);
        PendingIntent StartIntent = PendingIntent.getActivity(  getApplicationContext(),0,MyIntent,0);

        /* Set notification message */
        mNotification.setLatestEventInfo(   getApplicationContext(), MyNotifyTitle, MyNotifiyText, StartIntent);

  
        /* Sent Notification to notification bar */
        notificationManager.notify( NOTIFICATION_ID , mNotification );            

     }
	
	private void deleteNotification(){
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	notificationManager.cancel(NOTIFICATION_ID);
    }

   }

