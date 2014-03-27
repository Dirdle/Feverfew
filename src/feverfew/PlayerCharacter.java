/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

import java.util.ArrayList;

/**
 *
 * @author Oscar
 */
public class PlayerCharacter implements Testing {
    
    // This class represents an in-game PC, not the actual player
    // TODO create a superclass of this that also covers mobs
    
    public ArrayList<GameAction> actionPool;
    public String name;
    
    public int stamina;
    public int willpower;
    
    public int availableActionCount;
    
    public PlayerCharacter(ArrayList<GameAction> actions){
        this.actionPool = actions;
        
        // ONLY FOR TEST
        this.name = "TEST";
        this.stamina = 100;
        this.willpower = 100;
        
       
        
    }
    
    public void takeAction(GameAction action, int rating){
        LOGGER.log(this.name + " takes action: " + action.getName());
        // Imposes costs etc on this PlayerCharacter for using an GameAction with
        // a given Rating for success.
        
        // For the most part, actions will have constant costs. For now, that's
        // all we'll use.
        
        // The PC needs to have Traits that can modify costs in various ways. 
        // This will just have to wait I think
        
        this.stamina -= action.getStam_cost();
        this.willpower -= action.getWill_cost();
    }
    
}
