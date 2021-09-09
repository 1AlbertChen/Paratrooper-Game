package A_Bridge_Too_Far;

import acm.graphics.GImage;

public class GI extends Paratrooper{
	private boolean chuteOpen;
	private GImage GI;
	public GI(double x, double y) {
		GI=new GImage("101-MidNoChute.png");
		GI.setSize(80,140);
		this.add(GI,x,y);
	}
	public void left() {
		this.removeAll();
		if(chuteOpen) {
			GI=new GImage("101-Left.png");
			GI.setSize(190,270);
			this.add(GI, 330, -30);
		}
		else {
			GI=new GImage("101-LeftNoChute.png");
			GI.setSize(120,130);
		    this.add(GI, 350, 100);
		}
	}
	public void right() {
		this.removeAll();
		if(chuteOpen) {
			GI=new GImage("101-Right.png");
			GI.setSize(190,270);
			this.add(GI, 330, -30);
		}
		else {
			GI=new GImage("101-RightNoChute.png");
			GI.setSize(100,130);
		    this.add(GI, 350, 100);

		}
	}
	public void middle() {
		this.removeAll();
		if(chuteOpen) {
			GI=new GImage("101-Mid.png");
			GI.setSize(160,270);
			this.add(GI, 330, -30);
		}
		else {
			GI=new GImage("101-MidNoChute.png");
		    GI.setSize(80,140);
		    this.add(GI, 350, 100);
		}
		
	}
	public void openChute() {
		this.chuteOpen=true;
	}
	public void closeChute() {
		this.chuteOpen=false;
	}
	public boolean chuteStatus() {
		return chuteOpen;
	}

}
