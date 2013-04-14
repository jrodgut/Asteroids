package org.example.asteroids.drawables;

import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

public class Asteroid{
	public static ShapeDrawable getVectorDrawable() {
		Path path = new Path();
		path.moveTo((float) 0.3, (float) 0.0);
		path.lineTo((float) 0.6, (float) 0.0);
		path.lineTo((float) 0.6, (float) 0.3);
		path.lineTo((float) 0.8, (float) 0.2);
		path.lineTo((float) 1.0, (float) 0.4);
		path.lineTo((float) 0.8, (float) 0.6);
		path.lineTo((float) 0.9, (float) 0.9);
		path.lineTo((float) 0.8, (float) 1.0);
		path.lineTo((float) 0.4, (float) 1.0);
		path.lineTo((float) 0.0, (float) 0.6);
		path.lineTo((float) 0.0, (float) 0.2);
		path.lineTo((float) 0.3, (float) 0.0);
		PathShape pathShape = new PathShape(path, 1, 1);
		ShapeDrawable shape = new ShapeDrawable(pathShape);
		shape.getPaint().setColor(Color.WHITE);
		shape.getPaint().setStyle(Style.STROKE);
		shape.setIntrinsicWidth(50);
		shape.setIntrinsicHeight(50);
		return shape;
	}
}
