package com.example.steganography;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Result_Extracting extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result__extracting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result__extracting, menu);
		return true;
	}

}