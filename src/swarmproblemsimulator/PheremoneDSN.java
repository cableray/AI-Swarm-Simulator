/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

/**
 *
 * @author cableray
 */
public class PheremoneDSN extends DiscreteSpaceNode{

    protected int timestamp;
    protected double pheremoneLevel=0;
    protected int antCount=0, maxAnts=5;
    protected double food=0;


    public double getPheremoneLevel()
      {
        return pheremoneLevel;
      }


    public void setPheremoneLevel(double pheremoneLevel)
      {
        this.pheremoneLevel = pheremoneLevel;
      }


    public int getTimestamp()
      {
        return timestamp;
      }


    public void setTimestamp(int timestamp)
      {
        this.timestamp = timestamp;
      }

    public PheremoneDSN()
      {
        super(4); //by default set the edges to 4
      }

    public PheremoneDSN(int edges){
        super(edges);
    }

    public boolean hasAnt(){
        return antCount>0;
    }

    public boolean isFull(){
        return antCount>maxAnts;
    }

    public boolean recieveAnt(){
        if(!isFull()){
            ++antCount;
            return true;
        }
        return false;
    }

    public void removeAnt(){
        --antCount;
    }


    public int getMaxAnts()
      {
        return maxAnts;
      }


    public void setMaxAnts(int maxAnts)
      {
        this.maxAnts = maxAnts;
      }

    


    public boolean hasFood()
      {
        if (food <=0) return false;
        return true;
      }

    public double getFood(double amount){
        return food=food-amount;
    }

    public void setFood(double amount){
        food=amount;
    }


    public int getAntCount()
      {
        return antCount;
      }






}
