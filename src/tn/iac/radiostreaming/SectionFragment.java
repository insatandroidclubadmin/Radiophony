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

import tn.iac.radiostreaming.db.RadioStationTable;
import tn.iac.radiostreaming.domain.RadioStation;
import tn.iac.radiostreaming.listener.ListArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends ListFragment {

	public static final String ARG_NAMES = "section_names";

	RadioStationTable radioStations;
	ArrayAdapter<String> adapter;
	List<String> stationNames;

	public SectionFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stationNames = getArguments().getStringArrayList(ARG_NAMES);
		radioStations = new RadioStationTable(getActivity());
		adapter = new ListArrayAdapter(getActivity(), stationNames,
				radioStations);
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
		Bundle bundle = new Bundle();
		bundle.putString(RadioStationTable.COL_NAME, radioName);
		Intent intent = new Intent(getActivity(), RadioActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
