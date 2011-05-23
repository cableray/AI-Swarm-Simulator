/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swarmproblemsimulator;

/**
 *
 * @author cableray
 */
public class Coordinate2D {
    final int x,y;


    public Coordinate2D(int x, int y)
      {
        this.x = x;
        this.y = y;
      }

    @Override
    public boolean equals(Object obj){
        if ( obj instanceof Coordinate2D)
          {
            Coordinate2D comp = (Coordinate2D) obj;
            return this.x==comp.x&&this.y==comp.y;

          }
        return false;
    }


    @Override
    public int hashCode()
      {
        int hash = 7;
        hash = 17 * hash + this.x;
        hash = 17 * hash + this.y;
        return hash;
      }

    @Override
    public String toString(){
        return "("+this.x+","+this.y+")";
    }



}
