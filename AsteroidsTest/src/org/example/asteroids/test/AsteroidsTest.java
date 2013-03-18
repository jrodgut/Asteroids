package org.example.asteroids.test;

import org.example.asteroids.R;
import org.example.asteroids.activities.About;
import org.example.asteroids.activities.Asteroids;
import org.example.asteroids.activities.Preferences;
import org.example.asteroids.activities.Ranking;

import android.app.Activity;
import android.content.res.Resources;
import android.preference.PreferenceActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;

public class AsteroidsTest extends ActivityInstrumentationTestCase2<Asteroids> {

	protected Solo solo;

	protected Activity activity;
	protected Resources resources;

	@SuppressWarnings("deprecation")
	public AsteroidsTest() {
		super("com.android.example.spinner", Asteroids.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		solo = new Solo(getInstrumentation(), getActivity());

		activity = getActivity();
		resources = activity.getResources();

		// Configure the orientation of the screen
		if (this instanceof AsteroidsTestLandscape) {
			solo.setActivityOrientation(Solo.LANDSCAPE);
		} else {
			solo.setActivityOrientation(Solo.PORTRAIT);
		}
	}

	public void testPreConditions() {

	}

	/*
	 * Tests for checking that the elements are present
	 */

	public void test_loadActivity_newGameButtonPresent() {
		String buttonText = resources.getString(R.string.new_game);
		assertTrue(solo.searchButton(buttonText, true));
	}

	public void test_loadActivity_configButtonPresent() {
		String buttonText = resources.getString(R.string.configuration);
		assertTrue(solo.searchButton(buttonText, true));
	}

	public void test_loadActivity_aboutButtonPresent() {
		String buttonText = resources.getString(R.string.about);
		assertTrue(solo.searchButton(buttonText, true));
	}

	public void test_loadActivity_rankingButtonPresent() {
		String buttonText = resources.getString(R.string.ranking);
		assertTrue(solo.searchButton(buttonText, true));
	}

	/*
	 * Tests for checking the functionality of the elements
	 */

	public void test_onclickAbout_openAboutDialog() {
		String buttonText = resources.getString(R.string.about);
		Button aboutButton = solo.getButton(buttonText, true);
		solo.clickOnView(aboutButton);
		solo.waitForActivity(About.class.getName());
		solo.assertCurrentActivity("About activity not launched", About.class);
	}
	
	public void test_onclickConfiguration_openPreferences() {
		String buttonText = resources.getString(R.string.configuration);
		Button configurationButton = solo.getButton(buttonText, true);
		solo.clickOnView(configurationButton);
		solo.waitForActivity(Preferences.class.getName());
		solo.assertCurrentActivity("Preferences activity not launched", Preferences.class);
	}
	
	public void test_onclickRanking_openRanking() {
		String buttonText = resources.getString(R.string.ranking);
		Button rankingButton = solo.getButton(buttonText, true);
		solo.clickOnView(rankingButton);
		solo.waitForActivity(Ranking.class.getName());
		solo.assertCurrentActivity("Ranking activity not launched", Ranking.class);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
