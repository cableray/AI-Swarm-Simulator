/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.util.Vector;

/**
 *
 * @author cableray
 */
public class DiscreteSpaceNode {
 public final int edges;
 protected DiscreteSpaceNode[] adjacentNodes;
 protected Object label;




    public Object getLabel()
      {
        return label;
      }


    public void setLabel(Object label)
      {
        this.label = label;
      }
    
    public DiscreteSpaceNode(int numberOfEdges){
        edges=numberOfEdges;
        adjacentNodes=new DiscreteSpaceNode[numberOfEdges];
    }

    public DiscreteSpaceNode( DiscreteSpaceNode[] nodes)
    {
        edges=nodes.length;
        adjacentNodes=nodes.clone();
    }

    public DiscreteSpaceNode(DiscreteSpaceNode[] nodes, Object label)
    {
        this(nodes);
        this.label=label;
    }
    
    public DiscreteSpaceNode[] getAdjacentNodes(){
        return adjacentNodes.clone();
    }

    public void setAdjacentNodes(DiscreteSpaceNode[] adjacentNodes)
      {
        if (adjacentNodes.length>edges) throw new ArraySizeException();
        this.adjacentNodes = adjacentNodes;
      }

    public DiscreteSpaceNode getNodeAt(int index){
        return adjacentNodes[index];
    }

    public void setNodeAt(int index, DiscreteSpaceNode node){
        this.adjacentNodes[index]=node;
    }
    
    public void link(int firstNodeEdge, DiscreteSpaceNode secondNode, int secondNodeEdge){
        link(this,firstNodeEdge, secondNode, secondNodeEdge);
    }
    
    public static void link(DiscreteSpaceNode firstNode, int firstNodeEdge, DiscreteSpaceNode secondNode, int secondNodeEdge){
        if (firstNode.edges <= firstNodeEdge || secondNode.edges <= secondNodeEdge){
            throw new IllegalArgumentException("Number of edges is less than or equal to the index specified");
        }

        firstNode.setNodeAt(firstNodeEdge, secondNode);
        secondNode.setNodeAt(secondNodeEdge, firstNode);
    }


}
