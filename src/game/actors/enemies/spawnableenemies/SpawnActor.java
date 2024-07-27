package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * An interface for defining the ability to spawn actors in the game.
 * Classes implementing this interface should provide a mechanism to create and return a new actor instance.
 * @author Sarviin Hari
 * @version 1.0
 */
public interface SpawnActor {
    /**
     * Spawn a new actor instance.
     *
     * @param spawnPercentage Integer for percentage to spawn enemy
     * @return A new actor instance to be added to the game.
     */
    Actor spawn(double spawnPercentage);
}

