package game.grounds.spawninggrounds;

import game.actors.enemies.spawnableenemies.SpawnActor;
import game.attributes.Weather;

/**
 * A class that represents a empty hut ground on a game map.
 * Actors can spawn in the Empty hut over time based on a provided SpawnActor implementation.
 * @author Choong Lee Ann
 * @version 1.0
 */
public class EmptyHut extends SpawnableGround {

    public static final double SUNNY_MULTIPLIER = 2;

    /**
     * Constructor for the EmptyHut class.
     *
     * @param spawnActor The implementation of SpawnActor that determines which actors can spawn in the emptyHut.
     * @param percentage The percentage for the probability of spawning of enemy
     */
    public EmptyHut(SpawnActor spawnActor, int percentage) {
        super('h', spawnActor, percentage);
    }

    /**
     * Updates the spawn multiplier based on the current weather conditions. If the weather is sunny,
     * {@code SUNNY_MULTIPLIER} is applied.
     */
    @Override
    public void updateSpawnMultiplier() {
        if (this.hasCapability(Weather.SUNNY)) {
            this.spawnMultiplier = EmptyHut.SUNNY_MULTIPLIER;
        }
        else{
            this.spawnMultiplier = EmptyHut.DEFAULT_SPAWN_MULTIPLIER;
        }
    }
}
