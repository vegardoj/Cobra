/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Collision;

import Graphics_Manager.ScreenManager;
import Level_Design.SpecialTile;
import Level_Design.TileMap;
import Level_Design.TileMapRenderer;
import Sprite.Sprite;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Vegard
 */
public class CollisionManager {
    private TileMap map;
    private ScreenManager manager;

    public CollisionManager(ScreenManager manager, TileMap map) {
        this.map = map;
        this.manager = manager;
    }

    public boolean isTileCollision(Sprite sprite, int newX, int newY) {
        for(int i=0; i<map.getSpecialTiles().size(); i++) {
            //System.out.println("TESTING SDL");
            //if(SDL_CollidePixel(sprite, Math.round(sprite.getX()), Math.round(sprite.getY()), ((SpecialTile)map.getSpecialTiles().get(i)).getImage(), ((SpecialTile)map.getSpecialTiles().get(i)).getX(), ((SpecialTile)map.getSpecialTiles().get(i)).getY())) {
            if(SDL_CollidePixel(sprite, newX, newY, ((SpecialTile)map.getSpecialTiles().get(i)).getImage(), ((SpecialTile)map.getSpecialTiles().get(i)).getX(), ((SpecialTile)map.getSpecialTiles().get(i)).getY())) {
                //sprite.setY(20);
                return true;
            }            
        }
        return false;
    }

    public boolean circlesColliding(int x1, int y1, int radius1, int x2, int y2, int radius2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int radii = radius1 + radius2;
        //System.out.println("x1: "+x1+" y1: "+y1+" r1: "+radius1+"\nx2: "+x2+" y2: "+y2+" r2: "+radius2);
        if((dx*dx) + (dy*dy) < radii * radii) return true;
        return false;
    }

