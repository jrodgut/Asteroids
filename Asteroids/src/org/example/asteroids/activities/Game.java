package org.example.asteroids.activities;

import org.example.asteroids.R;
import org.example.asteroids.views.PlayingField;

import android.app.Activity;
import android.os.Bundle;

public class Game extends Activity {

	private PlayingField playingField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		playingField = (PlayingField) findViewById(R.id.playing_field);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		playingField.registerSensors(this);
		playingField.getThread().resumeThread();
	}

	@Override
	protected void onPause() {
		super.onPause();
		playingField.unregisterSensors(playingField);
		playingField.getThread().pauseThread();
	}

	@Override
	protected void onDestroy() {
		playingField.getThread().stopThread();
		super.onDestroy();
	}
}
