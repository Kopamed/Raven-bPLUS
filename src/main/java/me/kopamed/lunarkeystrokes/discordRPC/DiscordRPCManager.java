package me.kopamed.lunarkeystrokes.discordRPC;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.modules.other.DiscordRPCModule;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class DiscordRPCManager {
    public Thread rpc_thread;
    private RPCMode rpcMode = RPCMode.RAVEN_BP;

    public void init_rpc(RPCMode rpcMode) {
        this.rpcMode = rpcMode;
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.discordInitialize(rpcMode.appID, handlers, true, "");
    }

    public void updateRPCMode(RPCMode rpcMode) {
        try { // 300ms of cooldown to prevent rpc from freezing
            if (rpc_thread != null)
                rpc_thread.interrupt();
            Thread.sleep(100);
            DiscordRPC.discordShutdown();

            Thread.sleep(100); // wait 0.1s
            this.rpcMode = rpcMode;
            DiscordRPC.discordInitialize(rpcMode.appID, new DiscordEventHandlers(), true, "");

            Thread.sleep(100); // wait 0.1s

            if (Module.getModule(DiscordRPCModule.class).isEnabled()) {
                this.updateRPC();
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void updateRPC() {
        DiscordRPC.discordClearPresence();
        if (rpc_thread != null) {
            rpc_thread.interrupt();
        }

        switch (rpcMode) {
            case BADLION:
                updateBadlionRPC();
                break;
            case LUNAR:
                updateLunarRPC();
                break;
            case MINECRAFT:
                updateMinecraftRPC();
                break;
            case RAVEN_BP:
                updateRavenRPC();
                break;
        }
    }


    // =========== EVENTS ===========
    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {
        if (event.entity == Minecraft.getMinecraft().thePlayer) {
            if (this.rpcMode == RPCMode.RAVEN_BP) {
                updateRavenRPC();
            }
        }
    }

    @SubscribeEvent
    public void onLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            if (this.rpcMode == RPCMode.RAVEN_BP) {
                updateRavenRPC("In menus", null);
            }
        }
    }



    /*=====================================
      ==            RAVEN RPC            ==
      =====================================*/
    public void updateRavenRPC() {
        if (this.rpcMode == RPCMode.RAVEN_BP) {
            if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
                int toggled_module = 0;
                for (int i = 0; i < Ravenbplus.notAName.getm0dmanager().getModules().size(); i++) {
                    if (Ravenbplus.notAName.getm0dmanager().getModules().get(i).isEnabled()) toggled_module++;
                }
                updateRavenRPC("In game", toggled_module + "/" + Ravenbplus.notAName.getm0dmanager().getModules().size() + " Modules activated");
            } else {
                updateRavenRPC("Playing Minecraft", null);
            }
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


    /*====================================
     ==          MINECRAFT RPC          ==
     =====================================*/
    // as far as I know, it doesn't do anything else
    private void updateMinecraftRPC() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "Playing Minecraft";
        presence.largeImageKey = "minecraft";
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

    /*=====================================
      ==            LUNAR RPC            ==
      =====================================*/
    // as far as I know, it doesn't do anything else
    private void updateLunarRPC() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "Playing Minecraft 1.8.9";
        presence.largeImageKey = "lunar";
        presence.largeImageText = "Lunar Client";
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


    /*=====================================
      ==           BADLION RPC           ==
      =====================================*/
    // as far as I know, it doesn't do anything else
    private void updateBadlionRPC() {
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.details = "Playing Minecraft 1.8.9";
        presence.largeImageKey = "badlion";
        presence.largeImageText = "Using Badlion Client Minecraft Launcher";
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
}
