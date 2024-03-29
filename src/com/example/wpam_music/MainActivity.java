package com.example.wpam_music;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the highScores button */
	public void highScores(View view) {
		
		Intent intent = new Intent(this, HighScoresActivity.class);
		
		startActivity(intent);
	}
	
	/** Called when the user clicks the settings button */
	public void settings(View view) {
		Intent intent = new Intent(this, SettingsAcivity.class);
		
		startActivity(intent);

	}
	
	
	/** Called when the user clicks the newGame button */
	public void newGame(View view) {
		Intent intent = new Intent(this, MainGameActivity.class);
		
		startActivity(intent);
	}
	
}
