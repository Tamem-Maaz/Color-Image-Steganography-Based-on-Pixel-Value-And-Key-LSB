package com.example.steganography;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class ChangePassword extends Activity {

	SessionManager session;
	AlertDialogManager alert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		session = new SessionManager(getApplicationContext());
		
	
		MyListners();
	}
	
	private void MyListners()
	{
		CheckBox ch =(CheckBox) findViewById(R.id.checkBox1);
		final Button b1 =(Button) findViewById(R.id.button1);
		final EditText newPass = (EditText) findViewById(R.id.newPass);
		final EditText oldPassword = (EditText) findViewById(R.id.Password);
		

		
		
		
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
				if(newPass.getText().toString().isEmpty() && oldPassword.getText().toString().isEmpty())
				{
					toast = Toast.makeText(ChangePassword.this, "Please enter an old and new password..", 5000);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				
				else if(oldPassword.getText().toString().equals(session.getPass()))
				{
						session.createLoginSession(session.getName(), newPass.getText().toString());
						startActivity(new Intent(ChangePassword.this,Options.class));
						
				
				}
				else
				{
					toast = Toast.makeText(ChangePassword.this, "oldPassword is incorrect..", 5000);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
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

