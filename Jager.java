package A_Bridge_Too_Far;

import acm.graphics.GImage;

public class Jager extends Paratrooper{
	private GImage jager;
	public Jager(double x, double y) {
		jager=new GImage("jerry.png");
		jager.setSize(75,135);
		this.add(jager, x, y);
	}
}
