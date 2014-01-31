/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

/**
 *
 * @author Oscar
 * 
 * This class represents a stop point on an action bar. It's just a way of
 * keeping track of things; all of the fields could be moved to ActBar, probably
 * 
 */

// TODO figure out how to 'display' the scoring.

import feverfew.Testing;
import java.awt.geom.Rectangle2D;

public class StopPoint extends Scaffold implements Testing {
    
    // TODO fix width to depend on stopcount, removing possible overlap
    
    private final static double DEFWIDTH = 0.1;
    private final static double MINRATIO = 0.2;
    // This scales how the minimum score grows with the difficulty
    private final static double WIDTHRATIO = 0.001;
    private final static double MAXWIDTH   = 0.2;
    // These define the maximum width and how it scales down with increasing
    // difficulty.
    private final static double STDEVMAX = 0.01;
    private final static double STDEV_EXPCO = 110;
    private final static double STDEV_POW = 1.1;
    private final static double STDEV_REDCO  = 0.0000625;
    // Numbers that control the scaling of the gaussian's standard deviation 
    // given values can be tested using the spreadsheet
    
    private Rectangle2D displaybox;
    private double boxlength;        
    private double boxwidth;
   
    public double location;
    // Location on bar as proportion of bar's length
    public double length;
    // The total width of the scoring region, as proportion of bar's length
    public double scoreFlatness;
    // A higher value here gives the gaussians used for calculating score a
    // Larger standard deviation, which makes them flatter 
    public double minscore;
    // The minimum score as a proportion of the maximum score
    // Calculated from other values
    public double lowerBound, upperBound;
    // The proportionate positions of the edges of the stop point's scoring zone
    
    public String type;
    /** Describes the stop point; usual value will be 'norm'
    * Other values:
    * flat: either success or failure, no variation within, case 1000
    * soon: highest value at start of scoring region, case 999
    * late: highest value at end of scoring region, case 1001
    */
    
    public StopPoint(double position, double difficulty){
        // Typical creation of a stop point at a given location with a given
        // level of difficulty.
        // The difficulty will usually be a number between 0 and 99
        // However some other numbers give 'special case' difficulty 
        this.location = position; 
        
        if (difficulty == 1000){
            type = "flat";
            
            this.scoreFlatness = Double.POSITIVE_INFINITY;
            // Can actually construct a flat line using the usual gaussian fn :D
            this.minscore = StopPoint.MAXSCORE;
            this.length = StopPoint.DEFWIDTH;
        }
        
        else if (difficulty == 999){
            // TODO soon difficulty
        }
        else if (difficulty == 1001){
            // TODO late difficulty
        }
        else {
            assert  0  <= difficulty;
            assert  99 >= difficulty;
            // Stop if given a bad difficulty
            // TODO proper error-catching code instead
            this.type = "norm";
            this.length = StopPoint.MAXWIDTH - StopPoint.WIDTHRATIO * difficulty;
            // Linear decrease in width with difficulty
            this.scoreFlatness = this.stdev(difficulty);
            // Square root decrease in std dev with difficulty
            this.minscore = (difficulty/99.0) 
                    * StopPoint.MINRATIO 
                    * StopPoint.MAXSCORE;
            // Linear *increase* in minimum score with difficulty
            // This is because otherwise the width becomes the same as the stdev
        }
        this.lowerBound = (this.location - (this.length/2.0));
        this.upperBound = (this.location + (this.length/2.0));
        
        
        LOGGER.log("Stop point created at " 
                + Double.toString(this.location)
                + ". Set stop point width to " 
                + Double.toString(length)
                + ". Stop point begins at " 
                + Double.toString(lowerBound)
                + ". Stop point ends at " 
                + Double.toString(upperBound));
        
        //TODO display rectangle initialisation
        boxlength = BOARD_HEIGHT * PROPORTIONY * this.length;
        boxwidth = STOPMARKWIDTH;
        
        this.setY((int) (BOARD_HEIGHT * (0.5 + PROPORTIONY * 
                       (this.location - 0.5)) - Math.floor(boxlength/2.0)));
        this.setX((int) (BOARD_WIDTH * PROPORTIONX - Math.floor(boxwidth/2.0)));
        // This formula gives the absolute location of the stop point on the 
        // y-axis. The x-axis location is trivial
        LOGGER.log("Determined absolute location of stop point: "
                + Double.toString(y));
        
        displaybox = new Rectangle2D.Double(0, 0, boxwidth, boxlength);
        
        this.setShape(displaybox);
        this.setFill(true);
        this.setStroke(false);
        this.setFillColor(STOPMARKCOLOR);
    }    
    
    
    /**
     * 
     * @param target 
     * 
     * Unfinished/Nonfunctional at present. No known use.
     */
    public void becomePoint(StopPoint target){

        this.location = target.location;
        this.length = target.length;
        this.scoreFlatness = target.scoreFlatness;
        this.minscore = target.minscore;
    }
    
    public double score(double hitLocation){
        // Returns the score based on the (proportionate) location of the
        // hit location within the stop point.
        double score;
        switch (this.type) {
            case "norm":
                LOGGER.log("Calculating score as gaussian with"
                        + " prefactor: " + Double.toString(StopPoint.MAXSCORE)
                        + ", centre: " + Double.toString(this.location)
                        + ", StdDev: " + Double.toHexString(this.scoreFlatness)
                        + ", location " + Double.toString(hitLocation));
                score = this.gauss(StopPoint.MAXSCORE, this.location,
                        this.scoreFlatness, hitLocation);
                // Normal gaussian scoring
                break;
            case "flat":
                 score = StopPoint.MAXSCORE;
                 // flat scoring could be replaced with a gaussian with infinite
                 // standard deviation
                break;
            case "soon":
                // TODO soon difficulty
                score = -10;
                break;
            case "late":
                score = -10;
                break;
            default:
                score = -20;
                break;
        }
        
        return score;
    }
    
    public final void move(double position){
        // Moves this stop point to the given position
        this.location = position;
    }
    
    public double getLoc(){
        // Returns the location of the stop point
        return this.location;
    }
    
    public double getWid(){
        // Returns the width of the stop point
        return this.length;
    }
    
    public double getMin(){
        // Returns the minimum score of the stop point
        return this.minscore;
    }
    
    public double getSpread(){
        // Returns the standard deviation/score flatness of the stop point
        return this.scoreFlatness;
    }
    
    public Rectangle2D getBox(){
        return this.displaybox;
    }
    
    private double gauss(double height, 
                         double center,
                         double stddev, 
                         double x){
        // returns the value at x of a gaussian function with height, center and
        // standard deviation
        double expon = -1.0*(Math.pow((x - center), 2.0))/(2.0*stddev);
        
        return height*(Math.pow(Math.E, expon));        
    }
    
    private double stdev(double difficulty){
        double exponent = ((99.0 - difficulty)/STDEV_EXPCO);
        double lincomp  = STDEV_REDCO*Math.pow((difficulty + 1.0), STDEV_POW);
        
        return STDEVMAX - lincomp * Math.pow(Math.E, exponent);
    }

    public double getBoxlength() {
        return boxlength;
    }

    public void setBoxlength(double boxlength) {
        this.boxlength = boxlength;
    }
    
    
    
}
