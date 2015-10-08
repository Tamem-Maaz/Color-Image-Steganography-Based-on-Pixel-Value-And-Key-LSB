package com.example.steganography;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
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
import android.widget.TextView;
import android.widget.Toast;

public class Embedding extends Activity {
	private SessionManager session;
	private AlertDialogManager alert;
	private boolean pvd,lsb = false;
	Toast toast;
	private static int RESULT_LOAD_IMAGE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_embedding);
		session = new SessionManager(getApplicationContext());
		if(session.getImage() != null)
		{
			ImageView imageView = (ImageView) findViewById(R.id.imgBefor);
	        imageView.setImageBitmap(BitmapFactory.decodeFile(session.getImage()));
	        Button btnHide = (Button) findViewById(R.id.hideText);
	        btnHide.setEnabled(true);
		}
		setImage();
		AlgoChooser();
		openImage();
		hideText();
		saveImage();
		
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
					pvd = true;
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
		  ImageView befor = (ImageView)findViewById(R.id.imgBefor);
		  Button openImage = (Button) findViewById(R.id.openImageEmb);
		  openImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	             startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
//			Intent receivedIntent = getIntent();
//			String receivedAction = receivedIntent.getAction();
//			String receivedType = receivedIntent.getType();
//			if(receivedAction.equals(Intent.ACTION_SEND)){
//				if(receivedType.startsWith("image/"))
//					befor.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
//			}
//			else if(receivedAction.equals(Intent.ACTION_MAIN)){
//				//app has been launched directly, not from share list
//			}
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
		           session.setImage(picturePath);
		           cursor.close();
		           ImageView imageView = (ImageView) findViewById(R.id.imgBefor);
		           
		           imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		           
		           Button btnHide = (Button) findViewById(R.id.hideText);
		           btnHide.setEnabled(true);
		       }
		    }
	  
	  protected void saveImage()
	  {
		  final Button saveImage = (Button)findViewById(R.id.saveImage);
		  final ImageView imv_After = (ImageView)findViewById(R.id.imgAfter);
		  saveImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable d = imv_After.getDrawable();
				Bitmap myBitmap = ((BitmapDrawable) d).getBitmap();
				
				String root = Environment.getExternalStorageDirectory().toString();
				
				File myDir = new File(root + "/StegoImage");
				myDir.mkdirs();
				Random generator = new Random();
				int n = 10000;
				n = generator.nextInt(n);
				String fname = "Test-" + n + ".png";
				File file = new File(myDir, fname);
				if(file.exists()) file.delete();
				
				try{
					FileOutputStream out = new FileOutputStream(file);
					myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					
					out.flush();
					out.close();				
				}
				catch(Exception e){
					e.printStackTrace();
				}
				saveImage.setEnabled(false);
				toast = Toast.makeText(Embedding.this, "Save is Done !!", 5000);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});
	  }
	  protected void hideText()
	  {
		  final Button saveImage = (Button)findViewById(R.id.saveImage);
		  Button hideText = (Button) findViewById(R.id.hideText);
		  final EditText myText = (EditText) findViewById(R.id.myText);
		  final ImageView imv_Befor = (ImageView)findViewById(R.id.imgBefor);
		  final ImageView imv_After = (ImageView)findViewById(R.id.imgAfter);
		  if(myText.getText().equals(null))
		  {
			toast = Toast.makeText(Embedding.this, "Your text is empty", 5000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		  }
		  else{
			  hideText.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						StegoPVD pvd = new PVDColor();
						Drawable d = imv_Befor.getDrawable();
						Bitmap myBitmap = ((BitmapDrawable) d).getBitmap();
						Object obj = pvd.stego(myBitmap, myText.getText().toString(), true);
						Bitmap newBitmap = (Bitmap) obj;
						imv_After.setImageBitmap(newBitmap);
						saveImage.setEnabled(true);
					}
				});
			  TextView txtResult = (TextView) findViewById(R.id.textView1);
			  txtResult.setVisibility(TextView.INVISIBLE);
		  }
//		  else
//		  {
//			  if(pvd)
//				  //pvd call
//			  else if(lsb)
//				  //lsb call
//		  }
	  }
	  protected void setImage()
	  {
		  ImageView befor = (ImageView) findViewById(R.id.imgBefor);
		  //ImageView im = (ImageView) findViewById(R.id.imv_show);
		//befor.setImageResource(R.id.imv_show);
		//  befor.setImageDrawable(im.getDrawable());
		  //befor.getDrawable()
		 // befor.set(R.id.imv_show);
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
			startActivity(new Intent(Embedding.this, ChangePassword.class));
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
