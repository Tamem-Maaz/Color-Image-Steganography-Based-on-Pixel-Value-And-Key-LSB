package com.example.steganography;

import javax.crypto.spec.OAEPParameterSpec;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class Authentication extends Activity {

	
	SessionManager session;
	AlertDialogManager alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
		
		session = new SessionManager(getApplicationContext());
		
	
		MyListners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_authentication, menu);
		return true;
	}

	
	private void MyListners()
	{
		CheckBox ch =(CheckBox) findViewById(R.id.checkBox1);
		final Button b1 =(Button) findViewById(R.id.button1);
		final EditText username = (EditText) findViewById(R.id.UserName);
		final EditText password = (EditText) findViewById(R.id.Password);
		

		
		
		
		ch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					b1.setEnabled(true);
				else
					b1.setEnabled(false);
				
			}
		});
	b1.setOnClickListener(new OnClickListener() {
		Toast toast = null;
		@Override
		public void onClick(View v) {
			alert = new AlertDialogManager();
			if(username.getText().toString().isEmpty() && password.getText().toString().isEmpty())
			{
				toast = Toast.makeText(Authentication.this, "Please enter an username and password..", 5000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			// check if there is an user 
			else if(session.isSessionSet())
			{
				session.Login();
				if(username.getText().toString().equals(session.getName()) && password.getText().toString().equals(session.getPass()))
				{
					startActivity(new Intent(Authentication.this, Options.class));
					
			
				}
				else
				{
					
					toast = Toast.makeText(Authentication.this, "Login Faild.. username or passowrd incorrect", 5000);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
			else
			{
				// login new user 
				session.createLoginSession(username.getText().toString(), password.getText().toString());
				//stert next activity
				startActivity(new Intent(Authentication.this,Options.class));
				
				
			}
			
		}
		
		
	});
		
	}
	  @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			this.finish();
		}
}
