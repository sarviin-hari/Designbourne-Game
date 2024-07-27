package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.attacks.AttackAction;
import game.actions.DropAction;
import game.attributes.Status;
import game.behaviours.AttackingBehaviour;
import game.behaviours.WanderBehaviour;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Represents the common functionality and characteristics of enemy actors within the game.
 * The Enemy class extends Actor and implements methods that allow enemies to engage in combat, wander,
 * and interact with the game world in various ways.
 *
 * Enemy actors can execute behaviors such as attacking and wandering.
 * When an enemy is defeated, they can drop items in the game world.
 *
 * @author Choong Lee Ann
 * Modified by:
 * @author Raynen Jivash
 * @version 1.0
 */
public abstract class Enemy extends Actor implements DropAction {
    /**
     * A collection of behaviours that the enemy actor can exhibit. The behaviours are ordered based on their execution priority.
     */
    protected Map<Integer, Behaviour> behaviours = new TreeMap<>();
    /**
     * Utility object for generating random numbers.
     */
    private final Random random = new Random();

//    protected Runes lifeCost;
    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        addBehaviours();   //Calling method to add default behaviours into Enemy's TreeMap of Behaviours

        this.addCapability(Status.ENEMY);
    }

//    public abstract void addLifeCost();

    /**
     * Determine the allowable actions for the HollowSoldier based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the HollowSoldier.
     * @param direction  The direction of interaction.
     * @param map        The game map containing the actors.
     * @return A list of allowable actions based on the actor's capabilities.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }


    /**
     * Determine the actions the Wandering Undead can take during its turn.
     *
     * @param actions    Collection of possible Actions for the Wandering Undead.
     * @param lastAction The Action the Wandering Undead took last turn.
     * @param map        The game map containing the Wandering Undead.
     * @param display    The display used to show messages and menus.
     * @return The Action to be performed.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Method to execute when the Wandering Undead becomes unconscious due to the action of another actor.
     *
     * @param actor The perpetrator of the action.
     * @param map   The game map where the Wandering Undead fell unconscious.
     * @return A string describing what happened when the Wandering Undead is unconscious.
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        String str = this + " met their demise in the hand of " + actor;
        // drop an item at the location where the wandering undead died
        Location location = map.locationOf(this);
        this.dropItem(location);

        map.removeActor(this);
        return str;
    }

    /**
     * Abstract method to be implemented by subclasses, which defines the logic for dropping items upon defeat.
     *
     * @param location Location in the game world where the items should be dropped.
     */
    public abstract void dropItem(Location location);

//I acknowledge the use of ChatGPT in forming this code
    /**
     * Add common behaviors to the enemy, which include both attacking and wandering behaviors.
     */
    protected void addBehaviours() {
        AttackingBehaviour attackingBehaviour = new AttackingBehaviour();
        WanderBehaviour wanderBehaviour = new WanderBehaviour();
        this.behaviours.put(attackingBehaviour.behaviourOrderRank(), attackingBehaviour);
        this.behaviours.put(wanderBehaviour.behaviourOrderRank(), wanderBehaviour);
    }

}
