/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 * 
 * A general class for Actions, i.e. things that happen in the game.
 * Examples: Attack, Flee, a magic spell. Specific actions could be created as
 * classes that extend this class, for ease of coding, or as objects that
 * instantiate this class. The latter would probably need to be created during
 * game loading and stored somewhere, and would be much simpler. 
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "feverfew.ActionExtractor")
@XmlAccessorType(XmlAccessType.FIELD)
public class GameAction implements Testing {    
    
    @XmlAttribute
    private String name;
    
    private String displayName, description,
            targets, restrictions, special;
    public int difficulty, stam_cost, will_cost, dodge_cost, block_cost;
    
    public GameAction(){
    
    }   
    
// <editor-fold defaultstate="collapsed" desc=" Getters and Setters for fields ">
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getTargets() {
        return targets;
    }
    
    public void setTargets(String targets) {
        this.targets = targets;
    }
    
    public String getRestrictions() {
        return restrictions;
    }
    
    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }
    
    public String getSpecial() {
        return special;
    }
    
    public void setSpecial(String special) {
        this.special = special;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    public int getStam_cost() {
        return stam_cost;
    }
    
    public void setStam_cost(int stam_cost) {
        this.stam_cost = stam_cost;
    }
    
    public int getWill_cost() {
        return will_cost;
    }
    
    public void setWill_cost(int will_cost) {
        this.will_cost = will_cost;
    }
    
    public int getDodge_cost() {
        return dodge_cost;
    }
    
    public void setDodge_cost(int dodge_cost) {
        this.dodge_cost = dodge_cost;
    }
    
    public int getBlock_cost() {
        return block_cost;
    }
    
    public void setBlock_cost(int block_cost) {
        this.block_cost = block_cost;
    }
// </editor-fold>
    
}
