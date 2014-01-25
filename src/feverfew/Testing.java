/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;


/**
 *
 * @author Oscar
 * 
 * The values of certain parameters during testing; classes that implement 
 * Testing should be considered unfinished
 * 
 * When the project is moving on towards completion, create a 'copy' of this
 * interface called Commons. Remove stuff that's been replaced with variable-
 * -values, naturally.
 * 
 */

import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.swing.JFrame;

public interface Testing {
    //TODO properly sort into some kind of grouping schema
    
    // Logging settings
    public static final Path DEFAULT_LOG = 
            Paths.get("C:/Program Files/Feverfew/feverfew.log");
    public static final Flogger LOGGER = new Flogger(DEFAULT_LOG);

    public static final Dimension DIM = new Dimension(800, 600);
    // Remember, width then height, across then down
    public static final int BOARD_WIDTH  = 800;
    public static final int BOARD_HEIGHT = 600;
    // in pixels    
    public static final Color BACKGROUND = Color.DARK_GRAY;
    
    
    public static final int ACTIONCOUNT = 2;
    
    
    public static final String ACTIONLOCATION = "resource/actions.xml"; 
    public static final String ENEMIESLOCATION = "resource/enemies.xml";
    
    public static final int MAXSCREENOBJECTS = 20;
    
    
    public final double PROPORTIONY = 0.7;
    // The proportion of the height of the window to be taken up by the bar
    // Currently, the bar is vertically central and there's no reason that
    // should change.
    public final double PROPORTIONX = 0.1;
    // The proportion of the width of the board to put to the left of the bar    
    public static final int ACTBAR_DURATION = 50;
    // this number does not really have units    
    public static final int ACTBAR_STOPCOUNT = 2;
    // Testing values, not final 
    
    public static final BasicStroke ACTBARSTYLE = new BasicStroke(7, 0, 2);
    public static final Color ACTBARCOLOR = Color.WHITE;
    
    public static final double MAXSCORE = 100;
    
    
    public static final double STOPMARKWIDTH = 15;
    public static final Color STOPMARKCOLOR = Color.RED;
    
    public static final double ACTIONPINOFFSET = 1.5*BOARD_WIDTH/100.0;
    
    
    public static final Color DEFTEXTCOLOR = Color.WHITE;
    
    
    public static final Color SCORECOLOR = Color.WHITE;
    public static final Dimension SCORELOCATION = new Dimension(360, 120);
            //new Dimension((int) 0.6*DIM.width, (int) 0.3*DIM.height);
    
    
    // PRIORITIES
    // larger number => painted later => 'on top'
    public static final int ACTBARPRI = 30;
    public static final int STOPPOINTPRI = 20;
    public static final int ACTPINPRI = 25;
    
    
    
}
