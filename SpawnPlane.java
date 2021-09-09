package A_Bridge_Too_Far;

import acm.graphics.GImage;

public class SpawnPlane extends FlyingObject{
	private GImage image;
	private static final double FLIGHTSPEED=7;
	public SpawnPlane(double x, double y) {
		image=new GImage("plane.png");
		image.setSize(400,120);
		this.add(image,x,y);
		super.setXVelocity(FLIGHTSPEED);
		super.setYVelocity(0);
	}
	public void move() {
		double vx=super.getXVelocity();
		double vy=super.getYVelocity();
		this.setLocation(this.getX()+vx, this.getY()+vy);
	}
	public void stop() {
		super.setXVelocity(0);
	}
}
