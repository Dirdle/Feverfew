/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

import feverfew.Testing;
import java.awt.Point;

/**
 *
 * @author Oscar
 * 
 * Pins are 'things that go on the board.' They have a set of common methods,
 * so Pin is implemented as an interface.
 * 
 * Currently used or expected implementors of Pin:
 * 
 * -Scaffold: an object that Java can draw via methods such as Fill, 
 *  Stroke etc
 * -Sprite: an object that Java draws by painting a predefined image to
 *  the screen
 * -Swingable: an object that extends a Swing component, or Component, and is
 *  added to the Board instead of being drawn
 */
public interface Pin extends Testing {
       

    void die();

    Point getPosition();

    int getPriority();

    int getX();

    int getY();

    boolean isDying();

    boolean isVisible();

    void setDying(boolean newdying);

    void setLocation(Point place);

    void setPriority(int priority);

    void setVisibility(boolean b);

    void setX(int newx);

    void setY(int newy);
    
}
