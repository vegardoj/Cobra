
package Level_Design;

import Sprite.Sprite;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  The TileMap class contains the data for a tile-based
 *  map, including Sprites. Each tile is a reference to an
 *  Image. Of course, Images are used multiple times in the tile
 *  map.
 * @author Vegard
 */
public class TileMap {
    private ArrayList specialTiles3;
    private Image[][] tiles;
    private Image[][] specialTiles;
    private BufferedImage[][] specialTiles2;
    private LinkedList sprites;
    private Sprite player;

    public TileMap(int width, int height) {
        tiles = new Image[width][height];
        specialTiles = new Image[width][height];
        specialTiles2 = new BufferedImage[width][height];
        specialTiles3 = new ArrayList();
        sprites = new LinkedList();
    }

    /**
     * Gets the width of the TileMap (number of tiles across).
     * @return Width of the TileMap.
     */
    public int getWidth() {
        return tiles.length;
    }

    /**
     * Gets the height of the TileMap (number of tiles down).
     * @return Height of the TileMap.
     */
    public int getHeight() {
        return tiles[0].length;
    }

    /**
     * Gets the tile at the specified location. Returns null if the tile doesn't exist
     * @param x
     * @param y
     * @return Tile at the specified location.
     */
    public Image getTile(int x, int y) {
        if(x < 0 || x >= getWidth() || y < 0 && y >= getHeight()) {            
            return null;
        } else {            
            return tiles[x][y];
        }
    }

    /**
     * Sets the tile at the specified location.
     * @param x
     * @param y
     * @param tile
     */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }

    /**
     * Gets the tile at the specified location. Returns null if the tile doesn't exist
     * @param x
     * @param y
     * @return Tile at the specified location.
     */
    public Image getSpecialTile(int x, int y) {
        if(x < 0 || x >= getWidth() || y < 0 && y >= getHeight()) {
            return null;
        } else {
            return specialTiles[x][y];
        }
    }

    public BufferedImage getSpecialTile2(int x, int y) {
        if(x < 0 || x >= getWidth() || y < 0 && y >= getHeight()) {
            return null;
        } else {
            return specialTiles2[x][y];
        }
    }

    public ArrayList getSpecialTiles() {
        return specialTiles3;
    }

    
    /**
     * Sets the tile at the specified location.
     * @param x
     * @param y
     * @param tile
     */
    public void setSpecialTile(int x, int y, Image tile) {
        specialTiles[x][y] = tile;
    }

    public void setSpecialTile2(int x, int y, BufferedImage tile) {
        specialTiles2[x][y] = tile;
    }

    public void setSpecialTile3(SpecialTile tile) {
        specialTiles3.add(tile);
    }


    /**
     * Gets the player sprite.
     * @return Player sprite.
     */
    public Sprite getPlayer() {
        return player;
    }

    /**
     * Sets the player sprite.
     * @param player
     */
    public void setPlayer(Sprite player) {
        this.player = player;
    }

    /**
     * Adds a sprite object to the map.
     * @param sprite
     */
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
     * Removes a sprite object from the map.
     * @param sprite
     */
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
     * Gets an Iterator of all the sprites in the map,
     * excluding the player sprite.
     * @return
     */
    public Iterator getSprites() {
        return sprites.iterator();
    }
}
