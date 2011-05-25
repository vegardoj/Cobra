

package Level_Design;

import Sprite.Creature.Circle;
import Sprite.Creature.Creature;
import Sprite.SelectionManager;
import Sprite.Sprite;
import Test.offsetManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
    The TileMapRenderer class draws a TileMap on the screen.
    It draws all tiles, sprites, and an optional background image
    centered around the position of the player.

    <p>If the width of background image is smaller the width of
    the tile map, the background image will appear to move
    slowly, creating a parallax background effect.

    <p>Also, three static methods are provided to convert pixels
    to tile positions, and vice-versa.

    <p>This TileMapRender uses a tile size of 64.
*/
public class TileMapRenderer {

    private static final int TILE_SIZE = 64;
    // the size in bits of the tile
    // Math.pow(2, TILE_SIZE_BITS) == TILE_SIZE
    private static final int TILE_SIZE_BITS = 6;

    private Image background;
    private SelectionManager sManager;
    private SelectionManager sManager2;
    
    /**
        Converts a pixel position to a tile position.
    */
    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


    /**
        Converts a pixel position to a tile position.
    */
    public static int pixelsToTiles(int pixels) {
        // use shifting to get correct values for negative pixels
        return pixels >> TILE_SIZE_BITS;

        // or, for tile sizes that aren't a power of two,
        // use the floor function:
        //return (int)Math.floor((float)pixels / TILE_SIZE);
    }


    /**
        Converts a tile position to a pixel position.
    */
    public static int tilesToPixels(int numTiles) {
        // no real reason to use shifting here.
        // it's slighty faster, but doesn't add up to much
        // on modern processors.
        return numTiles << TILE_SIZE_BITS;

        // use this if the tile size isn't a power of 2:
        //return numTiles * TILE_SIZE;
    }


    /**
        Sets the background to draw.
    */
    public void setBackground(Image background) {
        this.background = background;
    }


    /**
        Draws the specified TileMap.
    */
    public void draw(Graphics2D g, TileMap map, int screenWidth, int screenHeight) {        
        Sprite player = map.getPlayer();
        int mapWidth = tilesToPixels(map.getWidth());
        int mapHeight = tilesToPixels(map.getHeight());

        // get the scrolling position of the map
        // based on player's position
        int offsetX = screenWidth / 2 -
            Math.round(player.getX()) - TILE_SIZE;
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, screenWidth - mapWidth);
        offsetManager.setOffsetX(offsetX);
        // get the y offset to draw all sprites and tiles       
        int offsetY = screenHeight / 2 - Math.round(player.getY()) - TILE_SIZE;
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, screenHeight - mapHeight);
        offsetManager.setOffsetY(offsetY);
        //System.out.println(offsetX+ " "+offsetY);
        // draw black background, if needed
        sManager = new SelectionManager(player, offsetX, offsetY);
        if (background == null ||
            screenHeight > background.getHeight(null))
        {
            g.setColor(Color.black);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }

        // draw parallax background image
        if (background != null) {
            int x = offsetX *
                (screenWidth - background.getWidth(null)) /
                (screenWidth - mapWidth);
            
            int y = offsetY * (screenHeight - background.getHeight(null)) / (screenHeight - mapHeight);

            g.drawImage(background, x, y, null);
        }

        // draw the visible tiles
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX +
            pixelsToTiles(screenWidth) + 1;
        for (int y=0; y<map.getHeight(); y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {
                Image image = map.getTile(x, y);
                BufferedImage image2 = map.getSpecialTile2(x, y);
                
                if (image != null) {
                    
                    g.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
                if(image2 != null) {
                    
                    g.drawImage(image2, tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
                }
                /*BufferedImage specialImage2 = map.getSpecialTile2(x, y);
                Image specialImage = map.getSpecialTile(x, y);
                if(specialImage != null) {                    
                    g.drawImage(specialImage, tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);                    
                }
                if(specialImage2 != null) {
                    g.drawImage(specialImage2, tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
                }*/
            }
        }

        for (int y=0; y<map.getHeight(); y++) {
            for (int x=firstTileX; x <= lastTileX; x++) {
                BufferedImage image2 = map.getSpecialTile2(x, y);
                if(image2 != null) {

                    g.drawImage(image2, tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
                }
            }
        }

        for(int i=0; i<map.getSpecialTiles().size(); i++) {
            SpecialTile s = (SpecialTile)map.getSpecialTiles().get(i);
            g.drawImage(s.getImage(), s.getX() + offsetX, s.getY() + offsetY, null);
        }
        
        
        BufferedImage specialImage = map.getSpecialTile2(2, 10);
        //System.out.println(specialImage.getHeight());
        //g.drawImage(specialImage, tilesToPixels(2) + offsetX, tilesToPixels(10) + offsetY, null);
        player.setBounds(new Circle(Math.round(player.getX()) + offsetX + 30, Math.round(player.getY()) + offsetY + 54, 18*2, 18*2, 18));
        sManager.draw(g);
        // draw circle around player
        //g.setColor(Color.green);
        //g.drawOval(Math.round(player.getX()) + offsetX + 30, Math.round(player.getY()) + offsetY + 54, 18*2, 18*2);
        

        // draw player
        g.drawImage(player.getImage(),
            Math.round(player.getX()) + offsetX,
            Math.round(player.getY()) + offsetY,
            null);
       
        // draw sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite sprite = (Sprite)i.next();
            sManager2 = new SelectionManager(sprite, offsetX, offsetY);
            sprite.setBounds(new Circle(Math.round(sprite.getX()) + offsetX + 30, Math.round(sprite.getY()) + offsetY + 54, 18*2, 18*2, 18));
            sManager2.draw(g);
            int x = Math.round(sprite.getX()) + offsetX;
            int y = Math.round(sprite.getY()) + offsetY;
            g.drawImage(sprite.getImage(), x, y, null);
            

            // wake up the creature when it's on screen
            if (sprite instanceof Creature &&
                x >= 0 && x < screenWidth)
            {
                //((Creature)sprite).wakeUp();
            }
        }
    }

}

