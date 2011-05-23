/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author cableray
 */
public class GridLayoutDSView extends JPanel {

     protected TiledDiscreteSpace space;
     protected int scale=30, minscale=16;
     protected Vector<Ant> ants;
     public final long seed;

    public GridLayoutDSView(TiledDiscreteSpace space){
        super(new GridLayout(space.getXwidth(), space.getYwidth()));
        this.seed=1337;
        this.space=space;
        initComponents();

    }

    public GridLayoutDSView(int xWidth, int yWidth, int ants, long seed){
        this(xWidth,yWidth,ants,"TwoChoiceAnt",seed,false);
    }
    public GridLayoutDSView(int xWidth, int yWidth, int ants, long seed, boolean wrap){
        this(xWidth,yWidth,ants,"TwoChoiceAnt",seed,wrap);
    }
    public GridLayoutDSView(int xWidth, int yWidth, int ants, String antType, long seed ,boolean wrap)
      {
        super(new GridLayout(xWidth,yWidth));
        this.seed=seed;
        this.space=new TiledDiscreteSpace(new PheremoneDSN());
        space.generate(xWidth, yWidth);
        ((PheremoneDSN)space.getOrigin()).setMaxAnts(ants);
        ((PheremoneDSN)space.getNode(xWidth-1, yWidth-1)).setMaxAnts(ants);
        this.ants = new Vector<Ant>(ants);
        if (antType.equals("TwoChoiceAnt")){
            for (int i=0; i<ants; i++){
                this.ants.add(new TwoChoiceAnt((PheremoneDSN)space.getOrigin(), new Random((seed)*i)));
            }
        }
        else {
            for (int i=0; i<ants; i++){
                this.ants.add(new NChoiceAnt((PheremoneDSN)space.getOrigin(), new Random((seed)*i)));
            }
        }
        if (wrap) space.wrapEdges();
        initComponents();
      }



    protected void initComponents(){
        for (int j=space.getYwidth()-1; j>=0; j--){
            for (int i=0; i<space.getXwidth();i++){
                GridDSLabel label = new GridDSLabel((PheremoneDSN)space.getNode(i, j));
                //label.setText(""+i+","+j);
                add(label);
            }
        }
    }

    public void addMouseListenerToChildren(MouseListener l){
        for ( Component child : this.getComponents())
          {
              if ( child instanceof GridDSLabel)
                {
                  GridDSLabel label = (GridDSLabel) child;
                  label.addMouseListener(l);

                }
          }
    }


    public void setScale(int scale)
      {
        this.scale = scale;
      }

    public Vector<Ant> getAnts(){
        return ants;
    }


    public TiledDiscreteSpace getSpace()
      {
        return space;
      }

    public void setMaxAnts(int max){
        for ( DiscreteSpaceNode node : space.getSpace().values())
          {
            if (!(node==space.getOrigin()||node==space.getNode(space.getXwidth()-1,space.getYwidth()-1))){
                ((PheremoneDSN)node).setMaxAnts(max);
            }
          }
    }





    @Override
    public Dimension getPreferredSize(){
        return new Dimension(scale*space.getXwidth(), scale*space.getYwidth());
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(minscale*space.getXwidth(), minscale*space.getYwidth());
    }

}
