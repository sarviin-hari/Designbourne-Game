package game;

import edu.monash.fit2099.engine.positions.Location;

/**
 * A class representing a mapping between a location and a map to move to.
 * This class encapsulates a pair of a location on a game map and the name of the map to move to after unlocking a gate.
 *
 * Authored by:
 * @author Raynen Jivash
 * @version 1.0
 */
public class LocationMap {
    /**
     * The current location on the game map.
     */
    private Location location;

    /**
     * The name of the map to which the player should move when passing through this location.
     */
    private String mapToMove;


    /**
     * Constructor for the LocationMap class.
     *
     * @param location   The location on the destination map.
     * @param mapToMove  The name of the map to move to.
     */
    public LocationMap(Location location, String mapToMove) {
        this.location = location;
        this.mapToMove = mapToMove;
    }

    /**
     * Get the location to the map travelling to.
     *
     * @return The location on the map travelling to.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Get the name of the map to move to.
     *
     * @return The name of the map to move to.
     */
    public String getMapToMove() {
        return mapToMove;
    }
}
