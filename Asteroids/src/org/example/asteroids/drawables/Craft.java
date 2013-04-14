package org.example.asteroids.drawables;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

public class Craft {
	public static ShapeDrawable getVectorDrawable() {
		Path path = new Path();
		path.moveTo((float) 0, (float) 0);
		path.lineTo((float) 1, (float) 0.5);
		path.lineTo((float) 0, (float) 1);
		path.lineTo((float) 0, (float) 0);
		PathShape pathShape = new PathShape(path, 1, 1);
		ShapeDrawable shape = new ShapeDrawable(pathShape);
		shape.getPaint().setColor(Color.WHITE);
		shape.getPaint().setStyle(Style.STROKE);
		shape.setIntrinsicWidth(20);
		shape.setIntrinsicHeight(15);
		return shape;
	}
}
