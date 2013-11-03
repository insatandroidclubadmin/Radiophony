/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tn.iac.radiostreaming;

import java.util.List;

import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.domain.RadioStation;
import tn.iac.radiostreaming.utils.ListArrayAdapter;
import tn.iac.radiostreaming.utils.RadioManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends ListFragment {

	public static final String ARG_COLUMN = "section_column";
	public static final String ARG_VALUE = "section_value";
	
	ArrayAdapter<String> adapter;
	List<String> radioNames;
	Intent radiophonyServiceIntent;
	String column=""; String value="";
	
	public SectionFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		column = getArguments().getString(ARG_COLUMN);
		value = getArguments().getString(ARG_VALUE);
		radioNames = RadioManager.findAllStationNames(column, value);
		adapter = new ListArrayAdapter(getActivity(), radioNames);
		this.radiophonyServiceIntent = new Intent(getActivity(),
				RadiophonyService.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list,
				container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(adapter);
		registerForContextMenu(getListView());
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		TextView itemView = (TextView) ((ViewGroup) view).getChildAt(1);
		String radioName = itemView.getText().toString();

		if (RadiophonyService.getInstance().isPlaying()) {
			RadioStation playingRadio = RadiophonyService.getInstance()
					.getPlayingRadioStation();
			String playingRadioName = playingRadio.getName();

			if (radioName.equals(playingRadioName)) {
				((MainActivity) getActivity()).play(false);
			} else {
				((MainActivity) getActivity()).play(false);
				RadioStation radio = RadioManager.find(RadioDB.COL_NAME,
						radioName);
				RadiophonyService.initialize(getActivity(), radio);
				((MainActivity) getActivity()).play(true);
			}
		} else {
			RadioStation radio = RadioManager.find(RadioDB.COL_NAME, radioName);
			RadiophonyService.initialize(getActivity(), radio);
			((MainActivity) getActivity()).play(true);
		}
	}

	static RadioStation radio;

	/**
	 * Context Menu : when operating a long press on a radio channel
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);

		LinearLayout layout = (LinearLayout) ((AdapterContextMenuInfo) menuInfo).targetView;
		String radioName = ((TextView) (layout).getChildAt(1)).getText()
				.toString();

		menu.setHeaderTitle(radioName);
		getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);

		radio = RadioManager.find(RadioDB.COL_NAME, radioName);
		if (radio.isFavorite()) {
			menu.findItem(R.id.menu_context_favorite).setTitle(
					R.string.option_unset_favorite);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_context_favorite:
			if (!radio.isFavorite()) {
				RadioManager.setFavorite(radio.getName(), true);
			} else {
				RadioManager.setFavorite(radio.getName(), false);
			}
			((MainActivity) getActivity()).refresh();
			break;

		case R.id.menu_context_website:
			String uri = radio.getWebsite();
			Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			startActivity(browser);
			break;

		default:
			break;
		}
		return true;
	}

	public void refresh() {
		radioNames.clear();
		radioNames.addAll(RadioManager.findAllStationNames(column, value));
	    adapter.notifyDataSetChanged();
		
	}
}
