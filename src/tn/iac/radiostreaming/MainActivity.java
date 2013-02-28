package tn.iac.radiostreaming;

import java.util.LinkedList;
import java.util.List;
import tn.iac.radiostreaming.bd.RadioChannel;
import tn.iac.radiostreaming.bd.RadioChannelTable;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int NOTIFICATION_ID = 1;
	
	ImageView ChannelButton,pauseButton;
	Button backButton;
	NotificationManager notificationManager;
	RadioChannelTable radioChannels;
	ClickListener clickListener;
	List<RadioChannel> channels;
	List<String> channelNames;
	ArrayAdapter<String> adapter;
	TextView scrollingText;
	ListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTheme(R.style.WidgetBackground);
		
		radioChannels = new RadioChannelTable(this);
<<<<<<< HEAD
		clickListener = new ClickListener(MainActivity.this, radioChannels);
=======
		
		setTheme(R.style.WidgetBackground);
		pauseButton = (ImageView) findViewById(R.id.pause);	
		backButton = (ImageView) findViewById(R.drawable.back45);

		clickListener = new ClickListener(MainActivity.this, radioChannels);
		pauseButton.setOnClickListener(clickListener);
		backButton.setOnClickListener(new View.OnClickListener() {
 
			public void onClick(View v) {
				MainActivity.this.finish();
 
			}
		});
>>>>>>> 5825addfb8b4aedb0566fdb63c834f6fd003cc5a
		
		scrollingText = (TextView) findViewById(R.id.scrollingText);
		pauseButton = (ImageView) findViewById(R.id.pause);
		listView = (ListView) findViewById(R.id.list);				
		
		channels = radioChannels.getAllRadioChannels();	
		channelNames = new LinkedList<String>();
		for(int i=0 ; i<channels.size() ; i++){
			channelNames.add(channels.get(i).getName());		
		}
			
		adapter = new MySimpleArrayAdapter(this, channelNames);	
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(clickListener);
		
		pauseButton.setOnClickListener(clickListener);
		scrollingText.setSelected(true);
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
        String MyNotifyTitle =  (String)findViewById(R.string.notifTitle).toString();
        String MyNotifiyText  = (String)findViewById(R.string.notifText).toString();
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

