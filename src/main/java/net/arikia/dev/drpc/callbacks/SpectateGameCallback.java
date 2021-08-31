package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Interface to be implemented in classes that will be registered as "SpectateGameCallback" Event Handler.
 * @see net.arikia.dev.drpc.DiscordEventHandlers
 **/
public interface SpectateGameCallback extends Callback {

	/**
	 * Method called when joining a game.
	 *
	 * @param spectateSecret Unique String containing information needed to let the player spectate.
	 */
	void apply(String spectateSecret);
}
