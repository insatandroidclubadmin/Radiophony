package tn.iac.radiostreaming;

import tn.iac.radiostreaming.db.RadioChannel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloActivity extends Activity implements OnClickListener {

	Button national, international, favorite;
	Button quit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello);

		national = (Button) findViewById(R.id.national);
		national.setOnClickListener(this);

		international = (Button) findViewById(R.id.international);
		international.setOnClickListener(this);
		
		favorite = (Button) findViewById(R.id.favorite);
		favorite.setOnClickListener(this);
		
		quit = (Button) findViewById(R.id.quit);
		quit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
	}

	@Override
	public void onClick(View view) {
		Bundle bundle = new Bundle();
		
		switch (view.getId()) {
		case R.id.national:
			bundle.putInt("list", RadioChannel.NATIONAL);
			break;
		case R.id.international:
			bundle.putInt("list",  RadioChannel.INTERNATIONAL);
			break;
		case R.id.favorite:
			bundle.putInt("list", RadioChannel.FAVORITE);
			break;
		}
		
		Intent intent = new Intent(HelloActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtras(bundle);
		startActivityIfNeeded(intent, -1);
	}

}
