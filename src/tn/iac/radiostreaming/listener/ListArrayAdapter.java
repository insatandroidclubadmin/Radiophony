package tn.iac.radiostreaming.listener;

import java.util.List;

import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.R.drawable;
import tn.iac.radiostreaming.R.id;
import tn.iac.radiostreaming.R.layout;
import tn.iac.radiostreaming.db.RadioChannel;
import tn.iac.radiostreaming.db.RadioChannelTable;

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
	private final RadioChannelTable radioChannelTable;

	public ListArrayAdapter(Context context, List<String> values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
		radioChannelTable = new RadioChannelTable(context);
	}


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    ImageView play = (ImageView) rowView.findViewById(R.id.icon);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    ImageView logo = (ImageView) rowView.findViewById(R.id.logo);
    
    textView.setText(values.get(position));
    play.setImageResource(R.drawable.play4);
//    RadioChannel radioChannel = radioChannelTable.getRadioChannelByCol(RadioChannelTable.COL_NAME, values.get(position));
    logo.setImageResource(context.getResources().getIdentifier("drawable/logo_mosaiquefm", "drawable", context.getPackageName()));
    return rowView;
  }
} 
