package com.example.steganography;

import AlgoStego.PVDColor;
import AlgoStego.StegoPVD;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Extracting extends Activity {
	private SessionManager session;
	private static int RESULT_LOAD_IMAGE = 1;
	private boolean pvd,lsb =false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extracting);
		session = new SessionManager(getApplicationContext());
		AlgoChooser();
		openImage();
		showText();
	}
	
  protected void AlgoChooser()
  {
	  
	  final Spinner s = (Spinner) findViewById(R.id.chooseAlgo);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Algo_Array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		s.setAdapter(adapter);
	  
	  
	  
	  
	  s.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			 int index = s.getSelectedItemPosition();
				if(index == 0)
					pvd=true;
				if(index ==1)
					lsb=true;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	
	  
	  });

  }
  protected void openImage()
  {
	  ImageView image = (ImageView)findViewById(R.id.image);
	  Button openImage = (Button) findViewById(R.id.openImage);
	  openImage.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             startActivityForResult(i, RESULT_LOAD_IMAGE);
             
		}
	});
//		Intent receivedIntent = getIntent();
//		String receivedAction = receivedIntent.getAction();
//		String receivedType = receivedIntent.getType();
//		if(receivedAction.equals(Intent.ACTION_SEND)){
//			if(receivedType.startsWith("image/"))
//				image.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
//		}
//		else if(receivedAction.equals(Intent.ACTION_MAIN)){
//			//app has been launched directly, not from share list
//		}
  }
	@Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	       super.onActivityResult(requestCode, resultCode, data);
	       if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	    	   Uri selectedImage = data.getData();
	           String[] filePathColumn = { MediaStore.Images.Media.DATA };
	           Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	           cursor.moveToFirst();
	           int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	           String picturePath = cursor.getString(columnIndex);
	           cursor.close();
	           ImageView imageView = (ImageView) findViewById(R.id.image);
	           imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	           Button btnShowText = (Button) findViewById(R.id.showText);
	           btnShowText.setEnabled(true);
	       }
	    }
  protected void showText()
  {
	  final EditText text = (EditText) findViewById(R.id.text);
	  final ImageView imv_show = (ImageView) findViewById(R.id.image);
	  Button btnShowText = (Button) findViewById(R.id.showText);
	  btnShowText.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			  StegoPVD pvd = new PVDColor();
			  Drawable d = imv_show.getDrawable();
			  Bitmap StegoBitmap = ((BitmapDrawable) d).getBitmap();
			  
			  Object obj = pvd.stego(StegoBitmap, "", false);
			  
			  
			  if(obj != null){
				  String secretStego = (String) obj;
				  StringBuilder theLast = new StringBuilder();
			      int[] strChar = new int[8];
			      for (int i = 0; i < secretStego.length(); )
			      {
			          strChar = new int[8];
			          for (int j = 0; j < 8; j++)
			          {
			              if(i < secretStego.length())
			                  strChar[j] = Integer.parseInt(String.valueOf(secretStego.charAt(i++)));
			          }
			          
			          int b = 0;
			          int bin = 1;
			          for (int k= strChar.length-1; k >= 0; k--){
			              b+= strChar[k] * bin;
			              bin = bin * 2;
			          }
			          theLast.append(String.valueOf((char)b));
			      }
			      text.setText(theLast.toString());
			  }
			  else
				  text.setText("No Stego In This Image !!");
		}
	});
	  
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
		startActivity(new Intent(Extracting.this, ChangePassword.class));
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
