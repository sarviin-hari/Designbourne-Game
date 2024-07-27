package game.actions;

import edu.monash.fit2099.engine.positions.Location;

/**
 * The DropAction interface defines a contract for actions that allow a character to drop an item
 * at a specific location within the game world.
 *
 * Implementations of this interface should provide a way to drop an item at the specified location.
 * The dropped item should be placed in the game world at the specified location.
 *
 * Authored by:
 * @author Choong Lee Ann
 * @version 1.0
 */
public interface DropAction {
    /**
     * Drops the associated item at the specified location within the game world.
     *
     * @param location The location where the item should be dropped.
     */
    void dropItem(Location location);
}
