package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Ability;
import game.attributes.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for resetting game maps by clearing actors, ground capabilities, and removing specific items.
 *
 * Authored by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class MapReset {
    /**
     * An arraylist of all gameMaps
     */
    private ArrayList<GameMap> gameMaps;

    /**
     * Constructor for the MapReset class.
     *
     * @param gameMaps The list of game maps to be reset.
     */
    public MapReset(ArrayList<GameMap> gameMaps) {
        this.gameMaps = gameMaps;
    }

    /**
     * Constructor for the MapReset class with no parameters.
     */
    public MapReset() {}

    /**
     * Reset all the maps in the game by clearing actors, ground capabilities, and removing specific items.
     */
    public void resetAllMaps() {
        for (GameMap gameMap : gameMaps) {
            this.reset(gameMap);
        }
    }

    /**
     * Reset a specific map by clearing actors, ground capabilities, and removing specific items.
     *
     * @param map The game map to reset.
     */
    public void reset(GameMap map) {
        // loop every location (x,y) and reset the actors, grounds and items based on given condition
        for (int y : map.getYRange()) {
            for (int x : map.getXRange()) {
                Location location = map.at(x, y);
                Actor actor = location.getActor();
                Ground ground = location.getGround();
                List<Item> items = location.getItems();
                this.actorReset(actor, map);
                this.groundReset(ground);
                this.itemReset(items, location);
            }
        }
    }

    /**
     * Clear actors from the map and reset the boss enemy's health if necessary.
     *
     * @param actor The actor to reset.
     * @param map   The game map to reset.
     */
    public void actorReset(Actor actor, GameMap map) {
        // if there is an actor at the given location, remove any spawned actor and reset health of BOSS ENEMY
        if (actor != null) {
            if (actor.hasCapability(Status.SPAWNED)) {
                map.removeActor(actor);
            }
            if (actor.hasCapability(Status.BOSS_ENEMY)) {
                actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, actor.getAttributeMaximum(BaseActorAttributes.HEALTH));
            }
        }
    }

    /**
     * Clear ground capabilities from a specific ground.
     *
     * @param ground The ground to reset.
     */
    public void groundReset(Ground ground) {
        // remove the Moving Map capability from the current map
        ground.removeCapability(Ability.MOVE_MAP);
    }

    /**
     * Remove specific items from a location.
     *
     * @param items    The list of items at the location.
     * @param location The location from which to remove items.
     */
    public void itemReset(List<Item> items, Location location) {
        // stores all teh items at the current location
        Map<Item, Location> itemsToRemove = new HashMap<>();  // Store items to remove

        // for every item in the location, if the item is destroyable, add to the list
        for (Item item : items) {
            if (item.hasCapability(Status.DESTROY_IF_ON_GROUND)) {
                itemsToRemove.put(item, location);
            }
        }

        // remove all the items that are destroyable
        for (Map.Entry<Item, Location> entry : itemsToRemove.entrySet()) {
            entry.getValue().removeItem(entry.getKey());
        }
    }
}
