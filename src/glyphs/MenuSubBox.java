/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glyphs;

import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

/**
 *
 * @author Oscar
 */
public class MenuSubBox extends JButton {  
    
    // Construction is handled by JTextArea class
    public MenuSubBox(String text){
        super(text);
    }   
    // TODO various style stuff...
    // TODO add code to let the sub box be 'selected,'
    // Having the sub box contain the code to call the relevant function SEEMS 
    // like a good idea on the face of it, but it might make things horribly 
    // difficult to assign?
    // Actually having the menu itself track the correspondance between options
    // and outcomes of selecting those options might work better?
    // Draw a diagram before proceding with this
}
