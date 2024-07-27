package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import game.attributes.Ability;

/**
 * A class that represents the floor inside a building.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class Floor extends Ground {

    /**
     * Constructor for the Floor class.
     * Initializes the ground with a '_' character to represent the floor inside a building.
     */
    public Floor() {
        super('_');
    }

    /**
     * Determines whether an actor can enter this floor based on their capabilities.
     *
     * @param actor The actor trying to enter the floor.
     * @return True if the actor has the ENTER_FLOOR capability, indicating they can enter the floor, otherwise False.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Ability.ENTER_FLOOR);
    }
}