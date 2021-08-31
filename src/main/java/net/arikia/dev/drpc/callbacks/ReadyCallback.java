package net.arikia.dev.drpc.callbacks;

import com.sun.jna.Callback;
import net.arikia.dev.drpc.DiscordUser;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Interface to be implemented in classes that will be registered as "ReadyCallback" Event Handler.
 * @see net.arikia.dev.drpc.DiscordEventHandlers
 **/
public interface ReadyCallback extends Callback {

	/**
	 * Method called when the connection to Discord has been established.
	 *
	 * @param user Object containing all required information about the user executing the app.
	 * @see DiscordUser
	 **/
	void apply(DiscordUser user);
}

