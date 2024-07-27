package game.grounds.spawninggrounds;

import game.actors.enemies.spawnableenemies.SpawnActor;
import game.attributes.Weather;

/**
 * A class that represents a bush ground on a game map.
 * Actors can spawn in the bush over time based on a provided SpawnActor implementation.
 * @author Choong Lee Ann
 * @version 1.0
 */
public class Bush extends SpawnableGround {

    public static final double RAINY_MULTIPLIER = 1.5;

    /**
     * Constructor for the Bush class.
     *
     * @param actor The implementation of SpawnActor that determines which actors can spawn in the bush.
     * @param percentage The percentage for the probability of spawning of enemy
     */
    public Bush(SpawnActor actor, double percentage) {
        super('m', actor, percentage);
    }

    /**
     * Updates the spawn multiplier based on the current weather conditions. If the weather is rainy,
     * {@code RAINY_MULTIPLIER} is applied.
     */
    @Override
    public void updateSpawnMultiplier() {
        if (this.hasCapability(Weather.RAINY)) {
            this.spawnMultiplier = Bush.RAINY_MULTIPLIER;
        }
        else{
            this.spawnMultiplier = Bush.DEFAULT_SPAWN_MULTIPLIER;
        }
    }
}
