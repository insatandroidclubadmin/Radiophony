package tn.iac.radiostreaming.listener;

import java.util.List;

import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.RadiophonyService;
import tn.iac.radiostreaming.db.RadioDB;
import tn.iac.radiostreaming.db.RadioManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> values;

	public ListArrayAdapter(Context context, List<String> values) {
		super(context, R.layout.row_layout, values);
		this.context = context;
		this.values = values;
	}


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View rowView = inflater.inflate(R.layout.row_layout, parent, false);
    ImageView play = (ImageView) rowView.findViewById(R.id.row_play);
    TextView label = (TextView) rowView.findViewById(R.id.row_label);
    ImageView logo = (ImageView) rowView.findViewById(R.id.row_logo);
    
    label.setText(values.get(position));
    String logoName = RadioManager.find(RadioDB.COL_NAME, values.get(position)).getLogo();
    logo.setImageResource(context.getResources().getIdentifier("drawable/logo_"+logoName, "drawable", context.getPackageName()));
    
    if(RadiophonyService.getInstance().isPlaying()){
    	String radioName = RadiophonyService.getInstance().getPlayingRadioStation().getName();
    	if(radioName.equals(values.get(position))){
    		play.setImageResource(R.drawable.main_play);
    	}
    	
	}
    
    return rowView;
  }
} 
