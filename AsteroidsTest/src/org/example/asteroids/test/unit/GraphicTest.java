package org.example.asteroids.test.unit;

import static org.mockito.Mockito.mock;

import org.example.asteroids.activities.Game;
import org.example.asteroids.views.Graphic;

import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

public class GraphicTest extends ActivityInstrumentationTestCase2<Game> {

	private Graphic g1;

	private Graphic g2;

	@SuppressWarnings("deprecation")
	public GraphicTest() {
		super("org.example.asteroids.activities", Game.class);
	}

	@Override
	protected void setUp() throws Exception {
		// Graphic 1 at (1,2)
		int x0 = 1;
		int y0 = 2;

		View v1 = mock(View.class);
		Drawable d1 = mock(Drawable.class);

		g1 = new Graphic(v1, d1);

		g1.setPosX(x0);
		g1.setPosY(y0);

		// Graphic 2 at (4. 6)
		int x1 = 4;
		int y1 = 6;

		View v2 = mock(View.class);
		Drawable d2 = mock(Drawable.class);

		g2 = new Graphic(v2, d2);

		g2.setPosX(x1);
		g2.setPosY(y1);
	}

	public void test_getDistance_return5() {
		double distance = g2.getDistance(g1);
		assertTrue(distance == 5);
	}

	public void test_getDistanceReverse_return5() {
		double distance = g1.getDistance(g2);
		assertTrue(distance == 5);
	}

	public void test_getSelfDistance_return0() {
		double distance = g1.getDistance(g1);
		assertTrue(distance == 0);
	}

	public void test_getSelfDistance2_return0() {
		double distance = g2.getDistance(g1);
		assertTrue(distance == 0);
	}
}
