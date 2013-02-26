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
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView mosaiqueButton, shemsButton , pauseButton;
	MediaPlayer mediaPlayer = null;
	Radios radios = new Radios();
	NotificationManager notificationManager;
	RadioChannelTable radioChannels;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createNotification();
		
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
		
		OnClickListener clickListener = new ClickListener(MainActivity.this);
		
		mosaiqueButton.setOnClickListener(clickListener);
		shemsButton.setOnClickListener(clickListener);
		pauseButton.setOnClickListener(clickListener);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer = null;
	}
	
	private final void createNotification(){
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /* Create a notification */
        String MyText = "Text inside: By Firstdroid.com";
        Notification mNotification = new Notification(
               R.drawable.back,                // An Icon to display
               MyText,                         // the text to display in the ticker
               System.currentTimeMillis()       ); // the time for the notification

        /* Starting an intent */
        String MyNotifyTitle = "Firstdroid Rocks!!!";
        String MyNotifiyText  = "Firstdroid: our forum at www.firstdroid.com";
        Intent MyIntent = new Intent( getApplicationContext(), MainActivity.class );
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
        notificationManager.notify(  1 , mNotification );            

     }/* End of method onClick */

   }

