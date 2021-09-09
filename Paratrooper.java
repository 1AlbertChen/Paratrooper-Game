package A_Bridge_Too_Far;

import acm.graphics.GImage;

public abstract class Paratrooper extends FlyingObject implements Physics{
	private boolean isKIA;
	private double ax;
	private double ay;
	
	public double getXAcceleration() {
		return this.ax;
	}
	public double getYAcceleration() {
		return this.ay;
	}
	public void setXAcceleration(double ax) {
		this.ax=ax;
	}
	public void setYAcceleration(double ay) {
		this.ay=ay;
	}
	public double leftSide() {
		return this.getX();
	}
	public double rightSide() {
		return this.getX()+this.getWidth();
	}

	public double topSide() {
		return this.getY();
	}

	public double bottomSide() {
		return this.getY()+this.getHeight();
	}
	public void move() {
		double vx=this.getXVelocity();
		double vy=this.getYVelocity();
		this.setLocation(this.getX()+vx, this.getY()+vy);
		this.setXVelocity(vx+this.getXAcceleration());
		this.setYVelocity(vy+this.getYAcceleration());
	}
	public void kill() {
		this.isKIA=true;
	}
	
	public void stop() {
		this.setXAcceleration(0);
		this.setYAcceleration(0);
		super.setXVelocity(0);
		super.setYVelocity(0);
	}
}
