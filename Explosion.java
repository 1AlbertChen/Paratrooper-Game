package A_Bridge_Too_Far;
import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

public class Explosion extends GCompound {
    public static final int LOOPS = 0;
    public static final int REMOVES_ITSELF_WHEN_COMPLETE = 1;
    
    int i = 0;
    private String frameDir;
    private GImage frames[];
    private int frameDelays[];
    private boolean framesLoaded;
    private int mode;
    
    private Timer timer;
    private int currentFrame;
    private int millisecondsToNextFrame;
    private static double movex;
    private static double movey;
    private static double dx;
    private static double dy;
    
    public static void setMove(double x, double y, double mx, double my) {
        movex = x;
        movey = y;
        dx = mx;
        dy = my;
    }
    
    public void loadFrames() {
        File dir = new File(frameDir);              
        File[] directoryListing = dir.listFiles();    
        if (directoryListing != null) {            
            frameDelays = new int[directoryListing.length]; 
            frames = new GImage[directoryListing.length];  
            for (int i = 0; i < directoryListing.length; i++) {
                File file = directoryListing[i];
                int frameNum = Integer.parseInt(file.getName().split("_")[1]);
                frameDelays[frameNum] = (int) (100.0 * Double.parseDouble(
                                 file.getName().split("-")[1].split("s")[0]));
                frames[frameNum] = new GImage(frameDir + "//" + file.getName());
            }
            framesLoaded = true;
        }
    }
    
    public boolean framesLoaded() {
        return framesLoaded;
    }
    
    public Explosion(String frameDir, int mode) {
        this.frameDir = frameDir;
        this.mode = mode;
        
        if (! framesLoaded) {
            loadFrames();
        }
        
        timer = new Timer(10,new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if ((millisecondsToNextFrame == 0)  
                 && (currentFrame < frames.length - 1)) {
                    millisecondsToNextFrame = frameDelays[currentFrame];
                    currentFrame++;
                    Explosion.this.removeAll();
                    frames[currentFrame].setLocation(
                                      -frames[currentFrame].getWidth()/2+movex, 
                                      -frames[currentFrame].getHeight()/2+movey);
                    movex = movex + dx;
                    movey = movey + dy;
                    Explosion.this.add(frames[currentFrame]);
                    i++;
                }
                else if (currentFrame < frames.length - 1) {
                    millisecondsToNextFrame--;
                }
                else {
                    switch (mode) {
                    case LOOPS : 
                        currentFrame = 0;
                        break;
                    case REMOVES_ITSELF_WHEN_COMPLETE :
                        Explosion.this.timer.stop();
                        break;
                    }
                }
            }});
        timer.start();
        
    }

}



