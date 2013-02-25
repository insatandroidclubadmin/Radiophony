package tn.iac.radiostreaming;

import java.io.IOException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button startButton, pauseButton;
	MediaPlayer mediaPlayer = null;
	Radios radios = new Radios();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
/*		
		startButton = (Button) findViewById(R.id.start);
		pauseButton = (Button) findViewById(R.id.pause);
*/		
		mediaPlayer = new MediaPlayer();
		OnClickListener clickListener = new ClickListener(mediaPlayer);
		mediaPlayer.setOnPreparedListener(new PreparedListener());
/*		
		startButton.setOnClickListener(clickListener);
		pauseButton.setOnClickListener(clickListener);
*/		
		try {
			mediaPlayer.setDataSource(radios.getUrl("express"));

		} catch (IllegalArgumentException e) {
			Toast.makeText(MainActivity.this, "erreurIllegalArgument",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(MainActivity.this, "erreurIllegalState",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(MainActivity.this, "erreurIO", Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
		
		mediaPlayer.prepareAsync();
	}

}
