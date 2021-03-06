package org.example.asteroids.activities;

import org.example.asteroids.R;
import org.example.asteroids.model.RankingPosition;
import org.example.asteroids.services.MemoryRankingService;
import org.example.asteroids.services.RankingService;
import org.example.asteroids.views.PlayingField;

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
	
	private final int GAME_RETURN_CODE = 1234;
	
	private static RankingService rankingService;
	
	public Asteroids(){
		if (rankingService == null) {
			rankingService = new MemoryRankingService();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Assign function for about button
		Button gameButton = (Button) findViewById(R.id.new_game_button);
		gameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Asteroids.this, Game.class);
				startActivityForResult(intent, GAME_RETURN_CODE);
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
				launchRanking();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == GAME_RETURN_CODE & resultCode==RESULT_OK & data!=null) {
	       int score = data.getExtras().getInt(PlayingField.SCORE_PARAM);
	       String name = "Me";
	       
	       RankingPosition rankingPosition = new RankingPosition(name, score);
	       rankingService.saveRankingPosition(rankingPosition);
	       launchRanking();
	    }
	}

	private void launchAboutActivity() {
		startActivity(new Intent(this, About.class));
	}

	private void launchConfigActivity() {
		startActivity(new Intent(this, Preferences.class));
	}
	
	private void launchRanking(){
		startActivity(new Intent(Asteroids.this, Ranking.class));
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
