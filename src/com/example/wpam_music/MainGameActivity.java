package com.example.wpam_music;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track;
import com.echonest.api.v4.TrackAnalysis;
import com.example.echoprint.AudioFingerprinter.AudioFingerprinterListener;
import com.example.logic.Music;
import com.example.logic.Song;
import com.example.logic.Utils;

public class MainGameActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener, OnCompletionListener,
		DialogInterface.OnCancelListener {

	private static final String API_KEY = "GUYHX8VIYSCI79EZI";

	ArrayList<Song> songs;

	int index;
	int totalDuration;
	MediaPlayer mp;
	Utils utils;
	Music music;
	AudioFingerprinterListener listener;
	private SeekBar songProgressBar;
	private Handler mHandler = new Handler();
	private TextView songCurrentDurationLabel;
	private TextView songTotalDurationLabel;
	private TextView SongInfo;
	private EchoNestAPI echoNest;
	private ProgressDialog dialog;
	private MediaRecorder mRecorder = null;
	private static String mFileName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		Context mContext = this.getApplicationContext();
		mp = new MediaPlayer();
		music = new Music(mp);
		index = 0;
		echoNest = new EchoNestAPI(API_KEY);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		SongInfo = (TextView) findViewById(R.id.SongInfo);
		songProgressBar.setOnSeekBarChangeListener(this); // Important

		mp.setOnCompletionListener(this);
		utils = new Utils();
		onCreateDialog(savedInstanceState).show();
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.choose_category).setItems(R.array.categories,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						chooseCategory(which);

					}
				});
		return builder.create();
	}

	private void chooseCategory(int which) {
		// TODO Auto-generated method stub
		Log.i("MainGameActivity", "Which" + which);

		songs = Music.initializeSongs(MainGameActivity.this, which);
		SongInfo.setText(songs.get(index).title + "\n"
				+ songs.get(index).author);
		SongInfo.setTypeface(null, Typeface.BOLD_ITALIC);
		SongInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	}

	/** Called startButton the user clicks the highScores button */
	public void startPressed(View view) {
		/*
		 * final ProgressDialog dialog2 = new
		 * ProgressDialog(MainGameActivity.this); dialog2.setCancelable(true);
		 * new Thread(new Runnable(){ public void run(){ try{
		 * Thread.sleep(27000); dialog2.show(MainGameActivity.this,
		 * "Analyzing...", "Please wait...", true); Thread.sleep(3000); } catch
		 * (Exception e){ e.printStackTrace(); } dialog2.dismiss(); }
		 * }).start();
		 */
		music.play(songs.get(index).pathWithoutWords);
		startRecording();
		songProgressBar.setProgress(0);
		songProgressBar.setMax(100);
		updateProgressBar();
	}

	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			int totalDuration = Integer
					.parseInt(songs.get(0).totalDuration.substring(1));
			if (index < songs.size()) {
				 totalDuration = Integer
						.parseInt(songs.get(index).totalDuration.substring(1));
				//Log.i("DURATION", Integer.toString(mp.getDuration()));
			}
			long currentDuration = mp.getCurrentPosition();
			songTotalDurationLabel.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			songCurrentDurationLabel.setText(""
					+ utils.milliSecondsToTimer(currentDuration));
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
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
		totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);
		mp.seekTo(currentPosition);
		updateProgressBar();
	}

	/*
	 * private void onRecord(boolean start) { if (start) { startRecording(); }
	 * else { stopRecording(); } }
	 */

	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
		mRecorder.setOutputFile(songs.get(index).path);
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
	public void onStop() {
		super.onStop();
		music.stop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		index = 0;
		music.stop();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {

		Log.i("TUTAJ", "ON COMPETITION");
		stopRecording();
		TrackAnalysis a = null;
		String string = null;
		File file = new File(songs.get(index).path);
		
		updateMetadata(new File(songs.get(index).pathWithoutWords),file);
		 
		final Track track;
		try {
			track = echoNest.uploadTrack(file, true);
			/*
			 * final ProgressDialog dialog2 =
			 * ProgressDialog.show(MainGameActivity.this, "Analyzing...",
			 * "Please wait...", true); dialog2.setCancelable(true); new
			 * Thread(new Runnable(){ public void run(){ try{
			 * Thread.sleep(3000);
			 * 
			 * } catch (Exception e){ e.printStackTrace(); } dialog2.dismiss();
			 * } }).start();
			 */
			track.waitForAnalysis(30000);
			a = track.getAnalysis();
			string = a.getTempo().toString() + track.getArtistName()
					+ track.getTitle();
			Log.i("TUTAJ", a.getTempo().toString() + track.getArtistName()
					+ track.getTitle());
		} catch (EchoNestException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// komunikacja z API
		Toast.makeText(MainGameActivity.this, "Success\n" + string,
				Toast.LENGTH_LONG).show();
		// CustomDialog cdd = new CustomDialog(MainGameActivity.this, string);
		// cdd.show();
		index++;
		if (index == songs.size()) {
			Intent intent = new Intent(this, MainActivity.class);

			startActivity(intent);
		} else {
			// reslult increase
			SongInfo.setText(songs.get(index).title + "\n"
					+ songs.get(index).author);
			SongInfo.setTypeface(null, Typeface.BOLD_ITALIC);
			SongInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			totalDuration = 0;
			// mp.reset();
		}
	}

	private void updateMetadata(File src, File dst) {
		MusicMetadataSet src_set = null;
		try {
			src_set = new MyID3().read(src);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MusicMetadata meta = new MusicMetadata("name");
        meta.setSongTitle(songs.get(index).title);
        meta.setArtist(songs.get(index).author);
        try {
			new MyID3().write(src, dst, src_set, meta);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ID3WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		dialog.dismiss();
	}
}
