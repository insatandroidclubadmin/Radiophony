package tn.iac.radiostreaming;

import java.io.IOException;

import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;

public class ClickListener implements OnClickListener {

	MediaPlayer mediaPlayer;
	Radios radios = new Radios();
	
	public ClickListener(MediaPlayer mediaPlayer) {
		super();
		this.mediaPlayer = mediaPlayer;
	}


	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.pause){
			mediaPlayer.pause();
		}
		else{
			mediaPlayer.stop();
			
			mediaPlayer = new MediaPlayer();
			String radioName = view.getTag().toString();
			
			try {
				mediaPlayer.setDataSource(radios.getUrl(radioName));
				mediaPlayer.prepare();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mediaPlayer.start();
		}
	}

}
