/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feverfew;

/**
 *
 * @author Oscar
 */

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class ActionExtractor {
    
    // This list stores the action objects that will be created

    private ArrayList<Action> listOfActions; 
    
    public ActionExtractor(){
        
    }
    
    public ArrayList<Action> getListOfActions(){
        return listOfActions;
    }
    
    @XmlElementWrapper(name = "actionList")
    @XmlElement(name = "action")    
    public void setListOfActions(ArrayList<Action> list){
        this.listOfActions = list;
    }    
}
