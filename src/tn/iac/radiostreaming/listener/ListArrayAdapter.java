package tn.iac.radiostreaming.listener;

import java.util.List;

import tn.iac.radiostreaming.R;
import tn.iac.radiostreaming.db.RadioStationTable;
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
	private final RadioStationTable radioChannelTable;

	public ListArrayAdapter(Context context, List<String> values, RadioStationTable radioChannelTable) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
		this.radioChannelTable = radioChannelTable;
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
    String logoName = radioChannelTable.find(RadioStationTable.COL_NAME, values.get(position)).getLogo();
    logo.setImageResource(context.getResources().getIdentifier("drawable/logo_"+logoName, "drawable", context.getPackageName()));
    return rowView;
  }
} 
