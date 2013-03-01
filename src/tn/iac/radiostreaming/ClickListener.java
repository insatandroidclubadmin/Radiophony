package tn.iac.radiostreaming;

import java.io.IOException;
import tn.iac.radiostreaming.bd.RadioChannelTable;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ClickListener implements OnClickListener, OnItemClickListener {

	MediaPlayer mediaPlayer;
	RadioChannelTable radioChannelTable;
	Context applicationContext;
	boolean playing;

	public ClickListener(Context applicationContext,
			RadioChannelTable radioChannelTable) {
		super();
		this.applicationContext = applicationContext;
		this.radioChannelTable = radioChannelTable;
		playing = false;
	}

	@Override
	public synchronized void onClick(View view) {

		try {

			if (playing) {
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(applicationContext.getResources()
						.getDrawable(R.drawable.pauze));
				Thread.sleep(12);
				imageView.setImageDrawable(applicationContext.getResources()
						.getDrawable(R.drawable.pause));
				mediaPlayer.pause();
				playing = false;
			}

		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {

		TextView itemView = (TextView) ((ViewGroup) view).getChildAt(1);
		String item = itemView.getText().toString();
		TextView tView = (TextView) ((MainActivity) applicationContext)
				.findViewById(R.id.scrollingText);
		Toast.makeText(applicationContext, item, Toast.LENGTH_LONG).show();

		try {
			if (playing) {
				mediaPlayer.stop();
				playing = false;
			}

			try {
				mediaPlayer = new MediaPlayer();
				String url = radioChannelTable.getRadioChannelByCol(
						RadioChannelTable.COL_NAME, item).getUrl();

				Log.d("status", "charging");

				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				mediaPlayer.start();

				Log.d("status", "playing");

				playing = true;
				tView.setText(applicationContext.getResources().getText(
						R.string.playin)
						+ item + "---------------");

			} catch (IllegalArgumentException e1) {
				Toast.makeText(applicationContext, "Illegal argument probelm",
						Toast.LENGTH_SHORT).show();
			} catch (SecurityException e1) {
				Toast.makeText(applicationContext, "Security problem",
						Toast.LENGTH_SHORT).show();
			} catch (IllegalStateException e1) {
				Toast.makeText(applicationContext, "Illegal state problem",
						Toast.LENGTH_SHORT).show();
			} catch (IOException e1) {
				Toast.makeText(applicationContext,
						"Network connection disabled", Toast.LENGTH_SHORT)
						.show();
			} catch (Exception e) {
				Toast.makeText(applicationContext, "Stange problem",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isPlaying() {
		return playing;
	}

	public void stopMediaPlayer() {
		mediaPlayer.stop();
		mediaPlayer=null;
		playing = false;
	}
	
	

}
