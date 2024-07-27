package game.actions.consumeitems;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

public class ConsumeAction extends Action {

    /**
     * The ConsumeAbility associated with this ConsumeAction.
     */
    private ConsumeAbility consumeAbility;

    /**
     * Constructor for ConsumeAction.
     *
     * @param consumeAbility The ConsumeAbility associated with the action.
     */
    public ConsumeAction(ConsumeAbility consumeAbility){
        this.consumeAbility = consumeAbility;
    }
    /**
     * Perform the ConsumeAction.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened (the result of the action being performed) that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return consumeAbility.consumeItem(actor, map);
    }

    /**
     * Get the description of the action to be displayed on the menu.
     *
     * @param actor The actor performing the action.
     * @return the action description to be displayed on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consume " + consumeAbility;
    }
}
