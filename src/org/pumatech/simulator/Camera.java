package org.pumatech.simulator;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.List;

import org.pumatech.physics.Body;
import org.pumatech.physics.Vec2;

public class Camera {
	// The distance the tracked entity is allowed to get away before camera moves to keep it in frame
	public static final int X_DIST_TOLERANCE = 100;
	public static final int Y_DIST_TOLERANCE = 100;

	// The body the camera is tracking
	private Body tracking;
	// The current position of the camera (The center of the camera view)
	private Vec2 pos;
	
	// Initialize tracking body and start camera with tracked entity in the center
	public Camera(Body tracking) {
		this.tracking = tracking;
		pos = tracking.centerPoint();
	}
	
	public void activate(Graphics2D g) {
		g.translate(-pos.x + 200, -pos.y + 200);
	}
	
	public void deactivate(Graphics2D g) {
		g.translate(pos.x - 200, pos.y - 200);
	}
	
	// Draw drawables using pos as the center of the screen
	public void draw(Graphics2D g, List<? extends Drawable> drawables) {
		double x = -pos.x + 200;
		double y = -pos.y + 200;
		g.translate(x, y);
		for (Drawable d : drawables) {
			d.draw(g);
		}
		tracking.draw(g);
		g.translate(-x, -y);
	}
	
	public Vec2 getCoordinate(Vec2 mouseCoord) {
		return new Vec2(mouseCoord.x + pos.x - 200, mouseCoord.y + pos.y - 200);
	}
	
	public Vec2 getCoordinate(MouseEvent e) {
		return new Vec2(e.getX() + pos.x - 200, e.getY() + pos.y - 200);
	}
	
	// Update the position of the camera to keep the tracked entity within the X and Y tolerances
	public void update(double dt) {
		Vec2 centerPoint = tracking.centerPoint();
		if (centerPoint.x > pos.x + X_DIST_TOLERANCE)
			pos.x = centerPoint.x - X_DIST_TOLERANCE;
		if (centerPoint.x < pos.x - X_DIST_TOLERANCE)
			pos.x = centerPoint.x + X_DIST_TOLERANCE;
		if (centerPoint.y > pos.y + Y_DIST_TOLERANCE)
			pos.y = centerPoint.y - Y_DIST_TOLERANCE;
		if (centerPoint.y < pos.y - Y_DIST_TOLERANCE)
			pos.y = centerPoint.y + Y_DIST_TOLERANCE;
	}
}