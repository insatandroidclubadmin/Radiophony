package tn.iac.radiostreaming;

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
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final int NOTIFICATION_ID = 1;
	
	ImageView mosaiqueButton, shemsButton , pauseButton;
	Radios radios = new Radios();
	NotificationManager notificationManager;
	RadioChannelTable radioChannels;
	ClickListener clickListener;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//***************************INITIALISATION BASE***************************************
		radioChannels = new RadioChannelTable(this);
		radioChannels.open();
		
		radioChannels.fillInitialTable();
		
		//On récupére la chaine grâce au nom choisi par l'utilisateur (implicitement, lors du clic sur play
		//RadioChannel radiochannel=radioChannels.getRadioChannel(name.getText().toString());  
		//***************************************************************************************
		mosaiqueButton = (ImageView) findViewById(R.id.playMozaique);
		shemsButton = (ImageView) findViewById(R.id.playShems);
		pauseButton = (ImageView) findViewById(R.id.pause);
		
		clickListener = new ClickListener(MainActivity.this, radioChannels);
		
		mosaiqueButton.setOnClickListener(clickListener);
		shemsButton.setOnClickListener(clickListener);
		pauseButton.setOnClickListener(clickListener);
		
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

   }

