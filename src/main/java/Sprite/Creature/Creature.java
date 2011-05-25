
package Sprite.Creature;

import Sprite.Animation;
import Sprite.Sprite;
import java.lang.reflect.Constructor;
import static Sprite.SpriteConstants.*;

/**
 * A Creature is a Sprite that is affected by gravity and can
 * die. It has four Animations: moving left, moving right,
 * dying on the left, and dying on the right.
 * @author Vegard
 */
public abstract class Creature extends Sprite {

    /**
        Amount of time to go from STATE_DYING to STATE_DEAD.
    */
    private static final int DIE_TIME = 600;
    private long ATTACK_TIME;
    
   
    public Animations anims;    
    private int state;
    private int current_direction;
    private long stateTime;


    /**
        Creates a new Creature with the specified Animations.
    */
    public Creature(Animations anims) {
        super(anims.getStandingAnimation(EAST));
        this.anims = anims;
        current_direction = EAST;
        state = STOPPED;
        //ATTACK_TIME = anims.getAttackingAnimation(current_direction).getDuration();
        ATTACK_TIME = 600;
    }   

    @Override
    public Object clone() {
        // use reflection to create the correct subclass
        Constructor constructor = getClass().getConstructors()[0];
        try {
            return constructor.newInstance(new Object[] {                
                (Animations)anims.clone(),                
            });
        }
        catch (Exception ex) {
            // should never happen
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the walking speed of this creature.
     * @return walking speed.
     */
    public float getMoveSpeed() {
        return 0;
    }

    public abstract boolean isRunning();
    public abstract boolean isAttacking();
    public abstract void swapAttacking();

    /**
        Wakes up the creature when the Creature first appears
        on screen. Normally, the creature starts moving left.
    */
    public void wakeUp() {
        if (getState() == STOPPED && getVelocityX() == 0) {
            
            setVelocityX(-getMoveSpeed());
        }
    }

    /**
        Gets the state of this Creature. The state is either
        STATE_NORMAL, STATE_DYING, or STATE_DEAD.
    */
    public int getState() {
        return state;
    }

    /**
     * Sets the state of the creature.
     * @param state
     */
    public void setState(int state) {
        if(this.state != state) {
            this.state = state;
            stateTime = 0;
            if(state == DYING) {
                setVelocityX(0);
                setVelocityY(0);
            }
        }
    }

    /**
     * Checks if the creature is alive
     * @return Returns true if the creature is alive, else false.
     */
    public boolean isAlive() {
        return (state != DYING && state != DEAD);
    }

    /**
     * Checks if the creature is flying.
     */
    public boolean isFlying() {
        return false;
    }

    /**
        Called before update() if the creature collided with a
        tile horizontally.
    */
    public void collideHorizontal() {
        setVelocityX(-getVelocityX());
    }


    /**
        Called before update() if the creature collided with a
        tile vertically.
    */
    public void collideVertical() {
        setVelocityY(0);
    }

    /**
     * Updates the current direction.
     * @return Current direction.
     */
    public int getCurrentDirection() {
        int direction = current_direction;
        if(getVelocityX() < 0) {
            if(getVelocityY() == 0) {
                direction = WEST;
            }
            else if(getVelocityY() > 0) {
                direction = SOUTH_WEST;
            }
            else if(getVelocityY() < 0) {
                direction = NORTH_WEST;
            }
        }
        else if(getVelocityX() > 0) {
            if(getVelocityY() == 0) {
                direction = EAST;
            }
            else if(getVelocityY() > 0) {
                direction = SOUTH_EAST;
            }
            else if(getVelocityY() < 0) {
                direction = NORTH_EAST;
            }
        }
        else if(getVelocityX() == 0) {
            if(getVelocityY() > 0) {
                direction = SOUTH;
            }
            else if(getVelocityY() < 0) {
                direction = NORTH;
            }
        }
        return direction;
    }

    /**
     * Updates the current animation based on the current state and direction.
     * @param elapsedTime
     */
    public void updateAnim(long elapsedTime) {
        current_direction = getCurrentDirection();
        Animation newAnim = anims.getCurrentStateAnimation(getState(), current_direction);
        if(newAnim != anim) {
            anim = newAnim;
            anim.start();
        }
        else {
            anim.update(elapsedTime);
        }        
    }

    /**
     * Updates the current state.
     * @param elapsedTime
     */
    public void updateState(long elapsedTime) {
        if(isAlive()) {
            if(!isAttacking() && getVelocityX() == 0 && getVelocityY() == 0) {
                setState(STOPPED);
            }
            else if(isAttacking()) {
                setState(ATTACKING);                
            }
            else if(isRunning()) {
                setState(RUNNING);
            }
            else if(!isRunning() && !isAttacking()) {
                setState(WALKING);
            }
        }
       
        
        stateTime += elapsedTime;
        
    }
   
    /**
        Updates the animation for this creature.
    */

  
    @Override
    public void update(long elapsedTime, boolean collision) {
        
        // move player
        super.update(elapsedTime, collision);
        // select the correct state
        updateState(elapsedTime);
        // select the correct animation
        updateAnim(elapsedTime);
        
        
        if (state == ATTACKING && stateTime >= ATTACK_TIME) {
            swapAttacking();
            System.out.println("SWAPPED");
            setState(STOPPED);            
            
        }
        if (state == DYING && stateTime >= DIE_TIME) {
            setState(DEAD);
            System.out.println("STATE IS NOW: DEAD");
        }
        
    }

}
