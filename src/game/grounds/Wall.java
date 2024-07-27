package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;

/**
 *  * A class representing a wall on a game map.
 * Created by:
 * @author Riordan D. Alfredo
 */
public class Wall extends Ground {

    /**
     * Constructor for the Wall class.
     */
    public Wall() {
        super('#');
    }

    /**
     * Overrides the canActorEnter method to make walls impassable for actors.
     *
     * @param actor The actor trying to enter the wall.
     * @return False, indicating that actors cannot enter walls.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false; // Walls are impassable
    }
}