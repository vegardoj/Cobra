/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Level_Design;


import Sprite.Creature.Human.Warrior.Warrior;
import Sprite.Creature.Human.Warrior.init_warrior_animation;
import Sprite.Sprite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Vegard
 */
public class ResourceManager {
    private ArrayList tiles;
    private int currentMap = 0;
    private int numberOfMaps = 1;
    private GraphicsConfiguration gc;

    private String maplocation = "/Level_Design/Levels/";

    private Sprite playerSprite;
    private Sprite testSprite;


    /**
     * Creates a new ResourceManager with the specified GraphicsConfiguration
     * @param gc
     */
    public ResourceManager(GraphicsConfiguration gc) {
        this.gc = gc;
        loadTileImages();
        loadCreatureSprites();
    }

    public Image loadImage(String fileName) {
        return new ImageIcon(getClass().getResource(maplocation+"/Tiles/"+fileName)).getImage();
    }

    public BufferedImage loadBufferedImage(String fileName) {
        BufferedImage test = null;
        try{
            test = ImageIO.read(getClass().getResource(maplocation+"/Tiles/"+fileName));
        }catch(IOException e) {      
        }
        return test;
        
    }

    private Image getScaledImage(Image image, float x, float y) {

        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate(
            (x-1) * image.getWidth(null) / 2,
            (y-1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(
            image.getWidth(null),
            image.getHeight(null),
            Transparency.BITMASK);

        // draw the transformed image
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }

    public TileMap loadNextMap() {
        TileMap map = null;
        while(map == null) {
            currentMap++;
            try{
                map = loadMap("./src/Level_Design/Levels/" + currentMap + ".txt");
            } catch (IOException e) {
                if(currentMap > numberOfMaps ) {
                    //no maps to load
                    currentMap = 0;
                    map = null;
                }
            }
        }
        return map;
    }

    private TileMap loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();
        ArrayList<SpecialTile> specialTiles = new ArrayList<SpecialTile>();
        int width = 0;
        int height = 0;       
        
        
        
        // Read every line in the textfile into the list
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while(true) {
            String line = reader.readLine();
            // no more lines to read
            if(line == null) {
                reader.close();
                break;
            }
            // Add every line except lines starting with #
            if(!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }

            else if(line.startsWith("#special"))  {
                StringTokenizer tokenizer = new StringTokenizer(line);
                String command = tokenizer.nextToken();
                String imgName = tokenizer.nextToken();
                System.out.println(imgName);
                int x = Integer.parseInt(tokenizer.nextToken());
                System.out.println(x);
                int y = Integer.parseInt(tokenizer.nextToken());
                System.out.println(y);                
                specialTiles.add(new SpecialTile(imgName, x, y));                

                
            }
        }
        height = lines.size();
        TileMap newMap = new TileMap(width, height);

        for(int i=0; i<specialTiles.size(); i++) {
            newMap.setSpecialTile3(specialTiles.get(i));
        }
        // parse the lines to create a TileEngine
        
        for(int y=0; y<height; y++) {
            String line = (String)lines.get(y);
            for(int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                int tile = ch -'A';
                
                if(tile >= 0 && tile < tiles.size()) {                    
                    newMap.setTile(x, y, (Image)tiles.get(tile));
                }
            }            
        }

        for(int i=0; i<10; i++) {
            SpecialTile tile;
            int j = 64;
            if(i%2 == 0) {
                tile = new SpecialTile("bush.png", j*i, 64*10);
                j-= 40;
            } else {
                tile = new SpecialTile("bush1.png", j*i, 64*10);
                j+= 40;
                
            }
            
            newMap.setSpecialTile3(tile);
        }
        /*
        newMap.setSpecialTile2(2, 10, loadBufferedImage("bush.png"));
        newMap.setSpecialTile2(3, 10, loadBufferedImage("bush.png"));
        newMap.setSpecialTile2(4, 10, loadBufferedImage("bush.png"));
        newMap.setSpecialTile2(5, 10, loadBufferedImage("bush.png"));
        newMap.setSpecialTile2(6, 10, loadBufferedImage("bush.png"));
        */
        newMap.setSpecialTile(0, 0, loadImage("stone.png"));
        newMap.setSpecialTile(0, 1, loadImage("stone.png"));
        newMap.setSpecialTile(0, 2, loadImage("stone.png"));
        newMap.setSpecialTile(0, 3, loadImage("stone.png"));
        newMap.setSpecialTile(0, 4, loadImage("stone.png"));
        newMap.setSpecialTile(0, 5, loadImage("stone.png"));
        newMap.setSpecialTile(0, 6, loadImage("stone.png"));
        newMap.setSpecialTile(0, 7, loadImage("stone.png"));
        newMap.setSpecialTile(0, 8, loadImage("stone.png"));
        newMap.setSpecialTile(0, 9, loadImage("stone.png"));
        newMap.setSpecialTile(0, 10, loadImage("stone.png"));

        
        

        Warrior player = (Warrior)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(TileMapRenderer.tilesToPixels(12));
        newMap.setPlayer(player);

        Warrior test = (Warrior)playerSprite.clone();
        addSprite(newMap, test, 20, 10);
        return newMap;
    }

    private void addSprite(TileMap map, Sprite hostSprite, int tileX, int tileY) {
        if(hostSprite != null) {
            // Clone the sprite from the host
            Sprite sprite = (Sprite)hostSprite.clone();

            // center the sprite
            sprite.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }

    public TileMap reloadMap() {
        try {
            return loadMap(
                maplocation+currentMap+".txt");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void loadCreatureSprites() {
        init_warrior_animation test = new init_warrior_animation();
        playerSprite = new Warrior(test.getAnimations());
    }

    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ directory
        tiles = new ArrayList();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("./src/"+maplocation+"/Tiles/"+name);
            if (!file.exists()) {
                
                break;
            }
            tiles.add(loadImage(name));
            
            ch++;
        }
    }
}
