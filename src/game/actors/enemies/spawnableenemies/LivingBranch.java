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
import game.behaviours.AttackingBehaviour;
import game.items.consumables.Bloodberry;
import game.items.consumables.Runes;
import java.util.Random;

/**
 * A class representing a LivingBranch enemy in the Game.
 * LivingBranch is a spawnable type enemy that cannot Wander.
 *
 * Authored by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class LivingBranch extends SpawnableEnemy{
    /**
     * Generate random numbers
     */
    private final Random random = new Random();

    /**
     * Default probability of dropping a bloodberry for Living Branch.
     */
    public static final double DEFAULT_BLOODBERRY_PROBABILITY = 0.5;

    /**
     * Default hit points for Living Branch.
     */
    public static final int DEFAULT_HIT_POINTS = 75;

    /**
     * Default damage inflicted by Living Branch.
     */
    public static final int DEFAULT_DAMAGE = 250;

    /**
     * Default hit rate (accuracy) for Living Branch.
     */
    public static final int DEFAULT_HIT_RATE = 90;

    /**
     * Number of runes to drop by Living Branch.
     */
    public static final int RUNES_TO_DROP = 500;

    /**
     * The cost of life for this Living Branch in the form of runes.
     */
    private Runes lifeCost;


    /**
     * The constructor of the LivingBranch class.
     */
    public LivingBranch() {
        super("Living Branch", '?', DEFAULT_HIT_POINTS);
        this.lifeCost = new Runes(RUNES_TO_DROP);
        this.addCapability(Ability.VOID_IMMUNE);  // So that LivingBranch cannot be affected by the Void

    }

    /**
     * Get the intrinsic weapon of the Living Branch.
     *
     * @return The intrinsic weapon used by the Living Branch.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(DEFAULT_DAMAGE, "limbs", DEFAULT_HIT_RATE);
    }



    /**
     * Determine the allowable actions for the Living Branch based on the capabilities of the interacting actor.
     *
     * @param otherActor The actor interacting with the Living Branch.
     * @param direction  The direction of interaction.
     * @param map        The game map containing the actors.
     * @return A list of allowable actions based on the actor's capabilities.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
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
        // 50% chance to drop Bloodberry
        if (random.nextDouble() <= DEFAULT_BLOODBERRY_PROBABILITY){
            location.addItem(new Bloodberry());
        }

        // Drop the associated life cost item
        location.addItem(this.lifeCost);

    }


    /**
     * Create a new instance of the Living Branch.
     *
     * @return A new instance of the Living Branch.
     */
    @Override
    public Enemy newInstance() {
        return new LivingBranch();

    }


    /**
     * Add specific behaviors to the LivingBranch, which include only attacking behavior.
     */
    @Override
    protected void addBehaviours() {
        AttackingBehaviour attackingBehaviour = new AttackingBehaviour();
        this.behaviours.put(attackingBehaviour.behaviourOrderRank(), attackingBehaviour);

    }

}
