/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.util.Random;
import javax.media.j3d.Node;

/**
 *
 * @author cableray
 */
public class TwoChoiceAnt extends Ant {
    private boolean returning=false;
    private boolean laden=false;
    public static double divergence=1.2;
    
    public TwoChoiceAnt(PheremoneDSN source, Random seed){
        super(source, seed) ;
        source.recieveAnt();
    }

    public boolean isReturning(){
        return returning;
    }

    @Override
    public void move(){
        DiscreteSpaceNode[] adjacentNodes= locationNode.getAdjacentNodes();
        PheremoneDSN[] PherNodes=new PheremoneDSN[4] ;
        for ( int i=0; i<4; i++ )
          {
            PherNodes[i] = (PheremoneDSN) adjacentNodes[i];
          }
        int offset = 0;
        if (returning) offset=2; else offset = 0;
        PheremoneDSN left = PherNodes[offset+0], right= PherNodes[offset+1];
        double pMove=0, pLeft=0;
        if (left!=null){
            Coordinate2D leftC = (Coordinate2D) left.getLabel();
            if(right!=null){
                Coordinate2D rightC = (Coordinate2D) right.getLabel();
                int leftCenterDisY=Math.abs(leftC.y-leftC.x), rightCenterDisY=Math.abs(rightC.y-rightC.x);
                
                double lCentAdj=Math.pow(leftCenterDisY,divergence),rCentAdj=Math.pow(rightCenterDisY,divergence);
                pMove = .5*(1+Math.tanh((left.getPheremoneLevel()+right.getPheremoneLevel())/100-1));
                pLeft = Math.pow(left.getPheremoneLevel()+lCentAdj+5, 2)/(Math.pow(left.getPheremoneLevel()+lCentAdj+5, 2)+Math.pow(right.getPheremoneLevel()+rCentAdj+5,2));
            }
            else {
                pMove = .5*(1+Math.tanh((left.getPheremoneLevel())/100-1));
                pLeft=1;

            }

        }
        else {
            pLeft=0;
            if(right==null){
                pMove=0;
                returning=true;
            }
            else pMove = .5*(1+Math.tanh((right.getPheremoneLevel())/100-1));

        }

        if (rand.nextDouble()>=pMove) return;

        //int pick = rand.nextInt(2);
        int pick = ((rand.nextDouble()<pLeft) ? 0:1);
        PheremoneDSN choice = PherNodes[offset+pick];
        PheremoneDSN altchoice = PherNodes[offset+(pick==1?0:1)];
        //if (altchoice==null) altchoice=choice;
        if (choice.isFull()) {
            if (altchoice==null||altchoice.isFull()) return;
            choice=altchoice;
        }
        //if (choice==null) { offset =( (returning= !returning) ? 2:0 ); choice=PherNodes[offset+pick]; }
        if (choice.hasFood()){
            choice.getFood(1);
            this.laden=true;
            this.returning=true;
        }
        locationNode.removeAnt();
        setLocationNode(choice);
        locationNode.recieveAnt();
        double pAdd=0, pCurr=locationNode.getPheremoneLevel();
        if (laden && pCurr<300) pAdd = 10;
        if (!laden && pCurr < 1000) pAdd = 1;
        this.locationNode.setPheremoneLevel(pCurr+pAdd);
        if (locationNode==source){
            laden=false;
            returning=false;
        }
        
    }

}
