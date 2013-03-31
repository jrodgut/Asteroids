package org.example.asteroids.activities;

import org.example.asteroids.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroids extends Activity {

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
