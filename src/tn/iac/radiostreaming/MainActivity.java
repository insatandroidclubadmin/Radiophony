package tn.iac.radiostreaming;

import java.util.ArrayList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.db.RadioManager;
import tn.iac.radiostreaming.domain.RadioStation;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	
	FragmentStatePagerAdapter pagerAdapter;
	ViewPager viewPager;
	RelativeLayout bar; 
	ImageView logo, pause;
	TextView station;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		viewPager.setAdapter(pagerAdapter);
		
		RadioManager.init(getApplicationContext());

		if(RadiophonyService.getInstance().isPlaying()){
			RadioStation radioStation = RadiophonyService.getInstance().getPlayingRadioStation();
			bar = (RelativeLayout) findViewById(R.id.main_bar);
			logo = (ImageView) findViewById(R.id.main_bar_logo);
			station = (TextView) findViewById(R.id.main_bar_station);
			pause = (ImageView) findViewById(R.id.main_pause);
			
			logo.setImageResource(getResources().getIdentifier("drawable/logo_" + radioStation.getLogo(),
						"drawable", getPackageName()));
			station.setText(radioStation.getName());
			bar.setVisibility(View.VISIBLE);
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
			
			List<String> stationNames = RadioManager.findAllStationNames(column, value);
			SectionFragment fragment = new SectionFragment();
			Bundle args = new Bundle();
			args.putStringArrayList(SectionFragment.ARG_NAMES, (ArrayList<String>) stationNames);
	        fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
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

}
