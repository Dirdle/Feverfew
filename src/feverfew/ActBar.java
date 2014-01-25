/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 * @author Oscar
 * 
 * The bar that times the player when they try to act.
 */

import java.awt.geom.Line2D;

public class ActBar extends Scaffold implements Testing {  
    
    
    public int totalLength;
    
    
    private int length;
    // length of line, and position of top
    
    public int duration;
    // Gonna want to define duration in ticks (updates) at point of creation
    // Could change to 'speed' but same difference
    
//    private StopPoint[] stops;
    // An array of the number of stop points to place on the bar
    
    private double delta;
    // The amount to decrease the line by each update
    
    private Line2D line;
//     the line object itself
//     I'm sure having a thing called Double will never be an issue
//     whether the bar is currently in use
    
    public ActBar(int time){
        // Creates the bar object on start-up.
        // time: the duration of the actionbar to be created. Typical: 50
        // stopcount: number of stop points to create on the bar
        
        totalLength = (int) (PROPORTIONY * DIM.height);
        
        this.duration = time; 
        
        this.setFill(false);
        this.setStroke(true);
        this.setStrokeColor(ACTBARCOLOR);
        this.setPen(ACTBARSTYLE);        
        
        // Sets the bar's dimensions
        this.set();
        
        // Sets the amount to decrease length on each update
        this.delta = this.length / this.duration;
        if (delta == 0.0){
            // Could place a properly-alterable minimum value somewhere
            delta = 1.0;
        }
        LOGGER.log("Set actbar delta to " + delta);
    }
    
    public final void set(){
        // Sets/resets the bar to original size        
        this.setY( (int) ((1 - PROPORTIONY) * DIM.height/2.0));
        this.setX( (int) (PROPORTIONX * DIM.width));
        
        LOGGER.log("Actbar position set to (" + this.x + ", "
                + this.y + ")");
        length  = totalLength;
        
        LOGGER.log("Actbar length set to " + length);
        line = new Line2D.Double(0, 0, 0, length);
        this.setShape(line);
    }
    
    public void update(){
        // Retracts the bar from the top down
        y += delta;
        length -=delta;
        if (length < delta){
            // the bar has run out
//            this.score();
        }
        this.line.setLine(0, 0, 0, length);
        this.setShape(line);
    }
    
//    public double score(){       
//        return this.score((int) (PROPORTIONY * DIM.height) - length);
//    }
//    
//    public double score(int hitPosition){
//        // Scoring function
//        // First, find out if any stop points are relevant
//        double hitProportion = hitPosition / (PROPORTIONY * DIM.height);
//        LOGGER.log("Hit detected at " 
//                + Double.toString(hitProportion));
//        // this is the hit position as a proportion; more generally useful
//        for (int i = 0; i < stops.length; i++){
//            StopPoint aStop = stops[i];
//            if (Feverfew.within(aStop.lowerBound, aStop.upperBound, 
//                    hitProportion)){
//                // Conditional on the hit having been within the boundaries of 
//                // the stop point, get that stop point's score of the hit
//                double baseScore = aStop.score(hitProportion);
//                return baseScore + (double) i*1000;
//            }
//        }
//        return -1;
//        /** If no hit, return the miss-score (not 0 as that's ALMOST possible 
//        * for a hit, negative numbers will NEVER be produced by a hit, though)
//        * -1 is the most common "special case" score
//        * The others are, at present:
//        * -10: no score yet assigned to this stop point (error score)
//        * -20: scoring defaulted, stop point did not have a type assigned
//        *      or was assigned a non-existent type (error score)
//        * 
//        * If returning an actual negative score is ever required (what for?),
//        * subtract MAXSCORE from the score after the score is retrieved.
//        * 
//        * The thousands-unit is the reference that informs the system WHICH stop
//        * point has been hit, without any need for complex fiddling. It's pretty
//        * easy to reverse: the actual score is the passed score % 1000, and the
//        * stop point hit is log_10 
//        */        
//    }

//    public StopPoint[] getStops(){
//        return this.stops;
//    }

    public int getLength() {
        return length;
    }
    
    public boolean isDone(){
        if (this.length < this.delta) {
            return true;
        }
        else {
            return false;
        }
    }
}
