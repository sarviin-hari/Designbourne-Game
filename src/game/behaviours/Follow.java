package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * An interface representing the ability to perform a follow behavior.
 *
 * Authored by:
 * @author Choong Lee Ann
 * Version: 1.0
 */
public interface Follow {

    /**
     * Defines the behavior for following the specified target.
     *
     * @param target The target actor to follow.
     */
    void followBehaviour(Actor target);
}
