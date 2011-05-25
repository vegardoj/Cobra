package Sprite;

import Sprite.Creature.Circle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Vegard
 */
public class Sprite {

    protected Animation anim;
    private Circle bounds;
    
    // position, pixels
    private float x;
    private float y;

    // velocity, pixels per millisecond
    private float dx;
    private float dy;

    /**
     * Creates a new Sprite object with the given animation.
     * @param anim
     */
    public Sprite(Animation anim) {
        this.anim = anim;
        bounds = new Circle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight(), 18);
    }

    /**
        Updates this Sprite's Animation and its position based
        on the velocity.
    */
    public void update(long elapsedTime, boolean collision) {
        if(!collision) {
            x += dx * elapsedTime;
            y += dy * elapsedTime;
        }               
        anim.update(elapsedTime);
    }

    /**
     * Gets the sprite's bounds.
     * @return bounds
     */
    public Circle getBounds() {
        return bounds;
    }

    public void setBounds(Circle newBounds) {
        this.bounds = newBounds;
    }

    
    public void setCircle(Circle circle) {
        this.bounds = circle;
    }

    /**
     * Gets the sprite's current x position.
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the sprite's current y position.
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the sprite's current x position.
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the sprite's current y position.
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the sprite's width, based on the current image.
     * @return Width
     */
    public int getWidth() {
        return anim.getImage().getWidth(null);
    }

    /**
     * Gets the sprite's Height, based on the current image.
     * @return Height
     */
    public int getHeight() {
        return anim.getImage().getHeight(null);
    }

    /**
        Gets the horizontal velocity of this Sprite in pixels
        per millisecond.
    */
    public float getVelocityX() {
        return dx;
    }

    /**
        Gets the vertical velocity of this Sprite in pixels
        per millisecond.
    */
    public float getVelocityY() {
        return dy;
    }

    /**
        Sets the horizontal velocity of this Sprite in pixels
        per millisecond.
    */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
        Sets the vertical velocity of this Sprite in pixels
        per millisecond.
    */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**
     * Gets the sprite's current image.
     * @return Current image.
     */
    public BufferedImage getImage() {
        return anim.getImage();
    }

    /**
        Clones this Sprite. Does not clone position or velocity
        info.
    */
    
    public Object clone() {
        return new Sprite(anim);
    }

}
