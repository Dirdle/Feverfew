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
public class EnemyExtractor {
    
    private ArrayList<Enemy> listOfEnemies;
    
    public EnemyExtractor(){
        
    }
    
    public ArrayList<Enemy> getListOfEnemies(){
        return listOfEnemies;
    }
    
    @XmlElementWrapper(name = "enemyList")
    @XmlElement(name = "enemy")
    public void setListOfEnemies(ArrayList<Enemy> list){
        this.listOfEnemies = list;
    }
}
