package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.TreeMap;

/**
 * A behaviour representing the intention of an actor to follow a target actor.
 * This behaviour calculates the possible movements to reach the target and returns a MoveActorAction to follow, if possible.
 *
 * Authored by:
 * @author Choong Lee Ann
 * @version 1.0
 */
public class FollowBehaviour implements Behaviour, BehaviourOrder {

    /** Priority of the follow behaviour */
    private static final int FOLLOW_BEHAVIOUR_PRIORITY = 2;

    /** The target actor to follow */
    private final Actor target;

    /**
     * Constructor to set the target actor to follow.
     *
     * @param subject the Actor to follow
     */
    public FollowBehaviour(Actor subject) {
        this.target = subject;
    }

    /**
     * Determines the action to be performed by the actor based on their intention to follow the target actor.
     *
     * @param actor The actor performing the behaviour.
     * @param map   The game map containing the actor and the target.
     * @return A MoveActorAction to follow the target, or null if no valid movement is possible.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Only follows when both target and actor are on the map
        if (!map.contains(target) || !map.contains(actor))
            return null;

        // Location of the actor trying to follow
        Location playerLoc = map.locationOf(actor);

        // Location of the target to follow
        Location targetLoc = map.locationOf(target);

        // Stores all the possible movements and their respective distances
        TreeMap<Integer, Action> possibleMoveActions = new TreeMap<>();

        // Iterate through exits from the actor's current location
        for (Exit exit : playerLoc.getExits()) {
            Location destination = exit.getDestination();
            // If the actor can enter that exit, calculate the distance and store it with the action
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, targetLoc);
                possibleMoveActions.put(newDistance, new MoveActorAction(destination, exit.getName()));
            }
        }

        // If there is a possible path for the actor to follow, choose the smallest distance for the actor to cover
        if (!possibleMoveActions.isEmpty()) {
            return possibleMoveActions.firstEntry().getValue();
        }

        return null;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the second location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        // Measures the sum of the distance between the x and y coordinates
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Gets the priority of this behaviour in the behaviour order.
     *
     * @return The priority of this behaviour for ordering.
     */
    @Override
    public int behaviourOrderRank() {
        return FollowBehaviour.FOLLOW_BEHAVIOUR_PRIORITY;
    }
}
