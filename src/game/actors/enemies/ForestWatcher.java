package game.actors.enemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.FancyMessage;
import game.LocationMap;
import game.actors.abilities.WeatherControl;
import game.actions.attacks.AttackAction;
import game.attributes.Ability;
import game.attributes.Status;
import game.attributes.Weather;
import game.behaviours.*;
import game.grounds.LockedGate;
import game.items.consumables.Runes;


import java.util.*;

/**
 * A class representing a Forest Watcher enemy in the game.
 * Forest Watchers have the ability to switch weather and follow hostile actors.
 * They can drop runes upon defeat and change the ground to a locked gate.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Modified by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class ForestWatcher extends Enemy implements Follow {
    /**
     * Constant for the number of runes to drop when the ForestWatcher is defeated.
     */
    public static final int RUNES_TO_DROP = 5000;

    /**
     * Constant for the default hit points of the ForestWatcher.
     */
    public static final int DEFAULT_HIT_POINTS = 2000;

    /**
     * Constant for the default hit rate of the ForestWatcher.
     */
    public static final int DEFAULT_HIT_RATE = 25;

    /**
     * Constant for the default damage inflicted by the ForestWatcher.
     */
    public static final int DEFAULT_DAMAGE = 80;

    /**
     * The number of turns after which the ForestWatcher switches the weather.
     */
    private static final int WEATHER_SWITCH_TURNS = 3;


    /**
     * The list of destination game maps to which the ForestWatcher
     * will send the player to when ForestWatcher dies.
     */
    private ArrayList<LocationMap> destinations;


    /**
     * The list of all game maps in the game.
     */
    private ArrayList<GameMap> gameMaps;

    /**
     * The WeatherControl instance used by the ForestWatcher to control weather.
     */
    private WeatherControl weatherControl;

    /**
     * Constructor for the ForestWatcher class.
     *
     * @param destinations The list of destination game maps to which the ForestWatcher can lead the player.
     * @param gameMaps     The list of all game maps in the game.
     */
    public ForestWatcher(ArrayList<LocationMap> destinations, ArrayList<GameMap> gameMaps) {
        super("Forest Watcher", 'Y', DEFAULT_HIT_POINTS);
        this.addCapability(Ability.VOID_IMMUNE);
        this.destinations = destinations;
        this.gameMaps = gameMaps;
        this.addCapability(Status.BOSS_ENEMY);
        weatherControl = new WeatherControl(this, ForestWatcher.WEATHER_SWITCH_TURNS, this.gameMaps);
    }


    /**
     * Returns the intrinsic weapon of the ForestWatcher.
     *
     * @return The intrinsic weapon used by the ForestWatcher.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(DEFAULT_DAMAGE, "limbs", DEFAULT_HIT_RATE);
    }


    /**
     * Defines the actions to be taken by the ForestWatcher during its turn to play.
     *
     * @param actions    The list of allowable actions for the ForestWatcher.
     * @param lastAction The last action performed.
     * @param map        The game map containing the ForestWatcher.
     * @param display    The display to show messages or updates.
     * @return The action to be performed by the ForestWatcher.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        weatherControl.switchWeather();

        // set the weather to Sunny when the current weather is Sunny
        if (this.hasCapability(Weather.SUNNY)) {
//            display.println("The current weather is Sunny");
            weatherControl.isSunny();
        } else {
//            display.println("The current weather is Rainy");
            weatherControl.isRainy();
        }

        // getAction based on the behaviour priority
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }



    /**
     * Determine the allowable actions for the ForestWatcher based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the ForestWatcher.
     * @param direction  The direction of interaction.
     * @param map        The game map containing the actors.
     * @return A list of allowable actions based on the actor's capabilities.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            this.followBehaviour(otherActor);
            actions.add(new AttackAction(this, direction));
        }

        return actions;
    }
    /**
     * Drops an item (Runes) at the specified location (location ForestWatcher died) within the game world.
     *
     * @param location The location where the item should be dropped.
     */
    @Override
    public void dropItem(Location location) {
        location.addItem(new Runes(RUNES_TO_DROP));
    }

    /**
     * Executed when the ForestWatcher becomes unconscious due to the action of another actor.
     * This method sets the weather to normal, creates a LockedGate ground with specified destinations,
     * drops an item at the ForestWatcher's location, and removes the ForestWatcher from the map.
     *
     * @param actor The actor responsible for the ForestWatcher becoming unconscious.
     * @param map   The game map containing the ForestWatcher.
     * @return A string describing the event when the ForestWatcher becomes unconscious.
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        weatherControl.isNormal();    //Set weather to normal
        ArrayList<LocationMap> locationMaps = new ArrayList<>(destinations);
        map.at(map.locationOf(this).x(), map.locationOf(this).y()).setGround(new LockedGate(locationMaps)); //A LockedGate at current location
        this.dropItem(map.locationOf(this));
        map.removeActor(this);
        actor.addCapability(Status.BOSS_DEFEATED);
        return this + " met their demise in the hand of " + actor + "\n" + FancyMessage.FOREST_WATCHER_DIED;
    }

    /**
     * Add a following behaviour to the Forest Watcher.
     *
     * @param followTarget The actor to follow.
     */
    @Override
    public void followBehaviour(Actor followTarget) {
        FollowBehaviour followBehaviour = new FollowBehaviour(followTarget);
        this.behaviours.put(followBehaviour.behaviourOrderRank(), followBehaviour);
    }
}
