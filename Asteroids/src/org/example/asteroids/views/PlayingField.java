package org.example.asteroids.views;

import java.util.ArrayList;
import java.util.List;

import org.example.asteroids.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PlayingField extends View implements SensorEventListener {

	/**
	 * List of Asteroid Graphics
	 */
	private ArrayList<Graphic> asteroids;

	/**
	 * Initial number of Asteroids
	 */
	private int numAsteroids = 5;

	/**
	 * Number of fragments created after collision
	 */
	private int numFragments = 3;

	private Graphic craft;

	private int craftSpin;

	private float craftAcceleration;

	private static final int CRAFT_SPIN_STEP = 5;

	private static final float CRAFT_ACCELERATION_STEP = 0.5f;

	private Graphic missile;
	private static int MISSILE_SPEED_STEP = 12;
	private boolean activeMissile = false;
	private int missileTime;

	private GameThread thread = new GameThread();

	/**
	 * Execution period in milliseconds
	 */
	private static int UPDATE_PERIOD = 50;

	/**
	 * Time of the last execution of the process
	 */
	private long lastExecution = 0;

	/**
	 * Last X position of the event
	 */
	private float lastX = 0;

	/**
	 * Last Y position of the event
	 */
	private float lastY = 0;

	private boolean shoot = false;

	private Float initialSensorValue;

	private class GameThread extends Thread {
		@Override
		public void run() {
			while (true) {
				updatePhysics();
			}
		}
	}

	public PlayingField(Context context, AttributeSet attrs) {

		super(context, attrs);

		// Spacecraft
		Drawable craftDrawable = context.getResources().getDrawable(
				R.drawable.craft);

		craft = new Graphic(this, craftDrawable);

		// Missile
		Drawable missileDrawable = context.getResources().getDrawable(
				R.drawable.missile1);

		missile = new Graphic(this, missileDrawable);

		// Asteroids
		asteroids = new ArrayList<Graphic>();

		Drawable asteroidDrawable = context.getResources().getDrawable(
				R.drawable.asteroid1);

		for (int i = 0; i < numAsteroids; i++) {

			double incX = Math.random() * 4 - 2;
			double incY = Math.random() * 4 - 2;
			int angle = (int) (Math.random() * 360);
			int rotationSpeed = (int) (Math.random() * 8 - 4);
			Graphic asteroid = new Graphic(this, asteroidDrawable, incX, incY,
					angle, rotationSpeed);

			asteroids.add(asteroid);

		}

		// Register the sensor for movement control
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> listSensors = sensorManager
				.getSensorList(Sensor.TYPE_ORIENTATION);
		if (!listSensors.isEmpty()) {
			Sensor orientationSensor = listSensors.get(0);
			sensorManager.registerListener(this, orientationSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	@Override
	protected void onSizeChanged(int width, int height, int prevWidth,
			int prevHeight) {

		super.onSizeChanged(width, height, prevWidth, prevHeight);

		// Now we know the width and height
		// Place craft in the center of the screen
		craft.setPosX((width / 2) - (craft.getWidth() / 2));
		craft.setPosY((height / 2) - (craft.getHeight() / 2));

		for (Graphic asteroid : asteroids) {
			// make sure asteroids are far enough from craft at the start
			do {
				asteroid.setPosX(Math.random() * (width - asteroid.getWidth()));
				asteroid.setPosY(Math.random()
						* (height - asteroid.getHeight()));
			} while (asteroid.getDistance(craft) < (width + height) / 5);
		}

		lastExecution = System.currentTimeMillis();
		thread.start();

	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		craft.draw(canvas);

		for (Graphic asteroide : asteroids) {
			asteroide.draw(canvas);
		}
		
		if(activeMissile){
			missile.draw(canvas);
		}
	}

	protected synchronized void updatePhysics() {
		long now = System.currentTimeMillis();
		// The update is only done when the time has passed
		if (lastExecution + UPDATE_PERIOD > now) {
			return;
		}

		// Calculate delay for real-time performance
		double delay = (now - lastExecution) / UPDATE_PERIOD;
		// update the value of last execution with the current one
		lastExecution = now;

		// Update speed and direction of the craft from
		// the spin and acceleration of it
		craft.setAngle((int) (craft.getAngle() + craftSpin * delay));
		double nIncX = craft.getIncX() + craftAcceleration
				* Math.cos(Math.toRadians(craft.getAngle())) * delay;
		double nIncY = craft.getIncY() + craftAcceleration
				* Math.sin(Math.toRadians(craft.getAngle())) * delay;

		// Update if the module of speed is not bigger than the maximum
		if (Math.hypot(nIncX, nIncY) <= Graphic.MAX_SPEED) {
			craft.setIncX(nIncX);
			craft.setIncY(nIncY);
		}

		// Update positions
		craft.incrementPosition(delay);
		for (Graphic asteroid : asteroids) {
			asteroid.incrementPosition(delay);
		}

		// Update missile position
		if (activeMissile) {
			missile.incrementPosition(delay);
			missileTime -= delay;
			if (missileTime < 0) {
				activeMissile = false;
			} else {
				for (Graphic asteroid : asteroids)
					if (missile.verifyCollision(asteroid)) {
						destroyAsteroid(asteroid);
						break;
					}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			shoot = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x - lastX);
			float dy = Math.abs(y - lastY);

			final float POSITION_THRESHOLD = 6;
			final float SPIN_FACTOR = 2;
			final float ACCELERATION_FACTOR = 25;

			if (dy < POSITION_THRESHOLD && dx > POSITION_THRESHOLD) {
				// horizontal movement -> turn left/right
				craftSpin = Math.round((x - lastX) / SPIN_FACTOR);
				shoot = false;
			} else if (dx < POSITION_THRESHOLD && dy > POSITION_THRESHOLD) {
				// vertical movement -> accelerate
				if (lastY > y) {
					// deceleration is not possible
					craftAcceleration = Math.round((lastY - y)
							/ ACCELERATION_FACTOR);
				}
				shoot = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			craftSpin = 0;
			craftAcceleration = 0;
			if (shoot) {
				activeMissile();
			}
			break;
		}
		lastX = x;
		lastY = y;
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float value = event.values[1];
		if (initialSensorValue == null) {
			initialSensorValue = value;
		}
		final int SENSOR_FACTOR = 3;
		craftSpin = (int) (value - initialSensorValue) / SENSOR_FACTOR;
	}
	
	private void destroyAsteroid(Graphic asteroid) {
	       asteroids.remove(asteroid);
	       activeMissile = false;
	}
	 
	private void activeMissile() {
	       missile.setPosX(craft.getPosX()+ craft.getWidth()/2 - missile.getWidth()/2);
	       missile.setPosY(craft.getPosY()+ craft.getHeight()/2 - missile.getHeight()/2);
	       missile.setAngle(craft.getAngle());
	       missile.setIncX(Math.cos(Math.toRadians(missile.getAngle())) *
	                        MISSILE_SPEED_STEP);
	       missile.setIncY(Math.sin(Math.toRadians(missile.getAngle())) *
	    		   MISSILE_SPEED_STEP);
	       missileTime = (int) Math.min(this.getWidth() / Math.abs( missile.
	          getIncX()), this.getHeight() / Math.abs(missile.getIncY())) - 2;
	       activeMissile = true;
	}
}
