/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Vegard
 */
public class test {
    public static BufferedImage test;

    public static void main(String[] args) {
        test x = new test();
        x.init();
        for(int i=0; i<test.getHeight(); i++) {
            for(int j=0; j<test.getWidth(); j++) {
                System.out.println(i+" , "+j);
                x.getPixelData(j, i);
            }
        }
        


        
        

    }
    private void getPixelData(int x, int y) {
        int[] rgb = new int[4];
        Color color = new Color(test.getRGB(x, y));
        rgb[0] = color.getRed();
        rgb[1] = color.getGreen();
        rgb[2] = color.getBlue();
        
        System.out.println("rgb: "+rgb[0]+" "+rgb[1]+" "+rgb[2]+"\n");
        //return rgb;
    }

    private void init() {
        try{
            test = ImageIO.read(getClass().getResource("/Level_Design/Levels/Tiles/bush.png"));
        } catch(IOException e) {
            System.out.println(e);
        }

    }
    

}
