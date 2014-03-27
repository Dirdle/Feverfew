/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

import feverfew.GameAction;
import feverfew.Testing;
import java.awt.Color;
import java.awt.Shape;

/**
 *
 * @author Oscar
 * 
 */
public class ActionPin extends MenuSubBox implements Testing {
    
    private GameAction action;
    private Sprite sprite;
    
    private String name;
    

    //Construction: handled by MSB, which passes it up to JTA...
    public ActionPin(GameAction a, int x, int y){        
        super(a.getDisplayName());        
        this.setLocation(x, y);
        LOGGER.log("Created ActionPin");
    }
    

//    TODO make some icons...
    
    
//    public ActionPin(GameAction a, int x, int y){        
//        
//        // TODO initialise the icon
//        // Do this by looking up the action's name (dot png) in a folder full
//        // of icons named after the relevant action.
//        this.action = a;        
//        this.name = this.action.getDisplayName();
//        this.setX(x);
//        this.setY(y);
//        
//        this.setPriority((int) PRIORITIES.get(this.getClass().toString()));
//        LOGGER.log("Set ActBar priority: " + this.getPriority());        
//        this.setStroke(false);
//        this.setFill(true);
//        this.setFillColor(DEFTEXTCOLOR);
//        
//        LOGGER.log("Created ActionPin for " + this.name + " at (" 
//                + this.getX() + ", " + this.getY() + ")");
//
//    }
    
    public String getName(){
        return this.name;
    }
    
    public GameAction getGameAction() {
        return action;
    }

    public void setAction(GameAction action) {
        this.action = action;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite icon) {
        this.sprite = icon;
    }
    
}
