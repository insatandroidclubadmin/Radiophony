package tn.iac.radiostreaming;

import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.db.RadioManager;
import tn.iac.radiostreaming.domain.RadioStation;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class RadioActivity extends Activity {

	RadiophonyService radiophonyService;
	Intent radiophonyServiceIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio);
		// Show the Up button in the action bar.
		setupActionBar();
		
		this.radiophonyServiceIntent = new Intent(this, RadiophonyService.class);
		this.radiophonyService = RadiophonyService.getInstance();
		
		String name = getIntent().getExtras().getString(RadioDB.COL_NAME);
		RadioStation station = RadioManager.find(RadioDB.COL_NAME, name);

		if (radiophonyService.isPlaying()) {
			stopService(radiophonyServiceIntent);
		}
		RadiophonyService.initialize(this, station);
		startService(radiophonyServiceIntent);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
