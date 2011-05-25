/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Level_Design;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Vegard
 */
public class SpecialTile {
    private String maplocation = "/Level_Design/Levels/";
    
    private BufferedImage tile;
    private int width, height, x, y;
    private Rectangle bounds;
   

    public SpecialTile(String fileName, int x, int y) {
        this.tile = loadBufferedImage(fileName);
        this.x = x;
        this.y = y;
        
        width = tile.getWidth(null);        
        height = tile.getHeight(null);
        bounds = new Rectangle(width, height);
    }

    public BufferedImage getImage() {
        return tile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;        
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage loadBufferedImage(String fileName) {
        BufferedImage test = null;
        try{
            test = ImageIO.read(getClass().getResource(maplocation+"/Tiles/"+fileName));
        }catch(IOException e) {
        }
        return test;

    }

}
