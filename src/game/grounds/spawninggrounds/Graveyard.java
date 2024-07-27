package game.grounds.spawninggrounds;

import game.actors.enemies.spawnableenemies.SpawnActor;

/**
 * A class that represents a graveyard ground on a game map.
 * Actors can spawn in the graveyard over time based on a provided SpawnActor implementation.
 * @author Sarviin Hari
 * @version 1.0
 */
public class Graveyard extends SpawnableGround {

    /**
     * Constructor for the Graveyard class.
     *
     * @param actor The implementation of SpawnActor that determines which actors can spawn in the graveyard.
     * @param percentage The percentage for the probability of spawning of enemy
     */
    public Graveyard(SpawnActor actor, int percentage) {
        super('n', actor, percentage);
    }
}
