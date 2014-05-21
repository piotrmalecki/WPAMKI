package com.example.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpEntityConverter {

	public String message;
	public String artist;
	public String title;
	public String dancebility;
	public String id;
	public long bitrate;

	public final static String MESSAGE = "message";
	public final static String ARTIST = "artist";
	public final static String TITLE = "title";
	public final static String ID = "id";
	public final static String BITRATE = "bitrate";


	public HttpEntityConverter(HttpEntity entity) {
		String result = "";
		if (entity != null) {
			JSONObject jobj = null;
			try {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				// now you have the string representation of the HTML request
				instream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				jobj = new JSONObject(result);

				if (jobj.has("result")) {
					JSONObject jsonresult = jobj.getJSONObject("result");
					JSONObject status;

					status = jobj.getJSONObject("status");

					JSONObject track = jobj.getJSONObject("track");

					if (status.has(MESSAGE))
						message = status.getString(MESSAGE);
					else
						message = "FAILED";
					if (track.has(ARTIST))
						artist = track.getString(ARTIST);
					if (track.has(TITLE))
						title = track.getString(TITLE);
					if (track.has(ID))
						id = track.getString(ID);
					
					if (track.has(BITRATE))
						bitrate = track.getLong(BITRATE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
