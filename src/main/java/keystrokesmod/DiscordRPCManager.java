package keystrokesmod;

import keystrokesmod.main.Ravenbplus;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class DiscordRPCManager {

    private Thread rpc_thread;

    public void init_rpc() {
        initRavenRPC();
    }

    public void shutdown_rpc() {
        if (rpc_thread != null)
            rpc_thread.interrupt();
        DiscordRPC.discordClearPresence();
    }

    private void initRavenRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Starting RPC on : " + user.username + "#" + user.discriminator);
        }).build();
        DiscordRPC.discordInitialize("880735714805968896", handlers, true);
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
        DiscordRPC.discordUpdatePresence(presence);
        (rpc_thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(3000);
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
