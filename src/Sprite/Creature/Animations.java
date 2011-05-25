/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sprite.Creature;

import Sprite.Animation;
import java.util.ArrayList;
import static Sprite.SpriteConstants.*;

/**
 *
 * @author Vegard
 */
public abstract class Animations {
    protected ArrayList<Animation[]> animations;

    // Creates an empty arraylist of animations.
    public Animations() {
        animations = new ArrayList<Animation[]>();
    }

    public Animations(Animation[] standing, Animation[] walking, Animation[] running, Animation[] talking) {
        animations = new ArrayList<Animation[]>();
        animations.add(standing);
        animations.add(walking);
        animations.add(running);
        animations.add(talking);        
    }

    public Animation getStandingAnimation(int direction) {
        return animations.get(STOPPED)[direction];
    }

    public Animation getWalkingAnimation(int direction) {
        return animations.get(WALKING)[direction];
    }

    public Animation getRunningAnimation(int direction) {
        return animations.get(RUNNING)[direction];
    }

    public Animation getTalkingAnimation(int direction) {
        return animations.get(TALKING)[direction];
    }

    public Animation getCurrentStateAnimation(int state, int direction) {
        return animations.get(state)[direction];
    }

    public abstract Object clone();

    

    public abstract Animation getAttackingAnimation(int direction);
    public abstract Animation getBeenHitAnimation(int direction);
    public abstract Animation getFallingAnimation(int direction);

    

}
