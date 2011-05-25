/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Sprite.Creature.Human.Warrior;

import Sprite.Animation;
import Sprite.Creature.Animations;
import static Sprite.SpriteConstants.*;

/**
 *
 * @author Vegard
 */
public class Warrior_anim extends Animations {    

    public Warrior_anim(Animation[] stopped, Animation[] walking, Animation[] running, Animation[] talking,
            Animation[] attacking, Animation[] beenHit, Animation[] falling, Animation[] dead) {
        super(stopped, walking, running, talking);
        animations.add(attacking);
        animations.add(beenHit);
        animations.add(falling);
        animations.add(dead);
    }

    public Animation getAttackingAnimation(int direction) {
        return animations.get(ATTACKING)[direction];
    }

    public Animation getBeenHitAnimation(int direction) {
        return animations.get(BEEN_HIT)[direction];
    }

    public Animation getFallingAnimation(int direction) {
        return animations.get(DYING)[direction];
    }
    
    public Object clone() {
        return new Warrior_anim(animations.get(0), animations.get(1), animations.get(2), animations.get(3), animations.get(4),
                animations.get(5), animations.get(6), animations.get(7));
    }
}
    