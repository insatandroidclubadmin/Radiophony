package tn.iac.radiostreaming;

import java.util.ArrayList;
import java.util.List;

import tn.iac.radiostreaming.db.RadioStationTable;
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

public class MainActivity extends FragmentActivity {

	RadioStationTable radioStations;
	
	FragmentStatePagerAdapter pagerAdapter;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		viewPager.setAdapter(pagerAdapter);
		
		radioStations = new RadioStationTable(this);

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
				column = RadioStationTable.COL_FLAG;
				value = RadioStation.FAVORITE + "";
				break;
			case 1:
				column = RadioStationTable.COL_TYPE;
				value = RadioStation.NATIONAL + "";
				break;
			case 2:
				column = RadioStationTable.COL_TYPE;
				value = RadioStation.INTERNATIONAL + "";
				break;
			}
			
			List<String> stationNames = radioStations.findAllStationNames(column, value);
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
