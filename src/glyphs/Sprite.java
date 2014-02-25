/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

/**
 *
 * @author Oscar
 * An extension of Temp for objects that use a pre-made image, under some 
 * transformation (eg rotation, inversion, translation)
 * 
 */

import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Sprite implements Pin {
    
    private Image visage;
    private AffineTransform transform;
    
    private boolean dying, visible;
    private int priority, x, y;     
    
    public Sprite(){
        this.setVisibility(true);
    }
    
    public Image getImage(){
        return this.visage;
    }
    
    public void setImage(Image i){
        this.visage = i;
    }

// <editor-fold defaultstate="collapsed" desc=" Getters and Setters for Pin ">
    @Override
    public boolean isVisible() {
        return visible;
    }
    
    @Override
    public void die() {
        this.visible = false;
        this.dying = true;
    }
    
    @Override
    public void setVisibility(boolean b) {
        this.visible = b;
    }
    
    @Override
    public int getPriority() {
        return priority;
    }
    
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    @Override
    public void setX(int newx) {
        this.x = newx;
    }
    
    @Override
    public void setY(int newy) {
        this.y = newy;
    }
    
    @Override
    public void setLocation(Dimension place) {
        this.x = place.width;
        this.y = place.height;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
    
    @Override
    public Dimension getLocation() {
        return new Dimension(x, y);
    }

    //
    @Override
    public void setDying(boolean newdying) {
        this.dying = newdying;
    }
    
    @Override
    public boolean isDying() {
        return this.dying;
    }
// </editor-fold> 
}   
