package org.example.asteroids.views;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Graphic {
	/**
	 * Image to draw
	 */
	private Drawable drawable;

	/**
	 * Position
	 */
	private double posX, posY;

	/**
	 * Speed of movement
	 */
	private double incX, incY;

	/**
	 * Angle of rotation
	 */
	private int angle;
	
	/**
	 * Speed of rotation
	 */
	private int rotationSpeed;

	/**
	 * Size
	 */
	private int width, height;

	/**
	 * Radius used as a collision threshold
	 */
	private int collisionRadius;

	/**
	 * Where to draw
	 */
	private View view;

	public static final int MAX_SPEED = 20;

	public Graphic(View view, Drawable drawable) {

		this.view = view;

		this.drawable = drawable;

		width = drawable.getIntrinsicWidth();

		height = drawable.getIntrinsicHeight();

		collisionRadius = (height + width) / 4;

	}
	
	public Graphic(View view, Drawable drawable, double incX, double incY, int angle, int rotationSpeed) {

		this(view, drawable);
		this.incX = incX;
		this.incY = incY;
		this.angle = angle;
		this.rotationSpeed = rotationSpeed;
	}

	public void draw(Canvas canvas) {

		canvas.save();

		int x = (int) (posX + width / 2);

		int y = (int) (posY + height / 2);

		canvas.rotate((float) angle, (float) x, (float) y);

		drawable.setBounds((int) posX, (int) posY, (int) posX + width,
				(int) posY + height);

		drawable.draw(canvas);

		canvas.restore();

		int rInval = (int) Math.hypot(width, height) / 2 + MAX_SPEED;

		view.invalidate(x - rInval, y - rInval, x + rInval, y + rInval);

	}

	public void incrementPosition(double factor) {

		posX += incX * factor;

		// Si salimos de la pantalla, corregimos posición

		if (posX < - width / 2) {
			posX = view.getWidth() - width / 2;
		}

		if (posX > view.getWidth() - width / 2) {
			posX = - width / 2;
		}

		posY += incY * factor;

		if (posY < - height / 2) {
			posY = view.getHeight() - height / 2;
		}

		if (posY > view.getHeight() - height / 2) {
			posY = - height / 2;
		}

		angle += rotationSpeed * factor;

	}

	public double getDistance(Graphic g) {

		return Math.hypot(posX - g.posX, posY - g.posY);

	}

	public boolean verifyCollision(Graphic g) {

		return (getDistance(g) < (collisionRadius + g.collisionRadius));

	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public double getIncX() {
		return incX;
	}

	public void setIncX(double incX) {
		this.incX = incX;
	}

	public double getIncY() {
		return incY;
	}

	public void setIncY(double incY) {
		this.incY = incY;
	}
}
