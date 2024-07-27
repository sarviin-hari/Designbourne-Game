package game.actions.conversations;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;

/**
 * An interface for actions related to monologues during a conversation.
 * Implementing classes must provide methods to handle monologue actions.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Modified by:
 * @author Sarviin Hari
 * @version 1.0
 */
public interface MonologueAction {
    /**
     * Perform a monologue action, which can involve various interactions with an actor.
     *
     * @param player The actor initiating the monologue.
     * @return The action related to the monologue.
     */
    Action monologueList(Actor player);
    /**
     * Perform a standard monologue action.
     * This method is intended to be called without any actor-specific interactions.
     */
    void standardMonologue();
}
