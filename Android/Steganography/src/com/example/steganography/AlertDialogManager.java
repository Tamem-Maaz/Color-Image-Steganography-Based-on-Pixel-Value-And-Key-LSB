package com.example.steganography;

import java.security.PublicKey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
  

public class AlertDialogManager {
	    /**
	     * Function to display simple Alert Dialog
	     * @param context - application context
	     * @param title - alert dialog title
	     * @param message - alert message
	     * @param status - success/failure (used to set icon)
	     *               - pass null if you don't want icon
	     * */
	public boolean y=false;
	    @SuppressWarnings("deprecation")
		public void showAlertDialog(Context context, String title, String message,
	            Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	  
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	  
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	  
	        if(status != null)
	            // Setting alert dialog icon
	            //alertDialog.setIcon((status) ? R.drawable.whitebackground : R.drawable.whitebackground);
	  
//	        // Setting OK Button
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.cancel();
	            }
	        });
	  
	        // Showing Alert Message
	        alertDialog.show();
	    }
	    
	    public Boolean showAlertDialogYN(final Context context, String title, String message,
	            Boolean status)  {
	  
	         AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	       
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	  
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	  
	        if(status != null)
	            // Setting alert dialog icon
	           // alertDialog.setIcon((status) ? R.drawable.whitebackground : R.drawable.whitebackground);
	  
	        // Setting OK Button
	        alertDialog.setCancelable(false);
	        
	        alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				//	SessionManager session = new SessionManager(context.getApplicationContext());
					
					y=true;
				}
			});
	        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					y=false;
					
					Log.e(null, "cancellllllllllll"+y);
					//dialog.cancel();
					
				}
			});
	  
	        // Showing Alert Message
	        
	        
	      
	        AlertDialog alert =alertDialog.create();
	        alert.show();
	        
	        return y;
	    }
	}


