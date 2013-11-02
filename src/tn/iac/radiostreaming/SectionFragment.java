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
import tn.iac.radiostreaming.listener.ListArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends ListFragment {

	public static final String ARG_NAMES = "section_names";

	ArrayAdapter<String> adapter;
	List<String> radioNames;

	public SectionFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		radioNames = getArguments().getStringArrayList(ARG_NAMES);
		adapter = new ListArrayAdapter(getActivity(), radioNames);
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
		bundle.putString(RadioDB.COL_NAME, radioName);
		Intent intent = new Intent(getActivity(), RadioActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void pause(){
		if(isAdded()){
			String radioName = RadiophonyService.getInstance().getPlayingRadioStation().getName();
			if(radioNames.contains(radioName)){
				int position = radioNames.indexOf(radioName);
				ViewGroup view = (ViewGroup) adapter.getView(position, null, null);
				ImageView play = (ImageView) view.findViewById(R.id.row_play);
				play.setImageResource(0);
			}
		}
	}
}
