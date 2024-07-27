package game.items;

import edu.monash.fit2099.engine.items.Item;
import game.attributes.Ability;

/**
 * A class representing a Key item that can be dropped by an actor.
 * This item can be used to unlock doors.
 * @author Sarviin Hari
 * Modified by:
 * @author Choong Lee Ann
 */
public class OldKey extends Item {

    /**
     * Constructor to create a Key.
     * Initializes the name, display character, portability, and adds the UNLOCK_DOOR capability to the item.
     */
    public OldKey() {
        super("Old key", '-', true);
        this.addCapability(Ability.UNLOCK_DOOR);


    }

}
