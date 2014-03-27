/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

import feverfew.Testing;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Oscar
 */
public class Menu extends JPanel implements Testing, Pin {
    
    private boolean dying;
    private int priority;
    private int entryCount, currentEntryCount;    
    
    // int represents position of cursor in menu
    private int cursor;
    // array of the options
    private MenuSubBox[] options;
    
    public Menu(int entryCount, int a, int b, int width){
        super(true);    
        this.setBounds(a,b,width,entryCount * MENUSUBBOXHEIGHT);
        this.entryCount = entryCount;
        currentEntryCount = 0;
        options = new MenuSubBox[entryCount];

        this.setBackground(MENUBACKGROUND);       
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void addOption(String optionName) throws MenuSizeException{
        // Add an option by calling JPanel's add method for a new option
        // Does NOT increase the size of the menu
        // If the menu is full, throws an exception
        if (this.currentEntryCount < this.entryCount){
            // Create a new menu sub box if there's room for one
            MenuSubBox subbox = new MenuSubBox(optionName);
            subbox.setAlignmentX(Component.CENTER_ALIGNMENT);
            subbox.setMaximumSize(new Dimension(subbox.getPreferredSize().width,
                    MENUSUBBOXHEIGHT));
            // Add the new MSB to the panel and the array
            this.add(subbox);            
            this.options[currentEntryCount] = subbox;
            currentEntryCount++;
        }
        else {
            throw new MenuSizeException();
        }
    }
    
    // Move the cursor and move the Focus to the relevant option
    public void downshift(){
        cursor += 1;
        this.options[cursor].requestFocus();
    }
    public void upshift(){
        cursor -= 1;
        this.options[cursor].requestFocus();
    }
    
// <editor-fold defaultstate="collapsed" desc=" Pin Getters and Setters ">
    
    @Override
    public void die() {
        this.setVisible(false);
        this.dying = true;
    }
    
    @Override
    public void setVisibility(boolean b) {
        this.setVisible(b);
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
        this.setLocation(newx, this.getY());
    }
    
    @Override
    public void setY(int newy) {
        this.setLocation(this.getX(), newy);;
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

    @Override
    public Point getPosition() {
        return this.getLocation();
    }

    @Override
    public void setLocation(Point place) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
// </editor-fold>        

    public static class MenuSizeException extends Exception {
        
        public MenuSizeException(){
        }

        public MenuSizeException(String info) {
            super(info);
        }
    }
    
}
