package net.arikia.dev.drpc;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.*;

/**
 * @author Nicolas "Vatuu" Adamoglou
 * @version 1.5.1
 * <p>
 * Java Wrapper of the Discord-RPC Library for Discord Rich Presence.
 */
public final class DiscordRPC {

	//DLL-Version for Update Check (soon).
	private static final String DLL_VERSION = "3.4.0";
	private static final String LIB_VERSION = "1.6.2";

	static {
		/*loadDLL();*/
	}

	/**
	 * Method to initialize the Discord-RPC.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param handlers      EventHandlers
	 * @param autoRegister  AutoRegister
	 */
	public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister) {
		DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, null);
	}

	/**
	 * Method to register the executable of the application/game.
	 * Only applicable when autoRegister in discordInitialize is false.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param command       Launch Command of the application/game.
	 */
	public static void discordRegister(String applicationId, String command) {
		DLL.INSTANCE.Discord_Register(applicationId, command);
	}

	/**
	 * Method to initialize the Discord-RPC within a Steam Application.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param handlers      EventHandlers
	 * @param autoRegister  AutoRegister
	 * @param steamId       SteamAppID
	 * @see DiscordEventHandlers
	 */
	public static void discordInitialize(String applicationId, DiscordEventHandlers handlers, boolean autoRegister, String steamId) {
		DLL.INSTANCE.Discord_Initialize(applicationId, handlers, autoRegister ? 1 : 0, steamId);
	}

	/**
	 * Method to register the Steam-Executable of the application/game.
	 * Only applicable when autoRegister in discordInitializeSteam is false.
	 *
	 * @param applicationId ApplicationID/ClientID
	 * @param steamId       SteamID of the application/game.
	 */
	public static void discordRegisterSteam(String applicationId, String steamId) {
		DLL.INSTANCE.Discord_RegisterSteamGame(applicationId, steamId);
	}

	/**
	 * Method to update the registered EventHandlers, after the initialization was
	 * already called.
	 *
	 * @param handlers DiscordEventHandler object with updated callbacks.
	 */
	public static void discordUpdateEventHandlers(DiscordEventHandlers handlers) {
		DLL.INSTANCE.Discord_UpdateHandlers(handlers);
	}

	/**
	 * Method to shutdown the Discord-RPC from within the application.
	 */
	public static void discordShutdown() {
		DLL.INSTANCE.Discord_Shutdown();
	}

	/**
	 * Method to call Callbacks from within the library.
	 * Must be called periodically.
	 */
	public static void discordRunCallbacks() {
		DLL.INSTANCE.Discord_RunCallbacks();
	}

	/**
	 * Method to update the DiscordRichPresence of the client.
	 *
	 * @param presence Instance of DiscordRichPresence
	 * @see DiscordRichPresence
	 */
	public static void discordUpdatePresence(DiscordRichPresence presence) {
		DLL.INSTANCE.Discord_UpdatePresence(presence);
	}

	/**
	 * Method to clear(and therefor hide) the DiscordRichPresence until a new
	 * presence is applied.
	 */
	public static void discordClearPresence() {
		DLL.INSTANCE.Discord_ClearPresence();
	}

	/**
	 * Method to respond to Join/Spectate Callback.
	 *
	 * @param userId UserID of the user to respond to.
	 * @param reply  DiscordReply to request.
	 * @see DiscordReply
	 */
	public static void discordRespond(String userId, DiscordReply reply) {
		DLL.INSTANCE.Discord_Respond(userId, reply.reply);
	}

	//Load DLL depending on the user's architecture.
	// seems to be broken on Mac OS
	/*
	private static void loadDLL() {
		String name = System.mapLibraryName("discord-rpc");
		OSUtil osUtil = new OSUtil();
		File homeDir;
		String finalPath;
		String tempPath;
		String dir;

		if (osUtil.isMac()) {
			homeDir = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator);
			dir = "darwin";
			tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
		} else if (osUtil.isWindows()) {
			homeDir = new File(System.getenv("TEMP"));
			boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
			dir = (is64bit ? "win-x64" : "win-x86");
			tempPath = homeDir + File.separator + "discord-rpc" + File.separator + name;
		} else {
			homeDir = new File(System.getProperty("user.home"), ".discord-rpc");
			dir = "linux";
			tempPath = homeDir + File.separator + name;
		}

		finalPath = "/" + dir + "/" + name;

		File f = new File(tempPath);

		try (InputStream in = DiscordRPC.class.getResourceAsStream(finalPath); OutputStream out = openOutputStream(f)) {
			copyFile(in, out);
			f.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.load(f.getAbsolutePath());
	}*/

	private static void copyFile(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[1024 * 4];
		int n;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
	}

	private static FileOutputStream openOutputStream(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (!file.canWrite()) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file);
	}

	//------------------------ Taken from apache commons ------------------------------//

	/**
	 * Enum containing reply codes for join request events.
	 *
	 * @see net.arikia.dev.drpc.callbacks.JoinRequestCallback
	 */
	public enum DiscordReply {
		/**
		 * Denies the join request immediately.
		 * Currently behaving the same way like DiscordReply.IGNORE.
		 */
		NO(0),
		/**
		 * Accepts the join request, requesting player received a JoinGameCallback.
		 *
		 * @see net.arikia.dev.drpc.callbacks.JoinGameCallback
		 */
		YES(1),
		/**
		 * Denies the join request by letting it time out(10s).
		 */
		IGNORE(2);

		/**
		 * Integer reply code send to Discord.
		 */
		public final int reply;

		DiscordReply(int reply) {
			this.reply = reply;
		}
	}

	//JNA Interface
	private interface DLL extends Library {
		//DLL INSTANCE = Native.load("discord-rpc", DLL.class);
		DLL INSTANCE = (DLL) Native.loadLibrary(JMRaichPatch.getLibName(), DLL.class);

		void Discord_Initialize(String applicationId, DiscordEventHandlers handlers, int autoRegister, String optionalSteamId);
		void Discord_Register(String applicationId, String command);
		void Discord_RegisterSteamGame(String applicationId, String steamId);
		void Discord_UpdateHandlers(DiscordEventHandlers handlers);
		void Discord_Shutdown();
		void Discord_RunCallbacks();
		void Discord_UpdatePresence(DiscordRichPresence presence);
		void Discord_ClearPresence();
		void Discord_Respond(String userId, int reply);
	}
}