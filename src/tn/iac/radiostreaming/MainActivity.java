package tn.iac.radiostreaming;

import java.util.ArrayList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.domain.RadioStation;
import tn.iac.radiostreaming.utils.RadioManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private static final int NB_FRAGMENTS = 3;
	
	FragmentStatePagerAdapter pagerAdapter;
	ViewPager viewPager;
	RelativeLayout bar; 
	ImageView logo, pause;
	TextView station;
	List<SectionFragment> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		viewPager.setAdapter(pagerAdapter);
		
		RadioManager.init(getApplicationContext());
		
		fragments = new ArrayList<SectionFragment>();
		for (int i = 0; i < NB_FRAGMENTS; i++) {
			fragments.add(new SectionFragment());
		}
		
		if(RadiophonyService.getInstance().isPlaying()){
			notifyShowBar();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent intent = new Intent(this, AboutActivity.class);
			startActivityIfNeeded(intent, -1);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			String column="", value="";
			switch (position) {
			case 0:
				column = RadioDB.COL_FLAG;
				value = RadioStation.FAVORITE + "";
				break;
			case 1:
				column = RadioDB.COL_TYPE;
				value = RadioStation.NATIONAL + "";
				break;
			case 2:
				column = RadioDB.COL_TYPE;
				value = RadioStation.INTERNATIONAL + "";
				break;
			}
			
			Bundle args = new Bundle();
			args.putString(SectionFragment.ARG_COLUMN, column);
			args.putString(SectionFragment.ARG_VALUE, value);
	        fragments.get(position).setArguments(args);
			return fragments.get(position);
		}

		@Override
		public int getCount() {
			return NB_FRAGMENTS;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_activity_favorite);
			case 1:
				return getString(R.string.title_activity_national);
			case 2:
				return getString(R.string.title_activity_international);
			}
			return null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_pause:
			play(false);
			break;

		default:
			break;
		}
	}
	
	public void play(boolean toPlay){
		if(!toPlay){
			stopService(new Intent(this, RadiophonyService.class));
			bar.setVisibility(View.GONE);
		}else{
			startService(new Intent(this, RadiophonyService.class));
			//wait for the service to notify success, then notifyShowBar() will be called
		}
	}

	//Called by service, this method show the bar. It does smth else too, it refreshed implicitly the listView (play button before the the radio station name will be displayed too (calling getView of the Adapter)
	public void notifyShowBar() {
		RadioStation radioStation = RadiophonyService.getInstance().getPlayingRadioStation();
		bar = (RelativeLayout) findViewById(R.id.main_bar);
		logo = (ImageView) findViewById(R.id.main_bar_logo);
		station = (TextView) findViewById(R.id.main_bar_station);
		pause = (ImageView) findViewById(R.id.main_pause);
		pause.setOnClickListener(this);
		
		logo.setImageResource(getResources().getIdentifier("drawable/logo_" + radioStation.getLogo(),
					"drawable", getPackageName()));
		station.setText(radioStation.getName());
		bar.setVisibility(View.VISIBLE);
	}

	public void refresh() {
		for (int i = 0; i < NB_FRAGMENTS; i++) {
			if(fragments.get(i).isAdded())
				fragments.get(i).refresh();
		}
		
	}

	
	@Override
	public void onStop(){
		RadiophonyService.getInstance().onStop();
		super.onStop();
	}
}
