package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.abilities.WeatherControl;
import game.actions.attacks.AttackAction;
import game.actors.enemies.UpdateDamage;
import game.actors.enemies.Enemy;
import game.attributes.Status;
import game.attributes.Weather;
import game.behaviours.*;
import game.items.consumables.HealingVial;
import game.items.consumables.Runes;

import java.util.*;

/**
 * A type of enemy that represents a Red Wolf in the game.
 * Red Wolf is affected by weather conditions, adjusting its damage accordingly.
 *
 * Authored by:
 * @author Raynen Jivash
 * Modified by:
 * @author Jun Hirano
 * @version 1.0
 */
public class RedWolf extends SpawnableEnemy implements Follow, UpdateDamage {

    /** A random generator used for probabilistic calculations. */
    private final Random random = new Random();
    /** Display object to print messages on the user interface. */
    private Display display = new Display();

    /** Constant for the probability of default hit points of RedWolf  */
    public static final int DEFAULT_HIT_POINTS = 25;
    /** Default hit rate of Red Wolf's attacks. */
    public static final int DEFAULT_HIT_RATE = 80;
    /** An item representing the runes dropped by a Red Wolf. */
    private Runes lifeCost;
    /** Default damage dealt by Red Wolf. */
    public static final int DEFAULT_DAMAGE = 15;
    /** Number of runes dropped by Red Wolf upon defeat. */
    public static final int RUNES_TO_DROP = 25;
    /** Probability for Red Wolf to drop a Healing Vial upon defeat. */
    public static final double DEFAULT_HEALING_VIAL_PROBABILITY = 0.1;
    /** Default multiplier for Red Wolf's damage. */
    public static final double DEFAULT_DAMAGE_MULTIPLIER = 1.0;
    /** Multiplier for Red Wolf's damage when the weather is sunny. */
    public static final double SUNNY_DAMAGE_MULTIPLIER = 3.0;
    /** Damage value of Red Wolf, can be modified based on conditions like weather. */
    private int damage;
    /** Multiplier for damage, can change based on conditions like weather. */
    private double damageMultiplier;



    /**
     * The constructor of the RedWolf class.
     */
    public RedWolf() {
        super("Red Wolf", 'r', DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);
        this.addCapability(Status.HOSTILE_TO_WEATHER);

        this.damage = RedWolf.DEFAULT_DAMAGE;
        this.damageMultiplier = RedWolf.DEFAULT_DAMAGE_MULTIPLIER;



    }

    /**
     * Determine the actions the Red Wolf can take during its turn.
     *
     * @param actions    Collection of possible Actions for the Red Wolf.
     * @param lastAction The Action the Red Wolf took last turn.
     * @param map        The game map containing the Red Wolf.
     * @param display    The display used to show messages and menus.
     * @return The Action to be performed.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
//        display.println(this.capabilitiesList() + " " + this.hasCapability(Weather.SUNNY) + " " + this.hasCapability(Weather.RAINY));

        if (this.hasCapability(Weather.SUNNY)){
            this.updateActorDamage();
//            display.println("Red Wolf current weather is Sunny. Current Damage is " + this.getDamage());
        }

        else{
            this.resetActorDamage();
//            display.println("Red Wolf current weather is Rainy or Normal. Current Damage is " + this.getDamage());
        }

        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Determine the allowable actions for the Red Wolf based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the Red Wolf.
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

    public int getDamage() {
        return (int) Math.round(this.damage*this.damageMultiplier);
    }

    /**
     * Get the intrinsic weapon of the Red Wolf.
     *
     * @return The intrinsic weapon used by the Red Wolf.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(this.getDamage(), "bite", DEFAULT_HIT_RATE);
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

        // drop runes
        location.addItem(this.lifeCost);
    }

    /**
     * Creates a new instance of the RedWolf class and assigns a weather-related capability.
     * This method is typically used for spawning a new RedWolf.
     *
     * @return A new RedWolf instance with a weather-related capability.
     */
    @Override
    public Enemy newInstance() {
        RedWolf redWolf = new RedWolf();
        redWolf.addCapability(WeatherControl.WEATHER_CURR);
        return redWolf;
    }

    /**
     * Updates the RedWolf's damage multiplier to reflect the impact of sunny weather.
     * The damage multiplier is set to SUNNY_DAMAGE_MULTIPLIER when this method is called.
     */
    @Override
    public void updateActorDamage() {
        this.damageMultiplier = SUNNY_DAMAGE_MULTIPLIER;
    }

    /**
     * Resets the RedWolf's damage multiplier to its default value.
     * This method is typically called when the impact of sunny weather no longer applies.
     */
    @Override
    public void resetActorDamage() {
        this.damageMultiplier = RedWolf.DEFAULT_DAMAGE_MULTIPLIER;
    }


}
