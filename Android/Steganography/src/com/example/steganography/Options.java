package com.example.steganography;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class Options extends Activity {
	public AlertDialogManager al;
	public SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		session = new SessionManager(getApplicationContext());
		al = new AlertDialogManager();
		MyListner();
	
		
		
	}
	
	public void MyListner()
	{
		final Button change;
		final Button emb;
		final Button ext;
		final RadioGroup emb_ext;
		final Button go;
				
		
		emb_ext = (RadioGroup)findViewById(R.id.radioGroup1);
		go = (Button) findViewById(R.id.Go);
		
		go.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				int emb_ext_Id = emb_ext.indexOfChild(emb_ext.findViewById(emb_ext.getCheckedRadioButtonId()));
				//al.showAlertDialog(Options.this, "ddd", " == "+embId, null);
				
				if(emb_ext_Id==0)
				{
					startActivity(new Intent(Options.this, Embedding.class));
				}
				else if(emb_ext_Id==1)
				{
					startActivity(new Intent(Options.this, Extracting.class));
				}
			}
			
		});
//		
//	    change = (Button)findViewById(R.id.ChangeUsPa);
//		change.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//SessionManager s = new SessionManager(getApplicationContext());
//				//s.ClearUserData();
//				startActivity(new Intent(Options.this, ChangePassword.class));
//				
//			}
//		});
//		
//		 ext = (Button)findViewById(R.id.Extracting);
//		//Animation anm = AnimationUtils.loadAnimation(Options.this, R.anim.animation);
//		//ext.startAnimation(anm);
//		ext.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(Options.this, Extracting.class));
//				
//			}
//		});
//		
//		emb = (Button)findViewById(R.id.Embedding);
//		//emb.startAnimation(anm);
//		emb.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(Options.this, Embedding.class));
//				
//			}
//		});
	}
	
	
	
	  @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater in = getMenuInflater();
		in.inflate(R.menu.menuoptions, menu);

		return true;
	}
	  @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.LogOut)
		{
			session.logoutUser();
		}
		else if(item.getItemId() == R.id.LogoutAndExit)
		{
			//LogOutAndExitProgram
			session.logoutUser();
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.addCategory(Intent.CATEGORY_HOME);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}
		else if(item.getItemId() == R.id.ChPass)
		{
			startActivity(new Intent(Options.this, ChangePassword.class));
		}
		return super.onOptionsItemSelected(item);
	}
	  @Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			this.finish();
		}
}
