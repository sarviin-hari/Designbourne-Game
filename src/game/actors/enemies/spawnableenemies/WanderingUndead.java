package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.enemies.Enemy;
import game.items.*;
import game.items.consumables.HealingVial;
import game.items.consumables.Runes;

import java.util.Random;

/**
 * A class representing the Wandering Undead enemy actor in the game.
 * @author Sarviin Hari
 * @version 1.0
 */
public class WanderingUndead extends SpawnableEnemy {
    /**
     * Utility object for generating random numbers.
     */
    private final Random random = new Random();
    /**
     * Constant for the probability of default hit points of WanderingUndead
     */
    public static final int DEFAULT_HIT_POINTS = 100;
    /**
     * Constant representing the default damage dealt by a Wandering Undead.
     */
    public static final int DEFAULT_DAMAGE = 30;
    /**
     * Constant representing the hit rate of a Wandering Undead's attacks.
     */
    public static final int DEFAULT_HIT_RATE = 50;
    /**
     * Probability that a Wandering Undead drops a "Healing Vial" when defeated.
     */
    public static final double DEFAULT_HEALING_VIAL_PROBABILITY = 0.2;
    /**
     * Probability that a Wandering Undead drops an "Old Key" when defeated.
     */
    public static final double DEFAULT_OLD_KEY_PROBABILITY = 0.25;
    /**
     * The amount of runes a Wandering Undead drops upon defeat.
     */
    public static final int RUNES_TO_DROP = 50;
    /**
     * Object representing the runes that the Wandering Undead drops upon defeat.
     */
    private Runes lifeCost;

    /**
     * Constructor for the WanderingUndead class.
     */
    public WanderingUndead() {
        super("Wandering Undead", 't', WanderingUndead.DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);

    }

    /**
     * Get the intrinsic weapon of the Wandering Undead.
     *
     * @return The intrinsic weapon used by the Wandering Undead.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(DEFAULT_DAMAGE, "limbs", DEFAULT_HIT_RATE);
    }

    /**
     * Drops the associated item at the specified location within the game world.
     *
     * @param location The location where the item should be dropped.
     */
    @Override
    public void dropItem(Location location) {
        // 25% chance to drop Old Key
        if (random.nextDouble() <= DEFAULT_OLD_KEY_PROBABILITY){
            location.addItem(new OldKey());
        }

        // 20% chance to drop Healing Vial
        if (random.nextDouble() <= DEFAULT_HEALING_VIAL_PROBABILITY){
            location.addItem(new HealingVial());
        }

        // drop runes
        location.addItem(this.lifeCost);
    }

    /**
     * Creates and returns a new instance of the Wandering Undead.
     *
     * @return A new instance of WanderingUndead.
     */
    @Override
    public Enemy newInstance() {
        return new WanderingUndead();
    }
}
