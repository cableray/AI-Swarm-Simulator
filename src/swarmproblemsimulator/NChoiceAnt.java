/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cableray
 */
public class NChoiceAnt extends Ant {
    private boolean returning=false;
    private boolean laden=false;
    protected Vector<PheremoneDSN> prevNodes=new Vector<PheremoneDSN>();
    public static double pheremoneWhenLaden=10;


    public NChoiceAnt(PheremoneDSN source, Random seed){
        super(source, seed) ;
        source.recieveAnt();
    }

    public boolean isReturning(){
        return returning;
    }

    @Override
    public void move(){

        DiscreteSpaceNode[] adjacentNodes= locationNode.getAdjacentNodes();
        int nodeSize = adjacentNodes.length;
        PheremoneDSN[] PherNodes=new PheremoneDSN[nodeSize] ;
        for ( int i=0; i<nodeSize; i++ )
          {
            PherNodes[i] = (PheremoneDSN) adjacentNodes[i];
          }
        double pMove=1;
        double randD=rand.nextDouble();
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Random for ant "+this.hashCode()+" is "+randD);

        double pherSum=0, pherSqSum=0;

        for ( PheremoneDSN pdsn : PherNodes)
          {
            if (pdsn!= null && !prevNodes.contains(pdsn)){
                pherSqSum += Math.pow(pdsn.getPheremoneLevel()+5, 2);
                pherSum += pdsn.getPheremoneLevel();
            }
          }

        pMove = .5*(1+Math.tanh((pherSum)/100-1));
        if (pMove>=rand.nextDouble()) return;


        PheremoneDSN choice=null;
        Vector<PheremoneDSN> fullPdsns = new Vector<PheremoneDSN>(adjacentNodes.length);
        do
        {
        double startRand=0;
        choice=null;
        StringBuilder intervalsLogString = new StringBuilder("Node Intervals:");
            for ( PheremoneDSN pdsn : PherNodes)
              {
                if (pdsn!= null && !fullPdsns.contains(pdsn) && !prevNodes.contains(pdsn)){
                    double pThis = Math.pow(pdsn.getPheremoneLevel()+5, 2)/pherSqSum;
                    intervalsLogString.append(" ["+startRand+","+(startRand+pThis)+")");
                    if (randD>=startRand && randD<(startRand+pThis)) {
                        intervalsLogString.append("!");
                        choice=pdsn;
                        //break;
                    }
                    startRand+=pThis;
                }
              }
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, intervalsLogString.toString());
            if (choice==null) {
                if (prevNodes.containsAll(Arrays.asList(PherNodes))){
                    prevNodes.removeAll(Arrays.asList(PherNodes));
                    Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "Ant Blocked, blockage cleared");

                }
                else Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "No nodes picked, but ant not likely blocked. Full Nodes:"+fullPdsns.size());
                return;
            }
            if(choice.isFull()){
                fullPdsns.add(choice);
                pherSqSum -= Math.pow(choice.getPheremoneLevel()+5, 2);
                randD=rand.nextDouble();
            }
        } while (choice.isFull());

        prevNodes.add(choice);
        
        if (choice.hasFood() && !this.laden){
            choice.getFood(1);
            this.laden=true;
            this.returning=true;
            this.prevNodes.clear();
        }
        locationNode.removeAnt();
        setLocationNode(choice);
        choice.recieveAnt();
        double pAdd=0, pCurr=choice.getPheremoneLevel();
        if (laden && pCurr<300) pAdd = pheremoneWhenLaden;
        if (!laden && pCurr < 1000) pAdd = 1;
        this.locationNode.setPheremoneLevel(pCurr+pAdd);
        if (locationNode==source){
            laden=false;
            returning=false;
            this.prevNodes.clear();
        }

    }

}
