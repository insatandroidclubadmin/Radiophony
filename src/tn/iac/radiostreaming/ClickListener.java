package tn.iac.radiostreaming;

import java.io.IOException;

import tn.iac.radiostreaming.bd.RadioChannel;
import tn.iac.radiostreaming.bd.RadioChannelTable;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ClickListener implements OnClickListener {

	MediaPlayer mediaPlayer;
	Radios radios = new Radios();
	RadioChannelTable radioChannelTable; 
	Context applicationContext;
	boolean playing;
	
	public ClickListener(Context applicationContext, RadioChannelTable radioChannelTable) {
		super();
		this.applicationContext = applicationContext;
		this.radioChannelTable= radioChannelTable;
		playing = false;
	}


	@Override
	public synchronized void onClick(View view) {
		try{
		Log.d("aa", "try");
		if(view.getId()==R.id.pause){
			Log.d("aa", "if pause");
			if (playing)
				mediaPlayer.pause();
			playing = false;
			Log.d("aa", "paused");
		}
		else{
			if(playing){
				mediaPlayer.stop();
				playing= false;
				Log.d("aa", "stopping");
			}
				
			mediaPlayer = new MediaPlayer();
			String radioName = view.getTag().toString();
			Log.d("aa", "new media");
			try {
				String url = radioChannelTable.getRadioChannel(radioName).getUrl();
				Log.d("el url", url);
				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				mediaPlayer.start();
				Log.d("aa", "started");
				playing = true;
			} catch (IllegalArgumentException e1) {
				Toast.makeText(applicationContext,
						"Illegal argument probelm", Toast.LENGTH_SHORT).show();
			} catch (SecurityException e1) {
				Toast.makeText(applicationContext,
						"Security problem", Toast.LENGTH_SHORT).show();
			} catch (IllegalStateException e1) {
				Toast.makeText(applicationContext,
						"Illegal state problem", Toast.LENGTH_SHORT).show();
			} catch (IOException e1) {
				Toast.makeText(applicationContext,
						"Network connection disabled", Toast.LENGTH_SHORT).show();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		}catch (Exception e) {
			Toast.makeText(applicationContext,
					"Big Undefined Problem", Toast.LENGTH_SHORT).show();
		}
	}

}
