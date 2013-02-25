package tn.iac.radiostreaming;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

public class PreparedListener implements OnPreparedListener {

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		mediaPlayer.start();
	}

}
