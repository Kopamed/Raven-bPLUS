package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Interface to be implemented in classes that will be registered as "JoinGameCallback" Event Handler.
 * @see net.arikia.dev.drpc.DiscordEventHandlers
 **/
public interface JoinGameCallback extends Callback {

	/**
	 * Method called when joining a game.
	 *
	 * @param joinSecret Unique String containing information needed to let the player join.
	 */
	void apply(String joinSecret);
}
