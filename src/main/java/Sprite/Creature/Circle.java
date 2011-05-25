/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sprite.Creature;

/**
 *
 * @author Vegard
 */
public class Circle {
    private int radius;
    private int x;
    private int y;
    private int width;
    private int height;

    public Circle(int x, int y, int width, int height, int radius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
