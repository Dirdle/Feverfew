/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

import feverfew.Testing;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Oscar
 * 
 * Class for menus composed of options
 */
public class Menu extends JPanel implements Pin, Testing {
    
    private boolean visible, dying;
    private int x,y,x2,y2, priority;
    private int entryCount, currentEntryCount;
    
    
    public Menu(int entryCount, int x, int y, int width){
        // Start by creating a JPanel
        super();
        this.x = x;
        this.y = y;
        this.entryCount = entryCount;
        this.currentEntryCount = 0;
        this.x2 = x + width;
        this.y2 = y + entryCount * MENUSUBBOXHEIGHT;
        
        this.setBackground(MENUBACKGROUND);
        this.setDoubleBuffered(true);
        //this.setPreferredSize?
        
        //Sets the layout to Box, which will lay out individual subcomponents
        //vertically, one per line
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void addOption(String optionName) throws MenuSizeException{
        // Add an option by calling JPanel's add method for a new option
        // Does NOT increase the size of the menu
        // If the menu is full, throws an exception
        if (this.currentEntryCount < this.entryCount){
            // Create a new menu sub box if there's room for one, align it left
            // and add it to the menu
            MenuSubBox subbox = new MenuSubBox(optionName);
            subbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(subbox);
            currentEntryCount++;
        }
        else {
            throw new MenuSizeException();
        }        
    }
    
    public void setX2(int x2){
        this.x2 = x2;
    }
    
    public void setY2(int y2){
        this.y2 = y2;
    }
    
    public int getX2(){
        return this.x2;
    }
    
    public int getY2(){
        return this.y2;
    }    
    
// <editor-fold defaultstate="collapsed" desc=" Pin Getters and Setters ">
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
    public Dimension getPosition() {
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

    private static class MenuSizeException extends Exception {

        public MenuSizeException() {
        }
        
        public MenuSizeException(String info){
            // Exception class already contains method for creating exception
            // containing a short message. Convenient!
            super(info);
        }
    }
}
