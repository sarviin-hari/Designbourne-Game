package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.attacks.AttackAction;
import game.actors.enemies.Enemy;
import game.attributes.Ability;
import game.attributes.Status;
import game.behaviours.Follow;
import game.behaviours.FollowBehaviour;
import game.items.consumables.HealingVial;
import game.items.consumables.RefreshingFlask;
import game.items.consumables.Runes;
import java.util.Random;


/**
 * A class representing a EldentreeGuardian enemy in the Game.
 * EldentreeGuardian is a spawnable type enemy that can follow.
 *
 * Authored by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class EldentreeGuardian extends SpawnableEnemy implements Follow {
    /**
     * Generate random numbers
     */
    private final Random random = new Random();

    /**
     * Default probability of dropping a healing vial for Eldentree Guardian.
     */
    public static final double DEFAULT_HEALING_VIAL_PROBABILITY = 0.25;
    /** Constant probability for a Eldentree Guardian to drop a Refreshing Flask. */
    public static final double DEFAULT_REFRESHING_FLASK_PROBABILITY = 0.15;

    /**
     * Default hit points for Eldentree Guardian.
     */
    public static final int DEFAULT_HIT_POINTS = 250;

    /**
     * Default damage inflicted by Eldentree Guardian.
     */
    public static final int DEFAULT_DAMAGE = 50;

    /**
     * Default hit rate (accuracy) for Eldentree Guardian.
     */
    public static final int DEFAULT_HIT_RATE = 80;

    /**
     * Number of runes to drop by Eldentree Guardian.
     */
    public static final int RUNES_TO_DROP = 250;

    /**
     * The cost of life for this Eldentree Guardian in the form of runes.
     */
    private Runes lifeCost;


    /**
     * The constructor of the Eldentree Guardian class.
     */
    public EldentreeGuardian() {
        super("EldenTree Guardian", 'e', DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);
        this.addCapability(Ability.VOID_IMMUNE);  //Ensure that EldentreeGuardian is unaffected by the Void

    }

    /**
     * Get the intrinsic weapon of the EldentreeGuardian.
     *
     * @return The intrinsic weapon used by the EldentreeGuardian.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(DEFAULT_DAMAGE, "limbs", DEFAULT_HIT_RATE);
    }



    /**
     * Determine the allowable actions for the EldentreeGuardian based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the EldentreeGuardian.
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
        // 25% chance to drop Healing Vial
        if (random.nextDouble() <= DEFAULT_HEALING_VIAL_PROBABILITY){
            location.addItem(new HealingVial());
        }
        // 15% to drop RefreshingFlask
        if (random.nextDouble() <= DEFAULT_REFRESHING_FLASK_PROBABILITY){
            location.addItem(new RefreshingFlask());
        }

        // Drop the associated life cost item
        location.addItem(this.lifeCost);

    }


    /**
     * Add a following behaviour to the EldentreeGuardian.
     */
    @Override
    public void followBehaviour(Actor target) {
        FollowBehaviour followBehaviour = new FollowBehaviour(target);
        this.behaviours.put(followBehaviour.behaviourOrderRank(), followBehaviour);

    }

    /**
     * Create a new instance of the Eldentree Guardian.
     *
     * @return A new instance of the Eldentree Guardian.
     */
    @Override
    public Enemy newInstance() {
        return new EldentreeGuardian();
    }



}
