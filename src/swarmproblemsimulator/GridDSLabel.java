/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.Border;

/**
 *
 * @author cableray
 */
public class GridDSLabel extends JLabel {
    protected PheremoneDSN node;
    protected final Border normalBorder=BorderFactory.createLineBorder(Color.black, 1);
    protected final Border foodBorder=BorderFactory.createLineBorder(Color.red, 2);


    public PheremoneDSN getNode()
      {
        return node;
      }
    protected static final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(swarmproblemsimulator.SwarmProblemSimulatorApp.class).getContext().getResourceMap(SwarmProblemSimulatorView.class);


    public GridDSLabel(PheremoneDSN node){
        this.node=node;
        setBorder(normalBorder);
        this.setOpaque(true);
        this.setBackground(Color.white);
        this.setToolTipText(" ");
    }

    protected Color getBGColor(){
        int value = node.getPheremoneLevel()<1 ? 255 : (int) (255 * ( 1 / (node.getPheremoneLevel()+.01)));
        //int value = (int)(255*(1-Math.pow(node.getPheremoneLevel()+5, 2)/(Math.pow(node.getPheremoneLevel()+5, 2)+Math.pow(0+5,2))));
        return new Color(value,value,value);
    }

    @Override
    public void paintComponent(Graphics g){
        setBackground(getBGColor());
        if (node.hasAnt()){
            this.setText("x"+node.getAntCount());
        } else if (node.hasFood()){
            setText("F:"+node.getFood(0));
        } else setText("");

        if (node.hasFood()){
            this.setBorder(foodBorder);
        }
        else this.setBorder(normalBorder);

        if (getBGColor().getRGBComponents(null)[0]<(.4)){
            setForeground(Color.white);
        }
        else setForeground(Color.black);

        if (node.hasAnt()) {
            if (this.getSize().getHeight()>24) setIcon(resourceMap.getIcon("antIcon"));
            else setIcon(resourceMap.getIcon("antIcon.sm"));
        }
        else setIcon(null);
        super.paintComponent(g);
    }

    @Override
    public JToolTip createToolTip(){
        setToolTipText("Ant Count:"+node.getAntCount()+"/"+node.getMaxAnts()+"\nPheremone:"+node.getPheremoneLevel()+"\nFood:"+node.getFood(0)+"\nLocation: "+node.getLabel().toString());
        return super.createToolTip();
    }


}
