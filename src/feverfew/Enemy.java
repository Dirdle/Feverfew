/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Oscar
 */

@XmlRootElement(namespace = "feverfew.EnemyExtractor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Enemy {
    
    //@XmlAttribute
    //private String name;
    
    //private String displayName, description, spriteName;
    public int base_stam;//, base_will, stam_reg, will_reg;

    public Enemy(){
        
    }
    
// <editor-fold defaultstate="collapsed" desc=" Getters and Setters for fields">
//    public String getName() {
//        return name;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }
//    
//    public String getDisplayName() {
//        return displayName;
//    }
//    
//    public void setDisplayName(String displayName) {
//        this.displayName = displayName;
//    }
//    
//    public String getDescription() {
//        return description;
//    }
//    
//    public void setDescription(String description) {
//        this.description = description;
//    }
//    
//    public String getSpriteName() {
//        return spriteName;
//    }
//    
//    public void setSpriteName(String spriteName) {
//        this.spriteName = spriteName;
//    }
    
    public int getBase_stam() {
        return base_stam;
    }
    
    public void setBase_stam(int base_stam) {
        this.base_stam = base_stam;
    }
    
//    public int getBase_will() {
//        return base_will;
//    }
//    
//    public void setBase_will(int base_will) {
//        this.base_will = base_will;
//    }
//    
//    public int getStam_reg() {
//        return stam_reg;
//    }
//    
//    public void setStam_reg(int stam_reg) {
//        this.stam_reg = stam_reg;
//    }
//    
//    public int getWill_reg() {
//        return will_reg;
//    }
//    
//    public void setWill_reg(int will_reg) {
//        this.will_reg = will_reg;
//    }
// </editor-fold>
    
}
