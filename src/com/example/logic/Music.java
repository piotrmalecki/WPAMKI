package com.example.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.wpam_music.R;

public class Music  {
	MediaPlayer mediaplayer;
	FileInputStream fis ;
	
	
	public Music(MediaPlayer mp){
		this.mediaplayer=mp;
	}
	public void play(String path){
		try {
			fis = new FileInputStream(path);
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
	
public static ArrayList<Song> initializeSongs(Context res, int which) {
		
		ArrayList<Song> songlist = new ArrayList<Song>();
		//Song song = new Song();
		
		switch (which) {
		
		case 0:
			
			for (String item : res.getResources().getStringArray(R.array.Songs0)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.SongsAuthor0).length; i++) {
				songlist.get(i).author = res.getResources().getStringArray(R.array.SongsAuthor0)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.path0).length; i++) {
				songlist.get(i).path = res.getResources().getStringArray(R.array.path0)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.pathkaraoke0).length; i++) {
				songlist.get(i).pathWithoutWords = res.getResources().getStringArray(R.array.pathkaraoke0)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.totalDuration0).length; i++) {
				songlist.get(i).totalDuration = res.getResources().getStringArray(R.array.totalDuration0)[i];
			}
			break;
		case 1:
			
			for (String item : res.getResources().getStringArray(R.array.Songs1)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.SongsAuthor1).length; i++) {
				songlist.get(i).author = res.getResources().getStringArray(R.array.SongsAuthor1)[i];
			
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.path1).length; i++) {
				songlist.get(i).path = res.getResources().getStringArray(R.array.path1)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.pathkaraoke1).length; i++) {
				songlist.get(i).pathWithoutWords = res.getResources().getStringArray(R.array.pathkaraoke1)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.totalDuration1).length; i++) {
				songlist.get(i).totalDuration = res.getResources().getStringArray(R.array.totalDuration1)[i];
			}
			break;
		case 2:
			for (String item : res.getResources().getStringArray(R.array.Songs2)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.SongsAuthor2).length; i++) {
				songlist.get(i).author = res.getResources().getStringArray(R.array.SongsAuthor2)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.path2).length; i++) {
				songlist.get(i).path = res.getResources().getStringArray(R.array.path2)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.pathkaraoke2).length; i++) {
				songlist.get(i).pathWithoutWords = res.getResources().getStringArray(R.array.pathkaraoke2)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.totalDuration2).length; i++) {
				songlist.get(i).totalDuration = res.getResources().getStringArray(R.array.totalDuration2)[i];
			}
			break;
		case 3:
			for (String item : res.getResources().getStringArray(R.array.Songs3)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.SongsAuthor3).length; i++) {
				songlist.get(i).author = res.getResources().getStringArray(R.array.SongsAuthor3)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.path3).length; i++) {
				songlist.get(i).path = res.getResources().getStringArray(R.array.path3)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.pathkaraoke3).length; i++) {
				songlist.get(i).pathWithoutWords = res.getResources().getStringArray(R.array.pathkaraoke3)[i];
			}
			for (int i=0; i< res.getResources().getStringArray(R.array.totalDuration3).length; i++) {
				songlist.get(i).totalDuration = res.getResources().getStringArray(R.array.totalDuration3)[i];
			}
			break;
		}
		return songlist;
	}
}
