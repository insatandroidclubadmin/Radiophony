package tn.iac.radiostreaming;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final List<String> values;

  public MySimpleArrayAdapter(Context context, List<String> values) {
    super(context, R.layout.rowlayout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
    TextView textView = (TextView) rowView.findViewById(R.id.label);
    textView.setText(values.get(position));
    imageView.setImageResource(R.drawable.play4);
    return rowView;
  }
} 