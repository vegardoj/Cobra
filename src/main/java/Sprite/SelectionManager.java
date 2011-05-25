/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sprite;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Vegard
 */
public class SelectionManager {
    Sprite player;
    int offsetX;
    int offsetY;

    public SelectionManager(Sprite sprite, int offsetX, int offsetY) {
        player = sprite;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.green);
        g.drawOval(Math.round(player.getX()) + offsetX + 30, Math.round(player.getY()) + offsetY + 54, 18*2, 18*2);
        
    }

}
