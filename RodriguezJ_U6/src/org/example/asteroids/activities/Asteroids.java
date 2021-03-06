package org.example.asteroids.activities;

import org.example.asteroids.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroids extends Activity {

	private MediaPlayer mp = null;

	private final String POSITION = "position";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Assign function for about button
		Button gameButton = (Button) findViewById(R.id.new_game_button);
		gameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(new Intent(Asteroids.this, Game.class));
			}

		});

		// Assign function for about button
		Button aboutButton = (Button) findViewById(R.id.about_button);
		aboutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				launchAboutActivity();
			}

		});

		// Assign function for config button
		Button configButton = (Button) findViewById(R.id.config_button);
		configButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				launchConfigActivity();
			}

		});

		// Assign function for config button
		Button rankingButton = (Button) findViewById(R.id.ranking_button);
		rankingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(new Intent(Asteroids.this, Ranking.class));
			}

		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Music
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String musicKey = "music";
		if (pref.getBoolean(musicKey, false)) {
			mp = MediaPlayer.create(this, R.raw.audio);
		} else {
			mp = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mp != null) {
			mp.start();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mp != null) {
			mp.pause();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		if (mp != null) {
			int pos = mp.getCurrentPosition();
			bundle.putInt(POSITION, pos);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		if (bundle != null && mp != null) {
			int pos = bundle.getInt(POSITION);
			mp.seekTo(pos);
		}
	}

	private void launchAboutActivity() {
		startActivity(new Intent(this, About.class));
	}

	private void launchConfigActivity() {
		startActivity(new Intent(this, Preferences.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asteroids, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			launchAboutActivity();
			break;
		case R.id.menu_config:
			launchConfigActivity();
			break;
		}
		return true;
	}

}
