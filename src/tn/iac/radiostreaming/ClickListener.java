package tn.iac.radiostreaming;

import java.io.IOException;
import java.io.InputStream;


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
<<<<<<< HEAD
		try{
			if(playing){
				ImageView imageView = (ImageView)view;
				imageView.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.pauze));
				wait(400);
				imageView.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.pause));
				mediaPlayer.pause();
				playing = false;
			}

		}catch (Exception e) {
			Toast.makeText(applicationContext,
					"Big Undefined Problem", Toast.LENGTH_SHORT).show();
=======
		int id = view.getId();
		try {
			
			if(playing){
				playing = false;
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(applicationContext.getResources()
						.getDrawable(R.drawable.pauze));
				mediaPlayer.pause();
			} else{
				playing= true;
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(applicationContext.getResources()
						.getDrawable(R.drawable.pause));
			}
		
		} catch (Exception e) {
			Toast.makeText(applicationContext, "Big Undefined Problem",
					Toast.LENGTH_SHORT).show();
>>>>>>> 5825addfb8b4aedb0566fdb63c834f6fd003cc5a
		}
			
	}

	@Override
<<<<<<< HEAD
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		
		TextView itemView = (TextView)((ViewGroup)view).getChildAt(1);
		String item = itemView.getText().toString();
		TextView tView = (TextView) ((MainActivity)applicationContext).findViewById(R.id.scrollingText);
		Toast.makeText(applicationContext,item,Toast.LENGTH_LONG).show(); 
		
		try{
				if(playing){
					mediaPlayer.stop();
					playing= false;
				}
				
				try {
					mediaPlayer = new MediaPlayer();
					String url = radioChannelTable.getNameRadioChannel(item).getUrl();
					
					Log.d("status", "charging");
					
					mediaPlayer.setDataSource(url);
					mediaPlayer.prepare();
					mediaPlayer.start();
					
					Log.d("status", "playing");
					
					playing = true;
					tView.setText("---------------Playing now: "+item+"---------------");
					
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
					Toast.makeText(applicationContext,
							"Stange problem", Toast.LENGTH_SHORT).show();
				}
			
			}catch (Exception e) {
=======
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {

		ImageView imageView = (ImageView) ((ViewGroup) view).getChildAt(0);
		TextView itemView = (TextView) ((ViewGroup) view).getChildAt(1);
		imageView.setImageDrawable(applicationContext.getResources()
				.getDrawable(R.drawable.buble));
		String item = itemView.getText().toString();
		// ***************SCROLLING TEXT*********************************
		TextView tView = (TextView) ((MainActivity) applicationContext)
				.findViewById(R.id.scrollingText);
		tView.setText("---------------Playing now: " + item + "---------------");
		// **************************************************************

		Toast.makeText(applicationContext, item, Toast.LENGTH_LONG).show();
		try {
			if (playing) {
				mediaPlayer.stop();
				playing = false;
			}

			try {
				mediaPlayer = new MediaPlayer();
				String url = radioChannelTable.getNameRadioChannel(item)
						.getUrl();

				Log.d("status", "charging");
				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				mediaPlayer.start();
				// ImageView equalizer = (ImageView)
				// ((ViewGroup)view).getChildAt(1);

				// equalizer.setImageDrawable(applicationContext.getResources().getDrawable(R.drawable.eq));
				Log.d("status", "playing");
				playing = true;
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
>>>>>>> 5825addfb8b4aedb0566fdb63c834f6fd003cc5a
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

}
