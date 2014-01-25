/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * An extension of Pin for objects that use a pre-made image, under some 
 * transformation (eg rotation, inversion, translation)
 * 
 */

import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Sprite extends Pin {
    
    private Image visage;
    private AffineTransform transform;
    
    public Sprite(){
        this.setVisibility(true);
    }
    
    public Image getImage(){
        return this.visage;
    }
    
    public void setImage(Image i){
        this.visage = i;
    }
    
}
