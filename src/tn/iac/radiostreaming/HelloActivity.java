package tn.iac.radiostreaming;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HelloActivity extends Activity implements OnClickListener{
	
	
	
	Button redirect =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello);
		
		redirect=(Button)findViewById(R.id.red);
		redirect.setOnClickListener(this);
		
		Button q =(Button)findViewById(R.id.qt);
        q.setOnClickListener(new View.OnClickListener() {
 
			public void onClick(View v) {
				finish();
 
			}
		});
    }

   
	@Override
	public void onClick(View v) {
		
         	Intent intent=new Intent(HelloActivity.this,MainActivity.class);
			//intent.putExtra("nom",nom);
			//intent.putExtra("date", date_n);
			
			startActivity(intent);
			
			//Set the transition -> method available from Android 2.0 and beyond  
			overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
         }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello, menu);
		return true;
		
	}

}
