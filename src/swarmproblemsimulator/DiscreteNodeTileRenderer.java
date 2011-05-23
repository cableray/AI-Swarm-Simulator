/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.awt.Graphics2D;

/**
 *
 * @author cableray
 */
public class DiscreteNodeTileRenderer {
    private DiscreteSpaceNode node;


    public DiscreteSpaceNode getNode()
      {
        return node;
      }


    public void setNode(DiscreteSpaceNode node)
      {
        this.node = node;
      }

    public void draw(Graphics2D g2){
        int width = g2.getClipBounds().width, height = g2.getClipBounds().height;
    }


}
