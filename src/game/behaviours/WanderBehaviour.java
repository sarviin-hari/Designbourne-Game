package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * A behaviour representing the intention of an actor to wander to a random adjacent location.
 * This behaviour checks adjacent locations for the ability to enter and returns a MoveAction to wander, if possible.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class WanderBehaviour implements Behaviour, BehaviourOrder {

    /** Priority of the wander behaviour */
    private static final int WANDER_BEHAVIOUR_PRIORITY = 2;

    /** Random generator for selecting random movements */
    private final Random random = new Random();

    /**
     * Determines the action to be performed by the actor based on their intention to wander randomly.
     *
     * @param actor The actor performing the behaviour.
     * @param map   The game map containing the actor.
     * @return A MoveAction to wander to a random adjacent location, or null if no valid movement is possible.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        ArrayList<Action> actions = new ArrayList<>();

        // Iterate through exits from the actor's current location
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();

            // Check if the destination location can be entered by the actor
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        // Check if any valid movement actions are available and select a random movement from the available options
        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        } else {
            // No valid movement action available, return null
            return null;
        }
    }

    /**
     * Gets the priority of this behaviour in the behaviour order.
     *
     * @return The priority of this behaviour for ordering.
     */
    @Override
    public int behaviourOrderRank() {
        return WanderBehaviour.WANDER_BEHAVIOUR_PRIORITY;
    }
}
