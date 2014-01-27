/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * The project Feverfew
 * 
 */
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;



public class Feverfew extends JFrame implements Testing {

    /**
     * @param args the command line arguments
     * 
     * This class is the JFrame extending class
     * 
     */ 
    
    // classloader for all classes that will require one
    private static ClassLoader cl;
    
    public Feverfew() {
        
        LOGGER.log("Starting Feverfew");
        
        cl = this.getClass().getClassLoader();
        
        // Puts a new Board object onto the frame
        this.getContentPane().add(new Board(cl));
        
        this.addWindowListener(new MyWindowAdapter());
        
        this.pack();
        // Important to pack (ie build the frame) BEFORE choosing where to
        // put it...
        
        // A number of frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setTitle("Feverfew");
        setResizable(false);
        setVisible(true); 
    }
    
    
    public static void main(String[] args) {
        new Feverfew();
        
    }
    
    // And some functions I expect to come up a lot
    // Putting them here for ... accessibility? Meh
    public static int[] createUniformArray(int length, int content){
        // Creates an array of given length, with a given number as the only 
        // content, repeated.
        
        // Can't find a way to do this with generics... I feel it ought to be
        // possible, but fundamentally you'd have to go around Java's inability
        // to create a generic array, and you'd end up doing what you'd be
        // trying to avoid (ie a horrid mess of casting etc)
        int[] array = new int[length];
        for (int i = 0; i < length; i++){
            array[i] = content;           
        }
        return array;
    }
    
    public static boolean within(double low, double high, double n){
        return ((low <= n) && (high >= n));        
    }
    
    public static int randomRange(int low, int high){
        // Essentially just an accessible RNG
        Random gen = new Random();
        return gen.nextInt(high) + low + 1;
    }
        
    // Shuffle methods for templated array type. Aww yiss, templating.
    public static <T> T[] shuffle(T[] input){
        //Fisher-Yates shuffle, without altering original array
        Random gen = new Random();
        T[] output = input.clone();
        
        for (int i = output.length - 1; i > 0; i--){
            int index = gen.nextInt(i + 1);
            // Basic swap, always works. Could do XOR-swap with numeric types
            // but that's just being greedy, hmm?
            T temp = output[index];
            output[index] = output[i];
            output[i] = temp;
        }
        return output;        
    }
    
    public static HashMap getPrioritiesMap(){
        //This method creates a hashmap of the various Pins by getClass.toString
        // and their respective painting-priority
        HashMap output = new HashMap();
        output.put("class feverfew.ActBar", 10);
        output.put("class feverfew.StopPoint", 5);
        output.put("class feverfew.ActionPin", 6);
        
        return output;
    }

    private class MyWindowAdapter 
        extends WindowAdapter implements WindowListener {

        @Override    
        public void windowClosing(WindowEvent e){
            LOGGER.close();
            System.exit(0);
        }        
    }
    
}