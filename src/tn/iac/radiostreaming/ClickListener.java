package tn.iac.radiostreaming;

import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;

public class ClickListener implements OnClickListener {

	MediaPlayer mediaPlayer;
	
	public ClickListener(MediaPlayer mediaPlayer) {
		super();
		this.mediaPlayer = mediaPlayer;
	}


	@Override
	public void onClick(View view) {
/*		switch (view.getId()) {
		
		case R.id.start:
			mediaPlayer.start();
			break;

		case R.id.pause:
			mediaPlayer.pause();
			break;
		default:
			mediaPlayer.stop();
		}
*/
	}

}
