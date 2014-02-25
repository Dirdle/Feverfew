/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;


import feverfew.Action;
import feverfew.Action;
import java.awt.Color;
import java.awt.Shape;

/**
 *
 * @author Oscar
 * 
 * WARNING: ALWAYS USE A SETSHAPE METHOD, THIS DOES NOT SET ITS OWN SHAPE DURING
 * CREATION
 */
public class ActionPin extends Scaffold {
    
    private Action action;
    private Sprite icon;
    
    private Shape shape;
    
    private String name;
    

//    TODO make some icons...
    
    
    public ActionPin(Action a, int x, int y){        
        
        // TODO initialise the icon
        // Do this by looking up the action's name (dot png) in a folder full
        // of icons named after the relevant action.
        this.action = a;        
        this.name = this.action.getDisplayName();
        this.setX(x);
        this.setY(y);
        
        this.setPriority((int) PRIORITIES.get(this.getClass().toString()));
        LOGGER.log("Set ActBar priority: " + this.getPriority());        
        this.setStroke(false);
        this.setFill(true);
        this.setFillColor(DEFTEXTCOLOR);
        
        LOGGER.log("Created ActionPin for " + this.name + " at (" 
                + this.getX() + ", " + this.getY() + ")");

    }
    
    public String getName(){
        return this.name;
    }
    
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Sprite getIcon() {
        return icon;
    }

    public void setIcon(Sprite icon) {
        this.icon = icon;
    }
    
}
