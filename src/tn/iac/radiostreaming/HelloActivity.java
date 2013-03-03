package tn.iac.radiostreaming;

import tn.iac.radiostreaming.db.RadioChannel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelloActivity extends Activity implements OnClickListener {

	Button national, international, favorite, about;
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
		
		about = (Button) findViewById(R.id.about);
		about.setOnClickListener(this);
		
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
		Intent intent = new Intent(HelloActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
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
		case R.id.about:
			intent = new Intent(HelloActivity.this, AboutActivity.class);
			break;	
		}
		
		intent.putExtras(bundle);
		startActivityIfNeeded(intent, -1);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
        {
            case R.id.menu_about:
                Intent intent = new Intent(HelloActivity.this, AboutActivity.class);
                startActivityIfNeeded(intent, -1);
                return true;
            default:
            	return super.onOptionsItemSelected(item);
        }
	}

}
