
package Sprite.Creature.Human.Warrior;


import Sprite.Creature.Creature;

/**
 *
 * @author Vegard
 */
public class Warrior extends Creature {      
    private float WALK_SPEED = .2f;
    private float RUN_SPEED = .3f;
    private float CURRENT_SPEED;
    private boolean isRunning;    
    private boolean isAttacking;
    
    public Warrior(Warrior_anim anims) {
        super(anims);
        CURRENT_SPEED = WALK_SPEED;
        isRunning = false;
        isAttacking = false;
    }
   
    @Override
    public float getMoveSpeed() {
        return CURRENT_SPEED;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
    
    public void swapRunning() {
        if(isRunning) {
            isRunning = false;
            CURRENT_SPEED = WALK_SPEED;
        } else {
            isRunning = true;
            CURRENT_SPEED = RUN_SPEED;
        }
    }

    public void swapAttacking() {
        if(!isAttacking) {
            isAttacking = true;
        } else {
            isAttacking = false;            
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

}
