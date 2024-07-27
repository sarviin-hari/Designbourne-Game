package game.actors.enemies.spawnableenemies;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import game.actors.enemies.Enemy;
import game.attributes.Status;

import java.util.Random;

/**
 * An abstract class representing a spawnable enemy in the game.
 * Provides the functionality for spawning a new instance of a specific enemy based on a spawn probability.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Raynen Jivash
 * @author Jun Hirano
 * @version 1.0
 */
public abstract class SpawnableEnemy extends Enemy implements SpawnActor {
    /**
     * A Random object to generate random numbers for spawning.
     */
    private final Random random = new Random();
    /**
     * Display object to show messages.
     */
    private Display display = new Display();
    /**
     * Default bound for spawning probability. Represents 100% chance.
     */
    public static final int DEFAULT_BOUND = 100;
    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public SpawnableEnemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * Spawn a new instance of the enemy based on the given spawn probability.
     *
     * @param spawnPercentage The spawn probability as a percentage.
     * @return A new instance of the enemy if it spawns; otherwise, null.
     */
    public Actor spawn(double spawnPercentage) {
        if (random.nextInt(DEFAULT_BOUND) <= spawnPercentage) {
            display.println("\nNEW " + name + " " + spawnPercentage + " ENEMY SPAWNED\n");
            Enemy enemy = this.newInstance();
            enemy.addCapability(Status.SPAWNED);
            return enemy;
        }
        return null;
    }

    /**
     * Abstract method to create a new instance of a specific type of SpawnableEnemy.
     * This method is meant to be overridden by subclasses.
     *
     * @return A new instance of the specific SpawnableEnemy.
     */
    public abstract Enemy newInstance();
}
