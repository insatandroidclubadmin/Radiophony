package tn.iac.radiostreaming.listener;

import java.io.IOException;

import tn.iac.radiostreaming.MainActivity;
import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.R.drawable;
import tn.iac.radiostreaming.R.id;
import tn.iac.radiostreaming.R.string;
import tn.iac.radiostreaming.db.RadioChannelTable;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
	View view;
	TextView tView;
	String item;
	boolean playing;
	String lastChannelPlaying;


	public ClickListener(Context applicationContext,
			RadioChannelTable radioChannelTable) {
		super();
		this.applicationContext = applicationContext;
		this.radioChannelTable = radioChannelTable;
		this.lastChannelPlaying = "";
		playing = false;
	}

	@Override
	public synchronized void onClick(View view) {

		try {
			ImageView imageView = (ImageView) view;

			if (playing) {			
				//imageView.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.pauze));
				imageView.setImageResource(R.drawable.play4);
				Thread.sleep(12);
				//imageView.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.pause));
				mediaPlayer.pause();			
				playing = false;
			}
			
			else {
				imageView.setImageResource(R.drawable.pause);
				new ProgressTask().execute();
				
			}

		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		this.view = view;
		TextView itemView = (TextView) ((ViewGroup) view).getChildAt(1);
		this.item = itemView.getText().toString();
		this.tView = (TextView) ((MainActivity) applicationContext)
				.findViewById(R.id.scrollingText);

		try {
			if (playing) {
				mediaPlayer.stop();
				playing = false;
			}
			new ProgressTask().execute();

		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean isPlaying() {
		return playing;
	}

	public void stopMediaPlayer() {
		if(mediaPlayer != null){
			mediaPlayer.stop();
			mediaPlayer=null;
			playing = false;
		}
	}
	
	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        
        public ProgressTask() {
            dialog = new ProgressDialog(applicationContext);
        }

        protected void onPreExecute() {
            this.dialog.setMessage("Loading ...");
            this.dialog.show();
        }

            @Override
        protected void onPostExecute(final Boolean success) {
            mediaPlayer.start();

			Log.d("status", "playing");
			
			playing = true;
			tView.setText(applicationContext.getResources().getText(
					R.string.playing)
					+ " " + item + " ---------------");
			this.dialog.dismiss();
        }

        protected Boolean doInBackground(final String... args) {
        	
        	try {
				mediaPlayer = new MediaPlayer();
				String url = radioChannelTable.getRadioChannelByCol(
						RadioChannelTable.COL_NAME, item).getUrl();
				

				Log.d("status", "charging");

				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				mediaPlayer.start();
				return true;

			} catch (IllegalArgumentException e1) {
				Toast.makeText(applicationContext, "Illegal argument problem",
						Toast.LENGTH_SHORT).show();
			} catch (SecurityException e1) {
				Toast.makeText(applicationContext, "Security problem",
						Toast.LENGTH_SHORT).show();
			} catch (IllegalStateException e1) {
				Toast.makeText(applicationContext, "Illegal state problem",
						Toast.LENGTH_SHORT).show();
			} catch (IOException e1) {
				Toast.makeText(applicationContext,
						applicationContext.getString(R.string.exception_network), Toast.LENGTH_SHORT)
						.show();
			}
        	return false;
        }


    }
	

}
