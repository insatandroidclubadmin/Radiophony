package tn.iac.radiostreaming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HelloActivity extends Activity implements OnClickListener {

	TextView redirect;
	TextView quit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello);

		redirect = (TextView) findViewById(R.id.red);
		redirect.setOnClickListener(this);

		quit = (TextView) findViewById(R.id.qt);
		quit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(HelloActivity.this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivityIfNeeded(intent, -1);
	}

}
