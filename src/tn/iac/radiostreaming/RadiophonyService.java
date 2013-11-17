package tn.iac.radiostreaming;

import java.io.IOException;

import tn.iac.radiostreaming.MainActivity;
import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.domain.RadioStation;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class RadiophonyService extends Service {
	static private final int NOTIFICATION_ID = 1;
	
	static private RadiophonyService service;
	static private Context context;
	static private MediaPlayer mediaPlayer;

	static private RadioStation station;
	static public int list;
	private WifiLock wifiLock;
	static ProgressDialog dialog;
	static ProgressTask task;

	
	static public void initialize(Context context, RadioStation station) {
		RadiophonyService.context = context;
		RadiophonyService.station = station;
	}
	
	static public RadiophonyService getInstance() {
		if(service == null){
			service = new RadiophonyService();
		}
		return service;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		mediaPlayer = new MediaPlayer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		task =new ProgressTask();
		task.execute();
		return START_NOT_STICKY;
	}

	public void onDestroy() {
		stop();
	}
	
	public void onStop(){
		if(dialog != null && dialog.isShowing()){
			task.cancel(true);
		}
	}
	
	public void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			wifiLock.release();
			stopForeground(true); // delete notification
		}
	}

	public boolean isPlaying() {
		return (mediaPlayer != null && mediaPlayer.isPlaying());
	}

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {

		public ProgressTask() {
			dialog = new ProgressDialog(context);
		}

		protected void onPreExecute() {
			dialog.setMessage(context.getString(R.string.mp_loading) + " "
					+ station.getName() + "...");
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					ProgressTask.this.cancel(true);
					RadiophonyService.this.stopSelf();
				}
			});
		}

		protected Boolean doInBackground(final String... args) {

			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDataSource(station.getUrl());
				mediaPlayer.prepare();
				return true;

			} catch (IllegalArgumentException e1) {
				// Toast.makeText(applicationContext,
				// "Illegal argument problem",Toast.LENGTH_SHORT).show();
			} catch (SecurityException e1) {
				// Toast.makeText(applicationContext, "Security problem",
				// Toast.LENGTH_SHORT).show();
			} catch (IllegalStateException e1) {
				// Toast.makeText(applicationContext, "Illegal state problem",
				// Toast.LENGTH_SHORT).show();
			} catch (IOException e1) {
				// Toast.makeText(applicationContext,
				// applicationContext.getString(R.string.exception_network),
				// Toast.LENGTH_SHORT).show();
			}
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			dialog.dismiss();

			if (success) {
				wifiLock = ((WifiManager) context
						.getSystemService(Context.WIFI_SERVICE))
						.createWifiLock(WifiManager.WIFI_MODE_FULL,
								"RadiophonyLock");
				wifiLock.acquire();

				mediaPlayer.start();
				((MainActivity)context).notifyShowBar();
				createNotification();
			} else {
				Toast.makeText(context,
						context.getString(R.string.exception_network),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@SuppressWarnings("deprecation")
	private void createNotification() {
		Notification notification;
		Bundle bundle = new Bundle();
		bundle.putInt("list", list);
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),
				0, new Intent(getApplicationContext(), MainActivity.class)
						.putExtras(bundle), PendingIntent.FLAG_UPDATE_CURRENT);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			notification = new Notification();
			notification.tickerText = station.getName();
			notification.icon = R.drawable.ic_stat_play;
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			notification.setLatestEventInfo(getApplicationContext(),
					station.getName(), null, pi);
		} else {
			notification = new NotificationCompat.Builder(context)
					.setContentTitle(station.getName())
					.setSmallIcon(R.drawable.ic_stat_play)
					.setLargeIcon(
							BitmapFactory.decodeResource(
									context.getResources(),
									context.getResources().getIdentifier(
											"drawable/logo_"
													+ station.getLogo(),
											"drawable",
											context.getPackageName())))
					.setOngoing(true).setContentIntent(pi).build();
		}

		startForeground(NOTIFICATION_ID, notification);
	}

	public RadioStation getPlayingRadioStation() {
		return station;
	}
}
