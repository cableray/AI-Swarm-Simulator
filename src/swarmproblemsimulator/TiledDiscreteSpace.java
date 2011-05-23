/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.awt.PageAttributes.OriginType;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.text.normalizer.UProperty;

/**
 * index layout:
 *
 * <pre>
 *   0
 * 3|_|1
 *   2
 * </pre>
 *
 * @author cableray
 */
public class TiledDiscreteSpace extends DiscreteSpace{

    public static final int UP=0,RIGHT=1,DOWN=2,LEFT=3;

    private int xwidth, ywidth;


    public int getXwidth()
      {
        return xwidth;
      }


    public int getYwidth()
      {
        return ywidth;
      }


    public TiledDiscreteSpace(DiscreteSpaceNode origin)
      {
        super(origin);
        if (origin.edges !=4) throw new IllegalArgumentException("can only seed from square nodes");
        origin.setLabel(new Coordinate2D(0, 0));
      }


    @Override
    public void generate(int numberOfNodes)
      {
        int width = (int) Math.sqrt(numberOfNodes);
        if (width*width != numberOfNodes) throw new IllegalArgumentException("numberOfNodes must be square");
        generate(width, width);
      }

    public void generate(int xwidth, int ywidth){
        DiscreteSpaceNode currNode=null;
        Coordinate2D currCoor=null;
        this.xwidth=xwidth; this.ywidth=ywidth;
        for (int i=0; i<xwidth; i++){
            for(int j=0; j<ywidth; j++){
                currCoor=new Coordinate2D(i, j);
                if (i==j&&i==0){
                    currNode=origin;
                }
                else {
                    
                    try
                      {

                        currNode = origin.getClass().getConstructor(int.class).newInstance(4);


                      }
                    catch ( IllegalArgumentException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    catch ( InvocationTargetException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    catch ( NoSuchMethodException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    catch ( SecurityException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    catch ( InstantiationException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    catch ( IllegalAccessException ex )
                      {
                        Logger.getLogger(TiledDiscreteSpace.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    currNode.setLabel(currCoor);
                }
                space.put(currCoor, currNode);
                Coordinate2D previousAdjLeft = new Coordinate2D(i-1, j), previousAdjDwn = new Coordinate2D(i, j-1);
                if (i-1 >=0) 
                    DiscreteSpaceNode.link(currNode, LEFT, space.get(previousAdjLeft), RIGHT);
                if (j-1 >=0)
                    DiscreteSpaceNode.link(currNode, DOWN, space.get(previousAdjDwn), UP);

            }
        }
    }
    
    public void wrapEdges(){
        for (int i=0; i<xwidth; i++){
            DiscreteSpaceNode.link(this.getNode(i, 0),DOWN,this.getNode(i, ywidth-1),UP);
        }
        for (int i=0; i<ywidth; i++){
            DiscreteSpaceNode.link(this.getNode(0,i),DOWN,this.getNode(xwidth-1,i),UP);
        }
    }

    public DiscreteSpaceNode getNode(int x, int y){
        return space.get(new Coordinate2D(x, y));
    }



    
    
    

}
