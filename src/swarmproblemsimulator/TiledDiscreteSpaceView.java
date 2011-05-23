/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cableray
 */
public class TiledDiscreteSpaceView extends JPanel{
    protected TiledDiscreteSpace space;

    public TiledDiscreteSpaceView(TiledDiscreteSpace space){
        super(null);
        this.space=space;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g;

    }

    @Override
    public void paintChildren(Graphics g){
        //super.paintChildren(g);
        Graphics2D g2= (Graphics2D)g;


    }
}
