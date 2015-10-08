package com.example.steganography;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
	
	private static int RESULT_LOAD_IMAGE = 1;
	public int num_Brightness=50;
	public int num_Red=50;
	public int num_Green=50;
	public int num_Blue=50;
	public String temp_Text = "";
	public int startProgress = 50;
	public ImageView imv_show;
	public SessionManager session;
	public AlertDialogManager alert;
	Toast toast =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView txt_Title = (TextView) findViewById(R.id.txt_title);
		
		//create my aleart dialog
		alert = new AlertDialogManager();
		//create user session
		session = new SessionManager(getApplicationContext());
		session.ClearUserData();
		session.ClearValues();
		
		txt_Title.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				//check if user loged in or not
				
				if(session.isValuesSet())//check if there are values
				{
					if(session.checkValue(num_Brightness, num_Red, num_Blue, num_Green))  // check if the values are correct
						session.checkLogin(); // go to check pass and username
					else
					{
						toast = Toast.makeText(Main.this, "Error Values", 5000);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				}
				else 
				{
					//if new user then set session values
				 	

			 	if(num_Brightness != 50 || num_Red != 50 || num_Blue !=50 || num_Green !=50)
			 		ShowAlert();

					
				}
				return false;
			}
		});

		 final SeekBar skb_Brightness;
		 
		
		 final TextView txt_Brightness;
		 
		skb_Brightness = (SeekBar) findViewById(R.id.skb_brightness);
		
		imv_show = (ImageView) findViewById(R.id.imv_show);
		txt_Brightness = (TextView) findViewById(R.id.txt_Brightness);		
		
		final Spinner spinner = (Spinner) findViewById(R.id.sprChoose);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				 int index_text = spinner.getSelectedItemPosition();
				 if(index_text == 0) 
				 {temp_Text = "Brightness"; txt_Brightness.setText("Brightness" + ": " + num_Brightness ); skb_Brightness.setProgress(num_Brightness);}
				 else if(index_text == 1) 
				 {temp_Text = "Red"; txt_Brightness.setText("Red" + ": " + num_Red );  skb_Brightness.setProgress(num_Red);}
				// skb_Brightness.setBackgroundColor(Color.RED);}
				 else if(index_text == 2) 
				 {temp_Text = "Green"; txt_Brightness.setText("Green" + ": " + num_Green );  skb_Brightness.setProgress(num_Green);}
				// skb_Brightness.setBackgroundColor(Color.GREEN);}
				 else if(index_text == 3) 
				 {temp_Text = "Blue"; txt_Brightness.setText("Blue" + ": " + num_Blue );  skb_Brightness.setProgress(num_Blue);}
				 //skb_Brightness.setBackgroundColor(Color.BLUE);}
				 skb_Brightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						
						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {}
						
						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {}
						
						@Override
						public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
							//Log.v("seekBri", String.valueOf(progress));
							int index = spinner.getSelectedItemPosition();
							
							if(index == 0){
								num_Brightness = progress;
								imv_show.setColorFilter(new PorterDuffColorFilter(Color.argb(progress, num_Red, num_Green, num_Blue), Mode.SRC_OVER));
								txt_Brightness.setText("Brightness" + ": " + String.valueOf(progress) );
							}
							else if(index == 1){
								num_Red = progress;
								imv_show.setColorFilter(new PorterDuffColorFilter(Color.argb(num_Brightness, progress, num_Green, num_Blue), Mode.SRC_OVER));
								txt_Brightness.setText("Red " + ": " + String.valueOf(progress) );
							}
							else if(index == 2){
								num_Green = progress;
								imv_show.setColorFilter(new PorterDuffColorFilter(Color.argb(num_Brightness, num_Red, progress, num_Blue), Mode.SRC_OVER));
								txt_Brightness.setText("Green" + ": " + String.valueOf(progress) );
							}
							else if(index == 3){
								num_Blue = progress;
								imv_show.setColorFilter(new PorterDuffColorFilter(Color.argb(num_Brightness, num_Green, num_Green, progress), Mode.SRC_OVER));
								txt_Brightness.setText("Blue" + ": " + String.valueOf(progress) );
							}
						}
					});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		
	    
	    });
		 
		 
		Button bt = (Button) findViewById(R.id.btnOpenImage);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 num_Brightness = num_Red = num_Green = num_Blue = startProgress;
				 skb_Brightness.setProgress(startProgress);
				 txt_Brightness.setText(temp_Text + ":" + startProgress);
				 Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	             startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
			
		});
		
		Intent receivedIntent = getIntent();
		String receivedAction = receivedIntent.getAction();
		String receivedType = receivedIntent.getType();
		if(receivedAction.equals(Intent.ACTION_SEND)){
			if(receivedType.startsWith("image/"))
				imv_show.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
		}
		else if(receivedAction.equals(Intent.ACTION_MAIN)){
			//app has been launched directly, not from share list
		}
	}
	
	public void ShowAlert()
	{
 		AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main.this);
        alertDialog.setTitle( "Be Careful");
        final int value = num_Blue+num_Brightness+num_Green+num_Red;
        alertDialog.setMessage("You must remember this value where it is the sum, of your values  "+ value  +" if you want to change it press cancel");
 		
        
        alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				session.setValue(value);
				session.checkLogin();
				
			}
		});
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
			}
		});
        AlertDialog al = alertDialog.create();
        al.show();
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
	           ImageView imageView = (ImageView) findViewById(R.id.imv_show);
	           imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	       }
	    }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
		
//	 private Bitmap convertColorIntoBlackAndWhiteImage(Bitmap orginalBitmap) {
//	        ColorMatrix colorMatrix = new ColorMatrix();
//	        colorMatrix.setSaturation(0);
//
//	        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
//	                colorMatrix);
//
//	        Bitmap blackAndWhiteBitmap = orginalBitmap.copy(
//	                Bitmap.Config.ARGB_8888, true);
//
//	        Paint paint = new Paint();
//	        paint.setColorFilter(colorMatrixFilter);
//
//	        Canvas canvas = new Canvas(blackAndWhiteBitmap);
//	        canvas.drawBitmap(blackAndWhiteBitmap, 0, 0, paint);
//
//	        return blackAndWhiteBitmap;
//	 }

}