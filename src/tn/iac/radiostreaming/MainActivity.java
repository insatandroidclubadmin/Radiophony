package tn.iac.radiostreaming;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


import tn.iac.radiostreaming.bd.RadioChannel;
import tn.iac.radiostreaming.bd.RadioChannelTable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int NOTIFICATION_ID = 1;
	
	ImageView ChannelButton,pauseButton;
	NotificationManager notificationManager;
	RadioChannelTable radioChannels;
	ClickListener clickListener;
	List<RadioChannel> myChannels;
	List<String> AllUrls;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioChannels = new RadioChannelTable(this);
		
		//***************************STAR*******************************************
		
		
		
		//***************************TEXT DEFILANT *********************************
		TextView tView = (TextView) findViewById(R.id.scrollingText);
		
		tView.setSelected(true);
		//**************************************************************************
		
		
		
		setTheme(R.style.WidgetBackground);
		pauseButton = (ImageView) findViewById(R.id.pause);	

		clickListener = new ClickListener(MainActivity.this, radioChannels);
		pauseButton.setOnClickListener(clickListener);
		
		//*********************************Interface*********************************************
		//On récupére toutes les chaines radio	
		myChannels = radioChannels.getAllRadioChannels();
		//On met leurs noms dans une liste		
		AllUrls=new LinkedList<String>();
		for(int i=0;i<myChannels.size();i++){
			AllUrls.add(myChannels.get(i).getName());		
		}
		
		ListView listView = (ListView) findViewById(R.id.list);	
		ArrayAdapter<String> adapter = new MySimpleArrayAdapter(this,AllUrls);	
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(clickListener);
		
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
	
	private final void createNotification(){
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /* Create a notification */
        String MyText = "Radio streaming ON";
        Notification mNotification = new Notification(
               R.drawable.ic_stat_play,                // An Icon to display
               MyText,                         // the text to display in the ticker
               System.currentTimeMillis()       ); // the time for the notification

        /* Starting an intent */
        String MyNotifyTitle = "Radio streaming ON";
        String MyNotifiyText  = "Go to the Radio Streaming application ...";
        Intent MyIntent = new Intent( this, MainActivity.class );
        MyIntent.putExtra("extendedTitle", MyNotifyTitle);
        MyIntent.putExtra("extendedText" , MyNotifiyText);
        PendingIntent StartIntent = PendingIntent.getActivity(  getApplicationContext(),
                                                  0,
                                                  MyIntent,
                                                  0);

        /* Set notification message */
        mNotification.setLatestEventInfo(   getApplicationContext(),
                                   MyNotifyTitle,
                                   MyNotifiyText,
                                   StartIntent);

  
        /* Sent Notification to notification bar */
        notificationManager.notify( NOTIFICATION_ID , mNotification );            

     }
	
	private void deleteNotification(){
    	final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    	notificationManager.cancel(NOTIFICATION_ID);
    }

	public RadioChannelTable getRadioChannels() {
		return radioChannels;
	}
	
	

   }

