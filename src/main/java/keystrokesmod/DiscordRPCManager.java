package keystrokesmod;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import keystrokesmod.main.Ravenbplus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class DiscordRPCManager {
    private final DiscordRPC raven_rpc = DiscordRPC.INSTANCE;

    private Thread rpc_thread;


    public void init_rpc() {
        initRavenRPC();
    }

    public void shutdown_rpc() {
        rpc_thread.interrupt();
        raven_rpc.Discord_ClearPresence();
    }

    private void initRavenRPC() {
        String applicationId = "880735714805968896";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        raven_rpc.Discord_Initialize(applicationId, handlers, true, "");
    }

    public void updateRavenRPC() {
        if (Minecraft.getMinecraft().theWorld != null) {
            updateRPC_ingame();
        } else {
            updateRavenRPC("Playing Minecraft", null);
        }
    }

    private void updateRavenRPC(String details, String state) {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = details;
        if (state != null && !state.equals("")) presence.state = state;
        presence.largeImageKey = "raven";
        raven_rpc.Discord_UpdatePresence(presence);
        (rpc_thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                raven_rpc.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler")).start();
    }


    private void updateRPC_ingame() {
        int toggled_module = 0;
        for (int i = 0; i < Ravenbplus.notAName.getm0dmanager().listofmods().size(); i++) {
            if (Ravenbplus.notAName.getm0dmanager().listofmods().get(i).isEnabled()) toggled_module++;
        }
        updateRavenRPC("In game", toggled_module + "/" + Ravenbplus.notAName.getm0dmanager().listofmods().size() + " Modules activated");
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (event.entity == Minecraft.getMinecraft().thePlayer) {
            updateRPC_ingame();
        }
    }

    @SubscribeEvent
    public void onLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            updateRavenRPC("In menus", null);
        }
    }
}
