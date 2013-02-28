package tn.iac.radiostreaming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private static final int SPLASH_TIME = 3 * 1000;// 3 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(SplashActivity.this,
						HelloActivity.class);
				startActivity(intent);

				SplashActivity.this.finish();

				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			}
		}, SPLASH_TIME);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, SPLASH_TIME);

	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}
}
