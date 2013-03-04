package tn.iac.radiostreaming.listener;

import java.io.IOException;
import tn.iac.radiostreaming.MainActivity;
import tn.iac.radiostreaming.R;
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
	TextView scrollingText;
	ImageView playerButton;
	String radioName;
	boolean playing;
	

	public ClickListener(Context applicationContext,
			RadioChannelTable radioChannelTable) {
		super();
		this.applicationContext = applicationContext;
		this.radioChannelTable = radioChannelTable;
		this.playerButton = (ImageView) ((MainActivity)applicationContext).findViewById(R.id.pause);
		playing = false;
	}

	@Override
	public synchronized void onClick(View view) {

		try {
			playerButton = (ImageView) view;

			if (playing) {			
				playerButton.setImageResource(R.drawable.play4);
				mediaPlayer.pause();			
				playing = false;
			}
			
			else {
				playerButton.setImageResource(R.drawable.pause);
				new ProgressTask().execute();
			}

		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		
		TextView itemView = (TextView) ((ViewGroup) view).getChildAt(1);
		this.radioName = itemView.getText().toString();
		this.scrollingText = (TextView) ((MainActivity) applicationContext)
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
            
            this.dialog.dismiss();
            	
            if(success){
	            mediaPlayer.start();
	            
				Log.d("status", "playing");
				
				playing = true;
				playerButton.setOnClickListener(ClickListener.this);
				scrollingText.setText(applicationContext.getResources().getText(
						R.string.playing)
						+ " " + radioName + " ---------------");
				playerButton.setImageResource(R.drawable.pause);
            }
            else{
            	Toast.makeText(applicationContext,
						applicationContext.getString(R.string.exception_network), Toast.LENGTH_SHORT)
						.show();
            }
			
        }

        protected Boolean doInBackground(final String... args) {
        	
        	try {
				mediaPlayer = new MediaPlayer();
				String url = radioChannelTable.getRadioChannelByCol(
						RadioChannelTable.COL_NAME, radioName).getUrl();
				
				Log.d("status", "charging");

				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				return true;

			} catch (IllegalArgumentException e1) {
				// Toast.makeText(applicationContext, "Illegal argument problem",Toast.LENGTH_SHORT).show();
			} catch (SecurityException e1) {
				// Toast.makeText(applicationContext, "Security problem", Toast.LENGTH_SHORT).show();
			} catch (IllegalStateException e1) {
				// Toast.makeText(applicationContext, "Illegal state problem",	Toast.LENGTH_SHORT).show();
			} catch (IOException e1) {
				//Toast.makeText(applicationContext, applicationContext.getString(R.string.exception_network), Toast.LENGTH_SHORT).show();

				Log.d("debug", "IOException");
			}
        	return false;
        }


    }
	

}
