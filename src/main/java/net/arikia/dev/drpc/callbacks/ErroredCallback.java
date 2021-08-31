package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Interface to be implemented in classes that will be registered as "ErroredCallback" Event Handler.
 * @see net.arikia.dev.drpc.DiscordEventHandlers
 **/
public interface ErroredCallback extends Callback {

	/**
	 * Method called when a error occurs.
	 *
	 * @param errorCode Error code returned.
	 * @param message   Message containing details about the error.
	 */
	void apply(int errorCode, String message);
}
