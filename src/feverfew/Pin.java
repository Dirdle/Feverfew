/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * This class is extended by all classes that can be printed on the board
 * It implements features common to all such classes, such as Visibility and
 * Location.
 * 
 * It would be possible to convert Pin into an interface and make Sprite,
 * Scaffold etc implement their own methods for die() and getLocation() etc but
 * that seems wasteful to me, given how simple and 'unalterable' these methods
 */


import java.awt.Dimension;

public abstract class Pin implements Testing {
    // Abstract; nothing should make a Pin, only make a Sprite or a Scaffold
    
    private boolean visible;
    private boolean dying;    
    
    public int priority;
    // This int determines where in the 'stack' of drawn components to put the
    // pin. Not yet used by anything, but needs to be implemented
    
    protected int x;
    protected int y;
    // Gotta have a location (top left corner)
    
    
    
    /** There should be two types of pins: those that just have a shape (and
    * associated stroking methods): scaffolds. And those that import an image:
    * sprites.
    */ 
    
    public boolean isVisible(){
        return visible;
    }
            
    public void die(){
        this.visible = false;
        this.dying = true;
    }
    
    public void setVisibility(boolean b){
        this.visible = b;
    }
    
    //
    
    public void setX(int newx){
        this.x = newx;
    }
    
    public void setY(int newy){
        this.y = newy;
    }
    
    public void setLocation(Dimension place){
        this.x = place.width;
        this.y = place.height;
    }

    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public Dimension getLocation(){
        return new Dimension(x, y);
    }
    
    //
    
    public void setDying(boolean newdying){
        this.dying = newdying;
    }
    
    public boolean isDying(){
        return this.dying;
    }   
    
    
}
