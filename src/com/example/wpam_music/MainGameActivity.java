package com.example.wpam_music;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.logic.Music;
import com.example.logic.Song;
import com.example.logic.Utils;

public class MainGameActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

	ArrayList<Song> songs;
	
	int index;
	MediaPlayer mp;
	Utils utils;
	Music music;
	private SeekBar songProgressBar;
	private Handler mHandler = new Handler();
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private MediaRecorder mRecorder = null;
    private static String mFileName = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		Context mContext = this.getApplicationContext();
		
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
	    songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songProgressBar.setOnSeekBarChangeListener(this); // Important
		index = 0;
		utils = new Utils();
		onCreateDialog(savedInstanceState).show();
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.choose_category).setItems(R.array.categories,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						chooseCategory(which);

					}
				});
		return builder.create();
	}

	private void chooseCategory(int which) {
		// TODO Auto-generated method stub
		Log.i("MainGameActivity", "Which" + which);
		
		songs = initializeSongs(which);
	}

	private ArrayList<Song> initializeSongs(int which) {
		
		ArrayList<Song> songlist = new ArrayList<Song>();
		//Song song = new Song();
		switch (which) {
		
		case 0:
			
			for (String item : getResources().getStringArray(R.array.Songs0)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< getResources().getStringArray(R.array.SongsAuthor0).length; i++) {
				songlist.get(i).author = getResources().getStringArray(R.array.SongsAuthor0)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.path0).length; i++) {
				songlist.get(i).path = getResources().getStringArray(R.array.path0)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.pathkaraoke0).length; i++) {
				songlist.get(i).pathWithoutWords = getResources().getStringArray(R.array.pathkaraoke0)[i];
			}
			break;
		case 1:
			
			for (String item : getResources().getStringArray(R.array.Songs1)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< getResources().getStringArray(R.array.SongsAuthor1).length; i++) {
				songlist.get(i).author = getResources().getStringArray(R.array.SongsAuthor1)[i];
			
			}
			for (int i=0; i< getResources().getStringArray(R.array.path0).length; i++) {
				songlist.get(i).path = getResources().getStringArray(R.array.path0)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.pathkaraoke1).length; i++) {
				songlist.get(i).pathWithoutWords = getResources().getStringArray(R.array.pathkaraoke1)[i];
			}
			break;
		case 2:
			for (String item : getResources().getStringArray(R.array.Songs2)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< getResources().getStringArray(R.array.SongsAuthor2).length; i++) {
				songlist.get(i).author = getResources().getStringArray(R.array.SongsAuthor2)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.path0).length; i++) {
				songlist.get(i).path = getResources().getStringArray(R.array.path0)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.pathkaraoke2).length; i++) {
				songlist.get(i).pathWithoutWords = getResources().getStringArray(R.array.pathkaraoke2)[i];
			}
			break;
		case 3:
			for (String item : getResources().getStringArray(R.array.Songs3)) {
				Song song = new Song();
				song.title = item;
				songlist.add(song);
			}
			for (int i=0; i< getResources().getStringArray(R.array.SongsAuthor3).length; i++) {
				songlist.get(i).author = getResources().getStringArray(R.array.SongsAuthor3)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.path0).length; i++) {
				songlist.get(i).path = getResources().getStringArray(R.array.path0)[i];
			}
			for (int i=0; i< getResources().getStringArray(R.array.pathkaraoke3).length; i++) {
				songlist.get(i).pathWithoutWords = getResources().getStringArray(R.array.pathkaraoke3)[i];
			}
			break;
		}
		return songlist;
	}

	/** Called startButton the user clicks the highScores button */
	public void startPressed(View view) {
		mp = new MediaPlayer();
		music = new Music(mp);
		music.play(songs.get(1).pathWithoutWords);
		startRecording();
		// Intent intent = new Intent(this, HighScoresActivity.class);
		// startActivity(intent);
		songProgressBar.setProgress(0);
		songProgressBar.setMax(100);
		updateProgressBar();
	}
	
	public void updateProgressBar() {
	mHandler.postDelayed(mUpdateTimeTask, 100);
	}
		
	private Runnable mUpdateTimeTask = new Runnable() {
			public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();
			songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
			songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
			int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
			songProgressBar.setProgress(progress);
			mHandler.postDelayed(this, 100);
			}
			};


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
		mp.seekTo(currentPosition);
		updateProgressBar();
	}
        
        private void startRecording() {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setOutputFile(mFileName + index);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("Recorder", "prepare() failed");
            }

            mRecorder.start();
        }

        private void stopRecording() {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    
	@Override
    public void onDestroy(){
    super.onDestroy();
       music.stop();
    }
}
