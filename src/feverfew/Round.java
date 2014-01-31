/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

import glyphs.ActBar;
import glyphs.ActionPin;
import glyphs.Pin;
import glyphs.StopPoint;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Oscar
 * 
 * A single round of action. Creating a round starts it. A round contains a
 * single action choice via actbar, and no other choices.
 * 
 * This class could also contain the methods for determining which actions
 * are made available, given a certain player stance, selected action set up,
 * and so on and so forth.
 */
public class Round implements Testing {
    
    boolean active;
    
    int actCount;
    Action[] actions;
    
    ActBar actbar;
    StopPoint[] stops;
    ActionPin[] actpins;
    
    Board board;
    // Need to be given the context in which stuff can happen
    
    // A list is useful so that after Board creates a Round, it can add all of 
    // that Round's pins easily
    ArrayList<Pin> pins;
    
    PlayerCharacter player;
    
    public Round(int actionCount, PlayerCharacter player){
        /** TODO figure out what arguments this should take
        *int actionCount: for now, let the Board decide how many actions
        *later, it might be better for PC objects to have associated action-
        *-counts that describe how many actions they can choose from each round
        
        *For now, let's write it to just take n random entries from the action 
        * pool
        * 
        * TODO add who 'controls' the round? 
        * No, it should extract that from the replacement for PC. Probably.
        */
        
        this.actCount = actionCount;
        this.player = player;
        
        // Set the sizes of arrays that depend on number of actions
        actpins = new ActionPin[actCount];
        stops = new StopPoint[actCount];
        // Note: while we DO generally know, at this stage, 
        // how many pins a round will generate, it is undesirable to manually
        // recompute this value every time the code is changed
        // Therefore a list structure is used.
        pins = new ArrayList<>(0);
        
        // Get a set of actions from the playerCharacter
        LOGGER.log("Retrieving action pool from " + player.name);
        LOGGER.log(player.name + " has " + player.actionPool.size() + 
                " actions");        
        Action[] pool = player.actionPool.toArray(new Action[0]);
        
        // Choose which actions will be available (currently, random)
        LOGGER.log("Selecting " + actCount + " actions from " +
                player.name +"'s pool.");        
        this.actions = chooseActions(pool,
                actCount);
        
        // Create the action bar
        LOGGER.log("Creating new action bar using: " + "\n" +
                "   Duration: " + ACTBAR_DURATION + "\n" +
                "   Action Count: " + actCount + "\n");
        actbar = new ActBar(ACTBAR_DURATION);
        pins.add(actbar);  
        
        // Create the stop points         
        for (int i = 0; i < actCount; i++){
            // Stop points at 1/2n, 3/2n etc
            double place = (2.0*i + 1.0)/(2.0*actCount);
            
            LOGGER.log("Creating stop point at " + 
                    Double.toString(place));            
            stops[i] = new StopPoint(place, actions[i].getDifficulty());   
        }
        

        
    }
    
    public void setBoard(Board b){
        this.board = b;
        this.finalise();
    }
    
    private void finalise(){
        //Add all stoppoints and neighbouring actionpins to the pinlist
        //Needs to be done after construction and board being set
        for (int i = 0; i < actCount; i++){
            pins.add(stops[i]);
            
            // Create the actionpins as they're added
            LOGGER.log("Creating image to describe action: " +
                    actions[i].getName());
            actpins[i] = new ActionPin(this.actions[i],
                    actbar.getX() 
                    + (int) ACTIONPINOFFSET,
                    
                    stops[i].getY() 
                    + (int)Math.floor(stops[i].getBoxlength()/2.0));
            
            // Each actionpin needs to have its shape set from its name
            actpins[i].setShape(this.board.stringToShape(actpins[i].getName()));
            pins.add(actpins[i]);            
        }
        
        this.start();
    }
    
    public void update(){
        // Call each cycle that the round exists for
        if (active){
            actbar.update();
            if (actbar.isDone()){
                this.stop();
            }
        }
    }
    
    public void start(){
        this.active = true;
    }
    
    public void stop(){
        // Ends the round
        this.active = false;
        this.setOutcome(score(actbar.totalLength - actbar.getLength()));
        for(int i = 0; i < pins.size(); i++){
            pins.get(i).die();
        }
    }

    public double score(int hitPosition){
        // Scoring function
        // First, find out if any stop points are relevant
        double hitProportion = hitPosition / (PROPORTIONY * DIM.height);
        LOGGER.log("Hit detected at " 
                + Double.toString(hitProportion));
        // this is the hit position as a proportion; more generally useful
        for (int i = 0; i < stops.length; i++){
            StopPoint aStop = stops[i];
            if (Feverfew.within(aStop.lowerBound, aStop.upperBound, 
                    hitProportion)){
                // Conditional on the hit having been within the boundaries of 
                // the stop point, get that stop point's score of the hit
                double baseScore = aStop.score(hitProportion);
                return baseScore + (double) i*1000;
            }
        }
        return -1;
        /** If no hit, return the miss-score (not 0 as that's ALMOST possible 
        * for a hit, negative numbers will NEVER be produced by a hit, though)
        * -1 is the most common "special case" score
        * The others are, at present:
        * -10: no score yet assigned to this stop point (error score)
        * -20: scoring defaulted, stop point did not have a type assigned
        *      or was assigned a non-existent type (error score)
        * 
        * If returning an actual negative score is ever required (what for?),
        * subtract MAXSCORE from the score after the score is retrieved.
        * 
        * The thousands-unit is the reference that informs the system WHICH stop
        * point has been hit, without any need for complex fiddling. It's pretty
        * easy to reverse: the actual score is the passed score % 1000, and the
        * stop point hit is log_10 
        */        
    }    
    
    private void setOutcome(int score){
        // determines the action the player selected from the score returned by 
        // the actbar, and sets the outcome accordingly - applies relevant
        // effects to the user and the target
        int rating = score % 1000;
        Action choice = actions[score/1000];
        this.player.takeAction(choice, rating);
        //code to determine targetting rules and which targets to apply to
        if ("single, any".equals(choice.getTargets())){
            //this.primeTarget.undergoAction(choice, rating)
        }
        else {
            //code to handle 'unusual' targetting cases
        }
    }
    private void setOutcome(double score) {
        setOutcome((int) score);
    }
     
    private Action[] chooseActions(Action[] availAction, int n){
        // Chooses n actions from the given array and returns them as an array
        // Does so by shuffling the array, then returning the first n entries
        // Want to create a new array, too, since the array being fed to this
        // probably shouldn't be returned shuffled
        Action[] shuffledAvailable = Feverfew.shuffle(availAction);        
        return Arrays.copyOfRange(shuffledAvailable, 0, n);        
    }


    
}
