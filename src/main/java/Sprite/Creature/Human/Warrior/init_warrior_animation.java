/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sprite.Creature.Human.Warrior;
import Sprite.Animation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import static Sprite.SpriteConstants.*;

/**
 *
 * @author Vegard
 */
public class init_warrior_animation {
    private String location = "/Sprite/Creature/Human/Warrior/Images/";
    private Warrior_anim animations;

    public init_warrior_animation() {
        init();
    }

    public void init() {
        Animation[] standing = createAnimations("Standing", 1);
        Animation[] walking = createAnimations("Walking", 8);
        Animation[] running = createAnimations("Running", 8);
        Animation[] talking = createAnimations("Talking", 8);
        Animation[] attacking = createAnimations("Attacking", 12);
        Animation[] beenHit = createAnimations("BeenHit", 8);
        Animation[] falling = createAnimations("Falling", 12);
        Animation[] dead = createAnimations("Dead", 1);

        animations = new Warrior_anim(standing, walking, running, talking, attacking, beenHit, falling, dead);
    }

    public Warrior_anim getAnimations() {
        return animations;
    }

    public Animation[] createAnimations(String type, int frames) {
        Animation[] anims = new Animation[NUMBER_OF_DIRECTIONS];
        anims[NORTH] = createAnimation(type, frames, n);
        anims[NORTH_EAST] = createAnimation(type, frames, ne);
        anims[EAST] = createAnimation(type, frames, e);
        anims[SOUTH_EAST] = createAnimation(type, frames, se);
        anims[SOUTH] = createAnimation(type, frames, s);
        anims[SOUTH_WEST] = createAnimation(type, frames, sw);
        anims[WEST] = createAnimation(type, frames, w);
        anims[NORTH_WEST] = createAnimation(type, frames, nw);
        return anims;
    }

    private Animation createAnimation(String type, int frames, String direction) {
        Animation anim = new Animation();
        BufferedImage[] images = new BufferedImage[frames];
        for(int i=0; i<frames; i++) {
            //System.out.println(location+type+"/"+direction+"/"+Integer.toString(i));
            System.out.println(location+type+"/"+direction+"/"+Integer.toString(i)+".png");
            images[i] = loadImage(location+type+"/"+direction+"/"+Integer.toString(i)+".png");
            anim.addFrame(images[i], 100);
        }
        return anim;
    }

    public BufferedImage loadImage(String fileName) {
        BufferedImage test = null;
        try{
            test = ImageIO.read(getClass().getResource(fileName));
        }catch(IOException e) {
        }
        return test;

    }

    public static void main(String[] args) {
        init_warrior_animation test = new init_warrior_animation();
        test.init();
        Warrior_anim anim = test.getAnimations();
    }
}
