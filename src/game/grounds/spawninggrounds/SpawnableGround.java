package game.grounds.spawninggrounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.enemies.spawnableenemies.SpawnActor;
import game.attributes.Ability;
import game.attributes.Status;
import game.attributes.Weather;

/**
 * A class representing a type of ground on which certain actors can spawn periodically.
 * SpawnableGround uses a spawn multiplier to determine the probability of spawning a given actor.
 * Subclasses may override default behaviors to introduce specific conditions, such as weather-based
 * modifications to the spawn multiplier.
 * This class is designed to be subclassed by specific types of spawning grounds that dictate the spawning of
 * different types of actors based on specific conditions.
 * Created by:
 * @author Choong Lee Ann
 * @version 1.0
 */
public abstract class SpawnableGround extends Ground {
    /** The actor implementation that will be spawned on this ground. */
    protected SpawnActor spawningActor;

    /** The default spawn multiplier. */
    public static final double DEFAULT_SPAWN_MULTIPLIER = 1.0;

    /** The current spawn multiplier, affected by conditions like weather. */
    protected double spawnMultiplier;

    /** The base probability percentage for spawning the actor. */
    protected double percentageOfSpawning;

    /** The display for showing information and errors. */
    private Display display = new Display();

    /**
     * Constructs a SpawnableGround instance.
     *
     * @param displayChar The character representation of this ground.
     * @param spawnActor The actor implementation that determines the spawning logic.
     * @param percentage The base probability percentage for spawning the actor.
     */

    public SpawnableGround(char displayChar, SpawnActor spawnActor, double percentage){
        super(displayChar);
        this.spawningActor = spawnActor;
        this.percentageOfSpawning = percentage;
        this.spawnMultiplier = SpawnableGround.DEFAULT_SPAWN_MULTIPLIER;
    }

    /**
     * Updates the spawn multiplier to the default value. Typically overridden by subclasses.
     */
    public void updateSpawnMultiplier() {
        this.spawnMultiplier = DEFAULT_SPAWN_MULTIPLIER;
    }

    /**
     * Resets the spawn multiplier to the default value.
     */
    public void resetSpawnMultiplier() {
        this.spawnMultiplier = DEFAULT_SPAWN_MULTIPLIER;
    }

    /**
     * Returns the effective percentage of spawning after applying the current spawn multiplier.
     *
     * @return The effective spawning percentage.
     */
    public double getPercentageOfSpawning() {
        return percentageOfSpawning * this.spawnMultiplier;
    }

    /**
     * Performs a periodic action, allowing actors to spawn in the graveyard based on the SpawnActor implementation.
     *
     * @param location The location of the graveyard on the game map.
     */
    @Override
    public void tick(Location location) {
        boolean isActorAdded = false;

        if(this.hasCapability(Weather.NORMAL)){
            this.resetSpawnMultiplier();
        }

        if (this.hasCapability(Weather.SUNNY) || this.hasCapability(Weather.RAINY)){
            this.updateSpawnMultiplier();
        }


        // Attempt to spawn an actor using the provided SpawnActor implementation
        Actor spawnActor = this.spawningActor.spawn(this.getPercentageOfSpawning());
//        display.println(this.spawningActor + " " + this.getPercentageOfSpawning() + " " + this.percentageOfSpawning + " " +this.spawnMultiplier + " " + this.hasCapability(Weather.SUNNY) + " " + this.hasCapability(Weather.RAINY));


        if (spawnActor != null) {
            // Iterate through exits of the graveyard location
            for (Exit exit : location.getExits()) {
                Location destination = exit.getDestination();

                // Check if the destination is empty (no actors)
                if (!(destination.containsAnActor())) {
                    // if the destination is void and actor is immune or if the ground is not void
                    if ((destination.getGround().hasCapability(Status.UNSPAWNABLE) && spawnActor.hasCapability(Ability.VOID_IMMUNE)) || !(destination.getGround().hasCapability(Status.UNSPAWNABLE))) {
                        isActorAdded = true;

                        // Add the spawned actor to the destination location
                        destination.addActor(spawnActor);
                        break;
                    }
                }
            }

            // If no suitable location for spawning was found, display an error message
            if (!isActorAdded) {
                display.println(spawningActor.toString() + " unable to be spawned in any location around the" + this);
            }
        }
    }
}
