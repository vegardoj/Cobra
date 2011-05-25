/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import Collision.CollisionManager;
import Core.GameAction;
import Core.InputManager;
import Graphics_Manager.ScreenManager;
import Graphics_Manager.SelectionManager;
import Level_Design.ResourceManager;
import Level_Design.TileMap;
import Level_Design.TileMapRenderer;
import Sprite.Creature.Creature;
import Sprite.Creature.Human.Warrior.Warrior;
import Sprite.Sprite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 *
 * @author Vegard
 */
public class TestTileMap {

    private boolean running = true;
    private boolean collision = false;
    private ScreenManager screen;
    private SelectionManager selectionManager;
    private GameAction exit;
    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction moveDown;
    private GameAction moveUp;
    private GameAction run;
    private GameAction attack;
    private GameAction die;
    private InputManager inputManager;
    private TileMapRenderer renderer;
    private ResourceManager resourceManager;
    private TileMap map;
    private CollisionManager collisionManager;
    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode(1280, 800, 32, 0),
        new DisplayMode(800, 600, 32, 0),
        new DisplayMode(800, 600, 24, 0),
        new DisplayMode(800, 600, 16, 0),
        new DisplayMode(640, 480, 32, 0),
        new DisplayMode(640, 480, 24, 0),
        new DisplayMode(640, 480, 16, 0)
    };
    public static void main(String[] args) {
        new TestTileMap().run();
    }

    public void run() {

        screen = new ScreenManager();

        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image cursorImg = toolkit.getImage(getClass().getResource("/Graphics_Manager/cursor.gif"));
            System.out.println(getClass().getResource("/Graphics_Manager/cursor2.gif"));
            Point hotSpot = new Point(0,0);
            Cursor cursor = toolkit.createCustomCursor(cursorImg, hotSpot, "Cursor");
            DisplayMode displayMode = screen.findFirstCompatibleMode(POSSIBLE_MODES);
            screen.setFullScreen(displayMode);
            

            
            resourceManager = new ResourceManager(screen.getFullScreenWindow().getGraphicsConfiguration());
            
            initInput();
            selectionManager = new SelectionManager(screen.getFullScreenWindow(), map);
            collisionManager = new CollisionManager(screen, map);
            //System.out.println("TEST3");

            screen.getFullScreenWindow().setCursor(cursor);
            animationLoop();


        }
        finally {
             screen.restoreScreen();
        }
    }

    public void initInput() {
        Window window = screen.getFullScreenWindow();
        
        exit = new GameAction("exit");
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveUp = new GameAction("moveUp");
        moveDown = new GameAction("moveDown");
        run = new GameAction("run", GameAction.DETECT_INITAL_PRESS_ONLY);
        attack = new GameAction("attack", GameAction.DETECT_INITAL_PRESS_ONLY);
        die = new GameAction("die", GameAction.DETECT_INITAL_PRESS_ONLY);
        inputManager = new InputManager(window);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
        inputManager.mapToKey(moveRight, KeyEvent.VK_D);
        inputManager.mapToKey(moveDown, KeyEvent.VK_S);
        inputManager.mapToKey(moveUp, KeyEvent.VK_W);
        inputManager.mapToKey(run, KeyEvent.VK_R);
        inputManager.mapToKey(attack, KeyEvent.VK_SPACE);
        inputManager.mapToKey(die, KeyEvent.VK_X);
        // load resources
        
        renderer = new TileMapRenderer();
        
        renderer.setBackground(resourceManager.loadImage("background.png"));
        
        // load first map
        map = resourceManager.loadNextMap();
        
        
    }

    public void animationLoop() {
        Creature player = (Creature)map.getPlayer();
        long startTime = System.currentTimeMillis();
        long currTime = startTime;

        while (running) {
            long elapsedTime =
                System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            checkInput();
          
            int newX = Math.round(player.getX() + player.getVelocityX() * elapsedTime);
            int newY = Math.round(player.getY() + player.getVelocityY() * elapsedTime);

            collision = collisionManager.isTileCollision(player, newX, newY);
            getSpriteCollision(player, newX, newY);
            
            
           
            player.update(elapsedTime, collision);
            
            
            //System.out.println("Player x: "+player.getX()+" Player y: "+player.getY());
            // draw and update screen
            Graphics2D g = screen.getGraphics();
            //System.out.println("TEST 5");
            draw(g);
            g.dispose();

            screen.update();

            


            // take a nap
            /*try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }*/
        }
    }

    public void getSpriteCollision(Sprite sprite, int newX, int newY) {

        // run through the list of Sprites
        Iterator i = map.getSprites();
        while (i.hasNext()) {
            Sprite otherSprite = (Sprite)i.next();
            if (isCollision(sprite, newX, newY, otherSprite)) {
                // collision found, return the Sprite
                collision = true;
            }
        }

        // no collision found
        
    }

    public boolean isCollision(Sprite s1, int newX, int newY, Sprite s2) {
        // if the Sprites are the same, return false
        if (s1 == s2) {
            return false;
        }

        // if one of the Sprites is a dead Creature, return false
        if (s1 instanceof Creature && !((Creature)s1).isAlive()) {
            return false;
        }
        if (s2 instanceof Creature && !((Creature)s2).isAlive()) {
            return false;
        }

        int alterX = Math.round(s1.getX()) - newX;
        int alterY = Math.round(s1.getY()) - newY;
        return collisionManager.circlesColliding(s1.getBounds().getX() - alterX, s1.getBounds().getY() - alterY, s1.getBounds().getRadius(), s2.getBounds().getX(), s2.getBounds().getY(), s2.getBounds().getRadius());
    }

    private void checkInput() {
        if(exit.isPressed()) {
            running = false;
        }
        Warrior player = (Warrior)map.getPlayer();
        if (player.isAlive()) {
            float velocityX = 0;
            float velocityY = 0;
            if(!player.isAttacking()) {
                if (moveLeft.isPressed()) {
                    velocityX-=player.getMoveSpeed();
                }
                if (moveRight.isPressed()) {
                    velocityX+=player.getMoveSpeed();
                }
                if (moveDown.isPressed()) {
                    velocityY+=player.getMoveSpeed();
                }
                if (moveUp.isPressed()) {
                    velocityY-=player.getMoveSpeed();
                }
            }
            

            if(run.isPressed()) {                
                player.swapRunning();                
            }
            
            if(attack.isPressed()) {
                if(!player.isAttacking()) {                    
                    player.swapAttacking();
                }
                
                //player.setState(Warrior.STATE_DYING);
            }

            if(die.isPressed()) {
                player.setState(6);
            }


            player.setVelocityY(velocityY);
            player.setVelocityX(velocityX);
        }        
        
    }

    
   

    public void draw(Graphics2D g) {        
        renderer.draw(g, map,
            screen.getWidth(), screen.getHeight());
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(selectionManager.getAngle()), screen.getWidth()-screen.getWidth()/2, screen.getHeight()-screen.getHeight()/2);
        if(collision) {
            //g.drawString("COLLISION", screen.getWidth()-screen.getWidth()/2, screen.getHeight()-screen.getHeight()/2);
        } else {
            //g.drawString("NOT COLLISION", screen.getWidth()-screen.getWidth()/2, screen.getHeight()-screen.getHeight()/2);
        }
       
        if(selectionManager.isSelecting()) {
            
            g.setColor(Color.BLUE);
            //g.drawRect(selectionX, selectionY, selectionX2, selectionY2);
            g.draw3DRect(selectionManager.getSelectionX(), selectionManager.getSelectionY(), selectionManager.getSelectionX2(), selectionManager.getSelectionY2(), true);
        }

    }


}
