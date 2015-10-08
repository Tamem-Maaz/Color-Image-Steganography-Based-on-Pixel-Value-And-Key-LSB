package com.example.steganography;
import java.util.HashMap;

import com.example.steganography.R.string;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.util.Log;
import android.widget.EditText;
public class SessionManager {

	
	// Shared Preferences
    SharedPreferences prefLogin;
    SharedPreferences prefValues;
     
    // Editor for Shared preferences
    SharedPreferences.Editor editorLogin;
    SharedPreferences.Editor editorValues;
     
    // Context
    Context _context;
    
 // Shared pref mode
    int PRIVATE_MODE = 0;
    
    // Sharedpref file name
    private static final String PREF_NAMEl = "AndroidHivePrefl";
    private static final String PREF_NAMEv = "AndroidHivePrefv";
     
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_SESSION_SET = "IsSessionSet";
    
    // User name (make variable public to access from outside)
    public static final String IMAGE="image";
    public static final String KEY_NAME="name";
    public static final String KEY_PASS="pass";
    public static final String ValuesSet="valuesset";
    public static final String VALUE="value";
    
    

    public SessionManager(Context context){
    	this._context=context;
    	
        prefLogin = _context.getSharedPreferences(PREF_NAMEl, PRIVATE_MODE);
        prefValues = _context.getSharedPreferences(PREF_NAMEv, PRIVATE_MODE);
        editorLogin = prefLogin.edit();
        editorValues = prefValues.edit();
    }
    
    /**
     * Create login session
     * */
    public void createLoginSession(String name, String pass){
        // Storing login value as TRUE
        editorLogin.putBoolean(IS_LOGIN, true);
        editorLogin.putBoolean(IS_SESSION_SET, true);
         
        // Storing name in pref
        editorLogin.putString(KEY_NAME, name);
         
        // Storing pass in pref
        editorLogin.putString(KEY_PASS, pass);
         
        // commit changes
        editorLogin.commit();
    }
    public void setImage(String im)
    {
    	editorValues.putString(IMAGE, im);
    	editorValues.commit();
    }
    public String getImage()
    {
    	return prefValues.getString(IMAGE,null);
    }
    public void clearImage()
    {
    	editorValues.putString(IMAGE, null);
    	editorValues.commit();
    }
    
    
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, prefLogin.getString(KEY_NAME, null));
         
        // user email id
        user.put(KEY_PASS, prefLogin.getString(KEY_PASS, null));
         
        // return user
        return user;
    }
    
   
    public String getName()
    {
    	return prefLogin.getString(KEY_NAME,null);
    }
    public String getPass()
    {
    	return prefLogin.getString(KEY_PASS, null);
    }
    
    // Get Login State
    public boolean isLoggedIn(){
        return prefLogin.getBoolean(IS_LOGIN, false);
        
    }
    public void Login()
    {
    	editorLogin.putBoolean(IS_LOGIN, true);
    	editorLogin.commit();
    }
    public boolean isValuesSet()
    {
    	return prefValues.getBoolean(ValuesSet, false);
    }
    public boolean isSessionSet()
    {
    	return prefLogin.getBoolean(IS_SESSION_SET, false);
    }
    //    public boolean isSessionSet()
//    {
//    	HashMap<String, String> user;
//    
//    	
//    	//String b = pref.getString(KEY_NAME, null);
//    	try {
//    		 user = this.getUserDetails();
//		} catch (Exception e) {
//			return false;
//		}
//    	
//    	//AlertDialogManager la = new AlertDialogManager();
//    	//la.showAlertDialog(_context, "dd", "kk", false);
//    	String name = user.get(this.KEY_NAME);
//    	String pass = user.get(this.KEY_PASS);
//   	   	
//   	if(name == null && pass == null)
//    		return false;
//   	else if(name.isEmpty() || pass.isEmpty())
//		return false;
//   else
//   		return true;
//    
//    }
    
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void setValue(int v)
    {
    	
    	editorValues.putInt(VALUE,v);
    	editorValues.putBoolean(ValuesSet, true);
    	editorValues.commit();
    	
    

    	
    }
     
 
    public boolean checkValue(int br,int r,int bl,int g)
    {
    	//check if main values equal the stored values
    		
    		int v =  prefValues.getInt(VALUE,0);
        	if( v == (br+r+bl+g))
        		return true;
	
    	
    	return false;
    		
    	
    		
    }
    public void checkLogin(){
        // Check login status
       // if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Authentication.class);
            // Staring Login Activity
          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
       // }
        
//        else
//        {
//        	 Intent i = new Intent(_context,Options.class);
//             // Closing all the Activities
//             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//              
//             // Add new Flag to start new Activity
//             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//              
//             // Staring Login Activity
//             _context.startActivity(i);
//        }
        	
         
    }
    
    
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
//        editor.clear();
//        editor.commit();
    	editorLogin.putBoolean(IS_LOGIN, false);
    	clearImage();
    	editorLogin.commit();
    	//editor.remove(IS_LOGIN);
         
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Authentication.class);
      i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
   
    public void ClearUserData()
    {
      //Clearing all data from Shared Preferences
      editorLogin.clear();
      editorLogin.commit();
    
     // editorValues.clear();
      //editorValues.commit();
    	 Intent i = new Intent(_context,Authentication.class);
         // Closing all the Activities
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          
         // Add new Flag to start new Activity
       //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          
         // Staring Activity
        // _context.startActivity(i);
    }
    public void ClearValues()
    {
    	  editorValues.clear();
          editorValues.commit();
    }

   
}