    public boolean isTileCollision2(Sprite sprite) {
        float fromX = (sprite.getX());
        float fromY = (sprite.getY());
        float toX = (sprite.getX());
        float toY = (sprite.getY());
        for(int i=0; i<map.getSpecialTiles().size(); i++) {
            //System.out.println("TESTING SDL");
            if(SDL_CollidePixel(sprite, Math.round(sprite.getX()), Math.round(sprite.getY()), ((SpecialTile)map.getSpecialTiles().get(i)).getImage(), ((SpecialTile)map.getSpecialTiles().get(i)).getX(), ((SpecialTile)map.getSpecialTiles().get(i)).getY())) {
                //sprite.setX(20);
                //sprite.setY(20);
                return true;
            }
        }
        // get the tile locations
        int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
        int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
        int toTileX = TileMapRenderer.pixelsToTiles(
            toX + sprite.getWidth() - 1);
        int toTileY = TileMapRenderer.pixelsToTiles(
            toY + sprite.getHeight() - 1);
         //check each tile for a collision
        for (int x=fromTileX; x<=toTileX; x++) {
            for (int y=fromTileY; y<=toTileY; y++) {
                if (x < 0 || x >= map.getWidth() ||
                    //map.getSpecialTile(x, y) != null ||
                    map.getSpecialTile2(x, y) != null)
                {
                    int offsetY = manager.getHeight() / 2 - Math.round(sprite.getY()) - 64;
                    offsetY = Math.min(offsetY, 0);
                    offsetY = Math.max(offsetY, manager.getHeight() - map.getHeight());
                    

                    return SDL_CollidePixel(sprite, Math.round(sprite.getX()), Math.round(sprite.getY()), map.getSpecialTile2(x, y), TileMapRenderer.tilesToPixels(x), TileMapRenderer.tilesToPixels(y));
                    //perfectPixelCollision(sprite, map.getSpecialTile2(x, y), TileMapRenderer.tilesToPixels(x), TileMapRenderer.tilesToPixels(y));
                    //return isPixelCollide(sprite, (double)TileMapRenderer.tilesToPixels(x), (double)(TileMapRenderer.tilesToPixels(y)), map.getSpecialTile2(x, y));
                    
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /*Now lets make the bouding box for which we check for a pixel collision*/

	/*To get the bounding box we do
            Ax1,Ay1______________
                |                |
                |                |
                |                |
                |    Bx1,By1____________
                |        |       |      |
                |        |       |      |
                |________|_______|      |
                         |    Ax2,Ay2   |
                         |              |
                         |              |
                         |___________Bx2,By2

	To find that overlap we find the biggest left hand cordinate
	AND the smallest right hand co-ordinate

	To find it for y we do the biggest top y value
	AND the smallest bottom y value

	Therefore the overlap here is Bx1,By1 --> Ax2,Ay2

	Remember	Ax2 = Ax1 + SA->w
			Bx2 = Bx1 + SB->w

			Ay2 = Ay1 + SA->h
			By2 = By1 + SB->h
	*/

    public boolean SDL_CollidePixel(Sprite SA, int ax, int ay, BufferedImage img, int bx, int by) {
        int ax1 = ax + SA.getWidth() - 1;
        int ay1 = ay + SA.getHeight() - 1;

        int bx1 = bx + img.getWidth() - 1;
        int by1 = by + img.getHeight() - 1;

        if((bx1 < ax) || (ax1 < bx)) return false;
        if((by1 < ay) || (ay1 < by)) return false;

        int ax2 = ax1 + SA.getWidth();
        int bx2 = bx1 + img.getWidth();

        int ay2 = ay1 + SA.getHeight();
        int by2 = by1 + img.getHeight();

        int xstart = Math.max(ax, bx);
        int xend = Math.min(ax1, bx1);

        int ystart = Math.max(ay, by);
        int yend = Math.min(ay1, by1);
//        System.out.println("ystart: "+ystart+" - yend: "+yend);
//        System.out.println("xstart: "+xstart+" - xend: "+xend);
        for(int y = ystart; y <= yend; y++) {
            for(int x = xstart; x <= xend; x++) {                
                
                if(getPixelData(map.getPlayer().getImage(), x-ax, y-ay)[0] != 0 &&
                        getPixelData(img, x-bx, y-by)[0] != 0) {
                    System.out.println("Checking player image at: "+(x-ax)+" , "+(y-ay));
                    System.out.println("Checking object image at: "+(x-bx)+" , "+(y-by));
                    return true;
                }
            }
        }
        return false;

    }


    public void perfectPixelCollision(Sprite sprite, BufferedImage image, int x, int y) {
        int Ax1 = Math.round(sprite.getX());
        int Ay1 = Math.round(sprite.getY());

        //sout bare x og y
        int Bx1 = x;
        int By1 = y;
        //int Bx1 = TileMapRenderer.tilesToPixels(x);
        //int By1 = TileMapRenderer.tilesToPixels(y);
        System.out.println(TileMapRenderer.tilesToPixels(1));
        

        int Ax2 = Ax1 + sprite.getWidth();
        int Bx2 = Bx1 + image.getWidth();

        int Ay2 = Ay1 + sprite.getHeight();
        int By2 = By1 + image.getHeight();

        //System.out.println("Bx1: "+Bx1+" By1: "+By1+" Ax2: "+Ax2+" Ay2: "+Ay2);

        for(int i=Ax2 - Bx1; i<sprite.getWidth()-1; i++) {
            for(int j=Ay2 - By1; i<sprite.getHeight()-1; j++) {
                //System.out.println("i: "+i+" j: "+j);
                int[] test = getPixelData(image, i, j);
                System.out.println(test[0]);
            }
        }      


        
    }

    public boolean isPixelCollide(Sprite sprite,
            double x2, double y2, BufferedImage image2) {

        double x1 = sprite.getX();
        double y1 = sprite.getY();

        BufferedImage image1 = sprite.getImage();

        // initialization
        double width1 = x1 + image1.getWidth() -1,
                height1 = y1 + image1.getHeight() -1,
                width2 = x2 + image2.getWidth() -1,
                height2 = y2 + image2.getHeight() -1;

        int xstart = (int) Math.max(x1, x2),
                ystart = (int) Math.max(y1, y2),
                xend   = (int) Math.min(width1, width2),
                yend   = (int) Math.min(height1, height2);

        // intersection rect
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend -xstart);

        for(int y=1; y < toty - 1; y++) {
            int ny = Math.abs(ystart - (int)y1) + y;
            int ny1 = Math.abs(ystart - (int)y2) + y;

            for(int x=1; x < totx - 1; x++) {
                int nx = Math.abs(xstart - (int)x1) + x;
                int nx1 = Math.abs(xstart - (int)x2) + x;

                try{
                    if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) &&
                            ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
                        // collide!!
                        //System.out.println("TRUE :D");
                        return true;
                    }
                    else if(getPixelData(image1, nx, ny)[0] != 0 && getPixelData(image2, nx1, ny1)[0] != 0) {
                        System.out.println("WTF?");
                        return true;
                    }
                } catch(Exception e) {
                    
                }
            }
        }
        return false;

    }

    private int[] getPixelData(BufferedImage image, int x, int y) {
        int[] rgb = new int[4];
        Color color = new Color(image.getRGB(x, y));
        rgb[0] = color.getRed();
        rgb[1] = color.getGreen();
        rgb[2] = color.getBlue();

        return rgb;
        
    }

    public static void main(String[] args) {
        System.out.println(TileMapRenderer.tilesToPixels(3));
        
    }

}
