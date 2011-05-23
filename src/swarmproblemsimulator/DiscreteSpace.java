/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.util.HashMap;

/**
 *
 * @author cableray
 */
public class DiscreteSpace {
    protected HashMap<Object,DiscreteSpaceNode> space = new HashMap<Object, DiscreteSpaceNode>();
    protected DiscreteSpaceNode origin;


    public DiscreteSpaceNode getOrigin()
      {
        return origin;
      }


    public HashMap<Object, DiscreteSpaceNode> getSpace()
      {
        return space;
      }


    public DiscreteSpace(DiscreteSpaceNode origin )
      {
        this.origin = origin;
      }

    public void generate(int numberOfNodes){
        
    }

    public DiscreteSpaceNode getNode(Object label){
        return space.get(label);
    }





}
