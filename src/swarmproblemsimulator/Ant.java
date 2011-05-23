/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.util.Random;

/**
 *
 * @author cableray
 */
public class Ant {
    protected PheremoneDSN locationNode, source;
    protected Random rand = new Random();


    public Random getRand()
      {
        return rand;
      }


    public void setRand(Random rand)
      {
        this.rand = rand;
      }


    public Ant(){

    }

    public Ant(PheremoneDSN source){
        this.source=source;
    }

    public Ant(PheremoneDSN source, Random seed){
        this.locationNode=this.source=source;
        this.rand = seed;
    }

    public void setSource(PheremoneDSN source){
        this.source=source;
    }

    public PheremoneDSN getSource(){
        return source;
    }

    public PheremoneDSN getLocationNode()
      {
        return locationNode;
      }


    public void setLocationNode(PheremoneDSN locationNode)
      {
        this.locationNode = locationNode;
      }

    
    public void move(){
        
    }


}
