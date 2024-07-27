package game.actions.consumeitems;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for actions that represent an actor's ability to consume items.
 */
public interface ConsumeAbility {

    /**
     * Represents the action of an actor consuming an item.
     *
     * @param actor the actor that is consuming the item.
     * @param map the GameMap on which the action is performed.
     * @return a message or description of the consumption action.
     */
    String consumeItem(Actor actor, GameMap map);
}
