package A_Bridge_Too_Far;

public interface Physics {
	double MASS=0.1;
	double GRAVITY=0.03;
	double XDRAG=0.001;
	double XPUSHFORCE=0.005;
	
	double getXAcceleration();
	double getYAcceleration();
	void setXAcceleration(double ax);
	void setYAcceleration(double ay);
}
