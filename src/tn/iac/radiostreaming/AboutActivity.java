package tn.iac.radiostreaming;

import java.util.List;
import tn.iac.radiostreaming.db.RadioChannelTable;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutActivity extends Activity{
	
	RadioChannelTable radioChannels;
	List<String> channelUrls;
	ListView listView;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setTheme(R.style.WidgetBackground);
	}

}
