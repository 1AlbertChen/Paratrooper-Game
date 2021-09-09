package A_Bridge_Too_Far;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

public class Main extends GraphicsProgram{
	JButton jump;
	JButton open;
	JTextArea instructions;
	Sky sky;
	GLabel warning;
	GLabel gameOver;
	GLabel executed;
	Timer phase1;
	Timer phase2;
	Timer phase3;
	Timer phase4;
	Timer phase5;
	GI gi;
	Explosion explosion;
	int killCount;
	
	SpawnPlane c47;
	private static int counter1=0;
	private double flakIntensity=0.02;
	private double terminalV;
	
	double explodeX;
	double explodeY;
	int count = 0;
	Random random = new Random();
	Jager jager = new Jager(random.nextDouble()*500.0 + 100.0, 800);
	
	
	public void init() {
		
		this.setSize(800,950);
		jump=new JButton("Jump");
		this.add(jump,SOUTH);
		open=new JButton("Open Parachute");
		this.add(open,SOUTH);
		warning=new GLabel("Warning! Return to combat area or you will be executed!",80,200);
		warning.setFont("Arial-BOLD-25");
		warning.setColor(Color.RED);
		gameOver=new GLabel("You are Killed in Action!",300,200);
		gameOver.setFont("Arial-BOLD-25");
		gameOver.setColor(Color.RED);
		executed=new GLabel("You are executed for desertion!",200,200);
		executed.setFont("Arial-BOLD-25");
		executed.setColor(Color.RED);
		instructions=new JTextArea("Jump out of the plane by pressing the 'Jump' button.");
		instructions.setFont(instructions.getFont().deriveFont(18f)); 
		instructions.setSize(600, 100);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		this.add(instructions, NORTH);
		this.addKeyListeners(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case 65: sky.setGoingDown(0); 
							 gi.left();break;
					case 68: sky.setGoingDown(2);
							 gi.right();break;
					case 83: sky.setGoingDown(1);
							 gi.middle();break;
				}
			}
			public void keyReleased(KeyEvent e) {	
			}
			public void keyTyped(KeyEvent e) {
			
			}
		});
		sky=new Sky(0,0,3);
		this.add(sky);
		phase5=new Timer(10,null);
		this.getGCanvas().addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                Explosion.setMove(sky.getXVelocity() - sky.getXAcceleration(),
                        sky.getYVelocity() - sky.getYAcceleration(),
                        sky.getXVelocity(),
                        sky.getYVelocity());
                explosion = new Explosion("explosion_frames", 
                           Explosion.REMOVES_ITSELF_WHEN_COMPLETE);
                
                if (jager.contains(e.getX(), e.getY())){
                explosion.setLocation(e.getX(),e.getY());
                Main.this.add(explosion); 
                jager.removeAll();
                killCount++;
                if (!phase5.isRunning()) {
                	jager = new Jager(random.nextDouble()*500.0 + 100.0, 800);
                    Main.this.add(jager);
                }
                }
            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            });
		
	}
	public void run() {
		phase1();
	}
	
	public void phase1() {
		c47=new SpawnPlane(230, 100);
		this.add(c47);
		phase1= new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sky.setXVelocity(-c47.getXVelocity());
				sky.setYVelocity(0);
				sky.move();
				Main.counter1++;
				if (counter1==500) {
					phase1.stop();
					phase2();
				}
			}
		});
		phase1.start();
		jump.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (phase1.isRunning()) {
					phase1.stop();
					phase2();
				}
			}
		});
	}
	
	public void phase2() {
		gi=new GI(350,100);
		sky.setXVelocity(-c47.getXVelocity());
		gi.setYAcceleration(Physics.GRAVITY);
		this.add(gi);
		phase2=new Timer(10,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sky.move();
				c47.move();
				gi.move();
				if (c47.getX()>500)
					Main.this.remove(c47);
				if (gi.getY()>100) {
					terminalV=gi.getYVelocity();
					Main.this.remove(c47);
					gi.stop();
					phase3();
					phase2.stop();
				}
			}
		});
		phase2.start();
	}
	public void phase3() {
		sky.setYVelocity(-terminalV);
		sky.setGoingDown(1);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sky.getY()<-2000) {
					gi.openChute();
					Main.this.getGCanvas().requestFocus();
					
				}
			}
			
		});
		phase3=new Timer(10,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sky.move();
				instructions.setText("Horizontal Speed="+ Math.floor( -sky.getXVelocity()* 100) / 100+" \nVertical Speed= "+Math.floor( -sky.getYVelocity()* 100) / 100 + " \nKills: "+killCount);
				
				if (jager.getBounds().intersects(gi.getBounds())){
                    sky.stop();
                    gi.kill();
                    jager.stop();
                    phase3.stop();
                    Main.this.add(gameOver);
                }
				
				count++;
				if (count == 40)
                Main.this.add(jager);                  
                if (jager.getY() < -800) 
                    jager.setLocation(random.nextDouble()*500.0 + 100.0, 800);
                jager.setYVelocity(sky.getYVelocity());
                jager.setXVelocity(sky.getXVelocity() - sky.getXAcceleration());
                jager.move();

                //random explosion
                if (random.nextDouble() < flakIntensity) {
                    Explosion.setMove(sky.getXVelocity() - sky.getXAcceleration(),
                            sky.getYVelocity() - sky.getYAcceleration(),
                            sky.getXVelocity() + 1,
                            sky.getYVelocity() + 1);
                    explosion = new Explosion("explosion_frames", 
                            Explosion.REMOVES_ITSELF_WHEN_COMPLETE);
                    
                    double rand = random.nextDouble();
                    explodeX = random.nextDouble()*800;
                    explodeY = random.nextDouble() * 950;
                    if (((explodeX < gi.getX() - 100) || (explodeX > gi.getX() + 100))) {
                        explosion.setLocation(explodeX,explodeY);
                        Main.this.add(explosion);
                    }
              
                }
				if (sky.getX()>2500||sky.getX()<-2500) {
					Main.this.add(warning);
				}
				else {
					Main.this.remove(warning);
				}
				if (sky.getX()>3000||sky.getX()<-3000) {
					sky.stop();
					gi.kill();
					phase3.stop();
					Main.this.remove(warning);
					Main.this.add(executed);
				}
				if (sky.getY()<-4500) {
					instructions.setText("Horizontal Speed="+ Math.floor(-sky.getXVelocity()* 100) / 100+ " \n Vertical Speed=" + Math.floor( -sky.getYVelocity()* 100) / 100 +"\nPress 'Open Parachute' to release your parachute."  + " \nKills: "+killCount);
				}
				if (gi.chuteStatus()==true) {
					phase3.stop();
					phase4();
				}
				if (sky.getY()<-6000) {
					phase3.stop();
					phase4();
				}
			}
		});
		phase3.start();
		this.getGCanvas().requestFocus();
	}
	public void phase4() {
		gi.openChute();
		sky.openChute();
		gi.middle();
		this.remove(jager);
		phase4=new Timer(10,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sky.move();
				instructions.setText("Horizontal Speed="+ Math.floor(-sky.getXVelocity()* 100) / 100+"\nVertical Speed= "+Math.floor( -sky.getYVelocity()* 100) / 100 + " \nKills: "+killCount);
				if (jager.getBounds().intersects(gi.getBounds())){
	                   sky.stop();
	                   gi.kill();
	                   gi.stop();
	                   jager.stop();
	                   phase3.stop();
	                   Main.this.add(gameOver);
	               }
				if (jager.getY() < -800)
					jager.removeAll();
                jager.setLocation(jager.getX() + jager.getXVelocity(),jager.getY() - jager.getYVelocity() - 1);
				if (sky.getX()>3500||sky.getX()<-3500) {
					Main.this.add(warning);
				}
				else {
					Main.this.remove(warning);
				}
				if (sky.getX()>4000||sky.getX()<-4000) {
					sky.stop();
					gi.stop();
					gi.kill();
					phase4.stop();
					Main.this.remove(warning);
					Main.this.add(executed);
				}
				if (sky.getY()<-6500) {
					phase4.stop();
					gi.setYVelocity(-sky.getYVelocity());
					phase5();
				}
			}
			
		});
		phase4.start();
	}
	
	public void phase5() {
		this.remove(jager);
		phase5=new Timer(10,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    instructions.setText("Horizontal Speed="+ Math.floor(-sky.getXVelocity()* 100) / 100+"\nVertical Speed= "+Math.floor( -sky.getYVelocity()* 100) / 100 + " \nKills: "+killCount);
				sky.setYVelocity(0);
				sky.setYAcceleration(0);
				sky.move();
				gi.move();
				if (gi.getY()>630) {
					phase5.stop();
					gi.closeChute();
					gi.middle();
					instructions.setText("By the time you landed, the Germans had already demolished the bridge. Well, as you know, I've always thought that we tried to go a bridge too far.");
				}
			}
			
		});
		phase5.start();
	}
}
