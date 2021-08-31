package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Interface to be implemented in classes that will be registered as "DisconnectedCallback" Event Handler.
 * @see net.arikia.dev.drpc.DiscordEventHandlers
 **/
public interface DisconnectedCallback extends Callback {

	/**
	 * Method called when disconnected.
	 *
	 * @param errorCode Error code returned on disconnection.
	 * @param message   Message containing details about the disconnection.
	 */
	void apply(int errorCode, String message);
}
