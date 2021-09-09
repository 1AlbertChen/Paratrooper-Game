package A_Bridge_Too_Far;

import acm.graphics.GCompound;

public abstract class FlyingObject extends GCompound{
	private double vx;
	private double vy;
	
	public double getXVelocity() {
		return this.vx;
	}
	public double getYVelocity() {
		return this.vy;
	}
	public void setXVelocity(double vx) {
		this.vx=vx;
	}
	public void setYVelocity(double vy) {
		this.vy=vy;
	}
	public abstract void stop();
	public abstract void move();
	

}
