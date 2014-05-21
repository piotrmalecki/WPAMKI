package com.example.wpam_music;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

public class CustomDialog extends Dialog {


	public Activity currentActivity;
	  public Dialog dialog;
	 public String info;
	  
	  public CustomDialog(Activity activity, String string) {
		super(activity);
		    this.currentActivity = activity;
		    this.info = string;
		// TODO Auto-generated constructor stub
	}
	  
	  protected Dialog onCreateDialog(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    //setContentView(R.layout.custom_dialog);
	    
	    AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
	    builder.setMessage(info);
	    return builder.create();
	  }
}
