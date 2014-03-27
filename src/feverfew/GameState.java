/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * 
 * Represents the internal state/stage of the game, allowing for cycle() to
 * know what to do in any given state without a mess of if statements
 * 
 * Not a subclass of board since FLogger also needs to access it, and adding
 * a public inner class to board just makes it even more cluttered and overused.
 * 
 * No need for states to have any internal structure or values
 */
public enum GameState {
    STARTINGCOMBAT, CREATINGCOMBAT, RUNNINGCOMBAT, WAITINGINPUT
}
