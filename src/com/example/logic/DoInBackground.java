package com.example.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;

class DoInBackground extends AsyncTask<Void, Void, Void> implements
		DialogInterface.OnCancelListener {
	private ProgressDialog dialog;
	private Context context;
	private String filepath;
	String url = "http://yourserver";
	File file;
	//HttpResponse response;

	public DoInBackground(Context cont, String filepath) {
		this.context = cont;
		this.filepath = filepath;
		file = new File(Environment.getExternalStorageDirectory(), "filepath");
	}

	protected void onPreExecute() {
		dialog = ProgressDialog.show(context, "", "Loading. Please wait...",
				true);
	}

	protected Void doInBackground(Void... unused) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		InputStreamEntity reqEntity;
		try {
			reqEntity = new InputStreamEntity(new FileInputStream(file), -1);

			reqEntity.setContentType("binary/octet-stream");
			reqEntity.setChunked(true); // Send in multiple parts if needed
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(Void unused) {
		dialog.dismiss();
		
		// parsowanie odpowiedzi
	}

	public void onCancel(DialogInterface dialog) {
		cancel(true);
		dialog.dismiss();
	}
}