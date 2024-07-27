package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.abilities.WeatherControl;
import game.actions.attacks.AttackAction;
import game.actors.enemies.Enemy;
import game.attributes.Status;
import game.attributes.Weather;
import game.behaviours.*;
import game.items.consumables.HealingVial;
import game.items.consumables.Runes;

import java.util.*;

/**
 * A class representing a Forest Keeper enemy, a spawnable type of enemy that can follow.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Modified by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class ForestKeeper extends SpawnableEnemy implements Follow {

    /**
     * Generate random numbers
     */
    private final Random random = new Random();

    /**
     * Default probability of dropping a healing vial for Forest Keeper.
     */
    public static final double DEFAULT_HEALING_VIAL_PROBABILITY = 0.2;

    /**
     * Default hit points for Forest Keeper.
     */
    public static final int DEFAULT_HIT_POINTS = 125;

    /**
     * Default damage inflicted by Forest Keeper.
     */
    public static final int DEFAULT_DAMAGE = 25;

    /**
     * Default hit rate (accuracy) for Forest Keeper.
     */
    public static final int DEFAULT_HIT_RATE = 75;

    /**
     * Number of runes to drop by Forest Keeper.
     */
    public static final int RUNES_TO_DROP = 50;

    /**
     * The cost of life for this Forest Keeper in the form of runes.
     */
    private Runes lifeCost;

    /**
     * Health update for the Forest Keeper when the weather is rainy.
     */
    private static final int HEALTH_UPDATE = 10;

    /**
     * The constructor of the ForestKeeper class.
     */
    public ForestKeeper() {
        super("Forest Keeper", '8', DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);
        this.addCapability(Status.HOSTILE_TO_WEATHER);
    }

    /**
     * Get the intrinsic weapon of the Forest Keeper.
     *
     * @return The intrinsic weapon used by the Forest Keeper.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(DEFAULT_DAMAGE, "limbs", DEFAULT_HIT_RATE);
    }

    /**
     * Perform actions for the Forest Keeper during its turn.
     *
     * @param actions    List of possible actions for the Forest Keeper.
     * @param lastAction The last action performed.
     * @param map        The game map containing the Forest Keeper.
     * @param display    The display object for rendering the game.
     * @return The action to be performed by the Forest Keeper.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if (this.hasCapability(Weather.RAINY)){
//            display.println("Forest Keeper current weather is Rainy. Current Health increased by 10");
            this.updateActorHealth();
        }
        else{
//            display.println("Forest Keeper current weather is Sunny or Normal. Current Health remains");
        }

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Determine the allowable actions for the Forest Keeper based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the Forest Keeper.
     * @param direction  The direction of interaction.
     * @param map        The game map containing the actors.
     * @return A list of allowable actions based on the actor's capabilities.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            this.followBehaviour(otherActor);
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }


    /**
     * Drops the associated item at the specified location within the game world.
     *
     * @param location The location where the item should be dropped.
     */
    @Override
    public void dropItem(Location location) {
        // 20% chance to drop Healing Vial
        if (random.nextDouble() <= DEFAULT_HEALING_VIAL_PROBABILITY){
            location.addItem(new HealingVial());
        }

        // Drop the associated life cost item
        location.addItem(this.lifeCost);

    }


    /**
     * Add a following behaviour to the Forest Keeper.
     */
    @Override
    public void followBehaviour(Actor target) {
        FollowBehaviour followBehaviour = new FollowBehaviour(target);
        this.behaviours.put(followBehaviour.behaviourOrderRank(), followBehaviour);

    }

    /**
     * Create a new instance of the Forest Keeper.
     *
     * @return A new instance of the Forest Keeper.
     */
    @Override
    public Enemy newInstance() {
        ForestKeeper forestKeeper = new ForestKeeper();
        forestKeeper.addCapability(WeatherControl.WEATHER_CURR);
        return forestKeeper;
    }

    /**
     * Update the health of the actor when the weather is rainy.
     */
    public void updateActorHealth() {
        this.heal(ForestKeeper.HEALTH_UPDATE);
    }

}
