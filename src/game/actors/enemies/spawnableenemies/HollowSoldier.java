package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.enemies.Enemy;
import game.items.consumables.HealingVial;
import game.items.consumables.RefreshingFlask;
import game.items.consumables.Runes;

import java.util.Random;

/**
 * A class representing the "Hollow Soldier" enemy actor in the game.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Choong Lee Ann
 * @author Jun Hirano
 * @version 1.0
 */
public class HollowSoldier extends SpawnableEnemy {

    /** A random generator used for probabilistic calculations. */
    private final Random random = new Random();

    /** Constant for the probability of default hit points of HollowSoldier  */
    public static final int DEFAULT_HIT_POINTS = 200;

    /** Constant value for the number of runes dropped by a Hollow Soldier.  */
    public static final int RUNES_TO_DROP = 100;
    /** Constant value for the damage dealt by a Hollow Soldier's intrinsic weapon.  */
    public static final int DEFAULT_DAMAGE = 50;
    /** Constant value for the hit rate of a Hollow Soldier's intrinsic weapon.  */
    public static final int DEFAULT_HIT_RATE = 50;
    /** Constant probability for a Hollow Soldier to drop a Healing Vial.  */
    public static final double DEFAULT_HEALING_VIAL_PROBABILITY = 0.2;
    /** Constant probability for a Hollow Soldier to drop a Refreshing Flask. */
    public static final double DEFAULT_REFRESHING_FLASK_PROBABILITY = 0.3;
    /** An item representing the runes dropped by a Hollow Soldier.   */
    private Runes lifeCost;



    /**
     * Constructor for the HollowSoldier class.
     */
    public HollowSoldier() {
        super("Hollow Soldier", '&', HollowSoldier.DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);

    }

    /**
     * Define the intrinsic weapon for the HollowSoldier.
     *
     * @return The intrinsic weapon used by the HollowSoldier.
     */
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
        // 30% chance to drop Refreshing Flask
        if (random.nextDouble() <= DEFAULT_REFRESHING_FLASK_PROBABILITY){
            location.addItem(new RefreshingFlask());
        }

        // 20% chance to drop Healing Vial
        if (random.nextDouble() <= DEFAULT_HEALING_VIAL_PROBABILITY){
            location.addItem(new HealingVial());
        }

        // drop runes
        location.addItem(this.lifeCost);

    }

    /**
     * Creates and returns a new instance of the Hollow Soldier.
     *
     * @return A new instance of Hollow Soldier.
     */
    @Override
    public Enemy newInstance() {
        return new HollowSoldier();
    }
}
