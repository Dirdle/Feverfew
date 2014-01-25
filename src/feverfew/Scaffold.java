/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.*;

public class Scaffold extends Pin {
    
    private boolean fill;
    private boolean stroke;
    // If true, perform action of same name on shape
    
    private Color fillColor;
    private Color strokeColor;
    
    private BasicStroke pen;
    
    private Shape shape;
    // The actual geometric shape needs to be stored, but this is the only
    // real option for doing so generally.
    
    public Scaffold(){
        this.setVisibility(true);
    }
    
    
    public boolean isFilled(){
        return this.fill;
    }
    
    public boolean isStroked(){
        return this.stroke;
    }
    
    public void setFill(boolean newfill){
        this.fill = newfill;
    }
    
    public void setStroke(boolean newstroke){
        this.stroke = newstroke;
    }
    
    //
    
    public Color getFillColor(){
        return this.fillColor;
    }
    
    public Color getStrokeColor(){
        return this.strokeColor;
    }
    
    public void setFillColor(Color c){
        this.fillColor = c;
    }
    
    public void setStrokeColor(Color c){
        this.strokeColor = c;
    }

    //
    
    public BasicStroke getPen(){
        return this.pen;
    }
    
    public void setPen(BasicStroke newpen){
        this.pen = newpen;
    }
    // Could include basically all of BasicStroke's constructors, but that's
    // just silly for the scope of this project
    
    
    public Shape getShape(){
        try {
            return this.shape;
        }
        catch (NullPointerException NPE){
            // Returns a rectangle if no shape available; no need to crash the
            // program over this
            LOGGER.log("Tried to get a nonexistent shape!");
            return new Rectangle(4,4);
        }
    }
    
    public void setShape(Shape s){
        this.shape = s;
    }
    
}
