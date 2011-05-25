/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Graphics_Manager;

import Level_Design.TileMap;
import Sprite.Sprite;
import Test.offsetManager;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Vegard
 */
public class SelectionManager implements MouseListener, MouseMotionListener {
    private int selectionX;
    private int selectionX2;
    private int selectionY;
    private int selectionY2;
    private boolean selecting;
    private TileMap map;
    private double angle;

    public SelectionManager(Window window, TileMap map) {
        window.addMouseListener(this);
        window.addMouseMotionListener(this);
        this.map = map;
        selecting = false;
        angle = 0;
    }

    public double getAngle() {
        return angle;
    }

    public int getSelectionX() {
        return selectionX;
    }

    public int getSelectionX2() {
        return selectionX2;
    }

    public int getSelectionY() {
        return selectionY;
    }

    public int getSelectionY2() {
        return selectionY2;
    }

    public boolean isSelecting() {
        return selecting;
    }

    public void mouseClicked(MouseEvent e) {
        /*System.out.println("Player x: "+map.getPlayer().getX());
        System.out.println("Player y: "+map.getPlayer().getY());        
        System.out.println("Mouse x: "+e.getX());
        System.out.println("Mouse y: "+e.getY());
        System.out.println("Mouse x on screen: "+e.getXOnScreen());
        System.out.println("Mouse y on screen: "+e.getYOnScreen());
        System.out.println(Math.atan((Math.round(map.getPlayer().getY()) - e.getY())/(Math.round(map.getPlayer().getY())-e.getX() )));
         */
        int offsetX = Math.abs(offsetManager.getOffsetX());
        int offsetY = Math.abs(offsetManager.gettOffsetY());
       
        System.out.println("PlayerX: "+map.getPlayer().getX() + " PlayerY: "+map.getPlayer().getY()+"\nMouseX: "+(e.getX()+offsetX)+" MouseY: "+(e.getY()+offsetY));
        //moveTo(map.getPlayer(), e.getX(), e.getY());
        //tryGetAngle(map.getPlayer(), e.getX()+offsetX, e.getY()+offsetY);
        this.angle = getAngle(Math.round(map.getPlayer().getX()), Math.round(map.getPlayer().getY()), e.getX()+offsetX, e.getY()+offsetY);
    }

    public void tryGetAngle(Sprite sprite, int toX, int toY) {
        int x1 = Math.round(sprite.getX());
        int y1 = Math.round(sprite.getY());

        int x2 = toX;
        int y2 = toY;

        int X = Math.max(x1, x2) - Math.min(x1, x2);
        int Y = Math.max(y1, y2) - Math.min(y1, y2);

        int dx = x2 - x1;
        int dy = y2 - y1;

        double r = Math.atan2(dy, dx);
        if(r < 0) r+= Math.PI * 2;

        
        this.angle = (r*180)/Math.PI;



  
    }

    public double getAngle(int x1, int y1, int x2, int y2) {
        double opp;
        double adj;
        double ang1;

        // Calculate vector differences

        opp = y1 - y2;
        adj = x1 - x2;

        if(x1==x2 && y1 == y2) return -1;

        if(adj==0) {
            if(opp>=0) {
                return 0;
            }
            else {
                return 180;
            }
        } else {
            ang1 = (Math.atan(opp/adj)) * 180/Math.PI;
        }

        if(x1>=x2) {
            ang1=90-ang1;
        } else {
            ang1=270-ang1;
        }
        return ang1;
    }

   
    public void mousePressed(MouseEvent e) {
        selectionX = e.getXOnScreen();
        selectionY = e.getYOnScreen();
    }

    public void mouseReleased(MouseEvent e) {
        selecting = false;
    }

    public void mouseEntered(MouseEvent e) {
        // Do nothing yet
    }

    public void mouseExited(MouseEvent e) {
        // Do nothing yet
    }

    public void mouseDragged(MouseEvent e) {
        selecting = true;
        selectionX2 = e.getXOnScreen() - selectionX;
        selectionY2 = e.getYOnScreen() - selectionY;
    }

    public void mouseMoved(MouseEvent e) {
        // Do nothing yet
    }


}
