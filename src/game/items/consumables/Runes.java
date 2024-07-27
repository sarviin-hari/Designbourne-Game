package game.items.consumables;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.consumeitems.ConsumeAbility;
import game.actions.consumeitems.ConsumeAction;
import game.attributes.Status;

/**
 * A class representing a Runes item that can be dropped by an actor.
 * This item can be used to increase an actor's wallet balance.
 *
 * @author Choong Lee Ann
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public class Runes extends Item implements ConsumeAbility {

    /**
     * The value of Runes
     */
    private int cost;

    /**
     * Constructor to create a Rune.
     * Initializes the name, display character, and portability of the item.
     *
     * @param amount The amount of money represented by the Runes.
     */
    public Runes(int amount) {
        super("Runes", '$', true);
        this.cost = amount;
        this.addCapability(Status.DESTROY_IF_ON_GROUND);

    }

    /**
     * Returns a list of allowable actions that the Runes can perform for the owner actor.
     * It includes the ConsumeRunesAction to perform a wallet balance increase action on the actor.
     *
     * @param owner The actor who owns the Runes.
     * @return A list of allowable actions for the owner actor.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = new ActionList();
        actionList.add(new ConsumeAction(this));
        return actionList;
    }

    /**
     * Get the cost (value) of the Runes.
     *
     * @return The cost of the Runes.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Represents the action of an actor consuming the Runes.
     *
     * @param actor the actor that is consuming the Runes.
     * @param map   the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    @Override
    public String consumeItem(Actor actor, GameMap map) {
        int runesAmount = getCost();
        actor.addBalance(runesAmount);
        actor.removeItemFromInventory(this);

        return "Actor's balance increased by " + getCost();

    }
}
