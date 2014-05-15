package com.example.logic;

import java.io.FileInputStream;
import java.io.IOException;

import android.media.MediaPlayer;

public class Music  {
	MediaPlayer mediaplayer;
	
	
	public Music(MediaPlayer mp){
		this.mediaplayer=mp;
	}
	public void play(String path){
		try {
			mediaplayer.reset();
			FileInputStream fis = new FileInputStream(path);
			mediaplayer.setDataSource(fis.getFD());
			mediaplayer.prepare();
			mediaplayer.start();
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e)
		{e.printStackTrace();}
	}
	//public void onPrepared(MediaPlayer player) {
	//    player.start();
	//}

	
	public void stop(){
		mediaplayer.stop();
		//mediaplayer.release();
	}
}
