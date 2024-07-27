package game.grounds;

import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperations;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.Ability;
import game.attributes.Status;

/**
 * A class representing a void, an impassable and deadly ground type.
 *
 * Authored by:
 * @author Sarviin Hari
 * Modified by:
 * @author Choong Lee Ann
 * @version 1.0
 */
public class Void extends Ground {

    /**
     * Display the items for input or output
     */
    private Display display = new Display();
    /**
     * Constant for the health being 0 (dead)
     */
    public static final int ZERO_HEALTH = 0;

    /**
     * Constructor for the Void class .
     * Initializes the Void ground with the '+' character and adds the UNSPAWNABLE capability.
     */
    public Void() {
        super('+'); // Initializes the Void ground with the '+' character.
        this.addCapability(Status.UNSPAWNABLE); // No actors can be spawned on Void
    }

    /**
     * Tick method to handle the passage of time on the Void ground.
     * Actors that enter the Void are marked as unconscious due to natural causes.
     * Actors that have WALK_FREELY ability can step on Void without consequences
     *
     * @param location The location of the Void ground.
     */
    @Override
    public void tick(Location location) {
        if (location.containsAnActor() && !location.getActor().hasCapability(Ability.VOID_IMMUNE)) {
            display.println(location.getActor() + " entered the void"); // Print a message when an actor enters the Void.
            location.getActor().modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.UPDATE, Void.ZERO_HEALTH);
            display.println(location.getActor().unconscious(location.map())); // Mark the actor as unconscious due to natural causes.
        }
    }
}
