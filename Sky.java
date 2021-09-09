package A_Bridge_Too_Far;

import java.awt.Image;

import acm.graphics.GImage;

public class Sky extends FlyingObject implements Physics{
	private int goingDown;
	private static final int LEFTBOUND=-4;
	private static final int RIGHTBOUND=5;
	private static final int HEIGHT=7;
	private double ydrag=0.001;
	private double ax;
	private double ay;
	public Sky(double x, double y, int goingDown) {;
		this.goingDown=goingDown;
		for (int i=LEFTBOUND;i<RIGHTBOUND;i++) {
			for (int j=0;j<HEIGHT;j++) {
				this.add(new GImage("sky.jpg"),i*1000,j*1000);
			}
		}
		for (int i=LEFTBOUND;i<RIGHTBOUND;i++) {
			this.add(new GImage("ground.png"),i*1000,HEIGHT*1000);
		}
		
		this.setLocation(x,y);
	}
	public void setGoingDown(int goingDown) {
		this.goingDown=goingDown;
	}
	
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
	public void stop() {
		this.setXAcceleration(0);
		this.setYAcceleration(0);
		super.setXVelocity(0);
		super.setYVelocity(0);
	}
	 public void move() { 
			// motion of the background when person not swing
		 	if (goingDown == 1) {
		 		this.setXAcceleration((-this.getXVelocity() * XDRAG) / MASS);
	            this.setXVelocity(this.getXVelocity() + this.getXAcceleration());
		 		this.setYAcceleration(((MASS * GRAVITY) + ydrag * this.getYVelocity()) / MASS);
		 		this.setYVelocity(this.getYVelocity() - this.getYAcceleration());
	        }
		 	else if (goingDown==3) {
		 		this.setXAcceleration((-this.getXVelocity() * XDRAG) / MASS);
	            this.setXVelocity(this.getXVelocity() + this.getXAcceleration());
		 		this.setYAcceleration(((MASS * GRAVITY) + ydrag * this.getYVelocity()) / MASS);
		 		this.setYVelocity(this.getYVelocity() - this.getYAcceleration());
		 	}
	        // motion of the background person swing
	        else if (goingDown == 0) {
	            this.setXAcceleration((XPUSHFORCE - this.getXVelocity() * XDRAG) / MASS);
	            this.setXVelocity(this.getXVelocity() + this.getXAcceleration());
	            this.setYAcceleration(((MASS * GRAVITY) + ydrag * this.getYVelocity()) / MASS);
		        this.setYVelocity(this.getYVelocity() - this.getYAcceleration());
	        }
	        else if (goingDown == 2) {
	            this.setXAcceleration((-XPUSHFORCE - this.getXVelocity() * XDRAG) / MASS);
	            this.setXVelocity(this.getXVelocity() + this.getXAcceleration());
	            this.setYAcceleration(((MASS * GRAVITY) + ydrag * this.getYVelocity()) / MASS);
		        this.setYVelocity(this.getYVelocity() - this.getYAcceleration());
	        }
		 	if (!(goingDown==3)) {
		 		if (this.getXVelocity()>1.5)
			 		this.setXVelocity(1.5);
			 	if (this.getXVelocity()<-1.5)
			 		this.setXVelocity(-1.5);
		 	}
		 	
	        this.setLocation(this.getX()+ this.getXVelocity(),this.getY() + this.getYVelocity());
	    }
	 public void openChute() {
		 this.ydrag=0.002;
	 }
}
