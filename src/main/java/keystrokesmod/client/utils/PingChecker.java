package keystrokesmod.client.utils;

import keystrokesmod.client.clickgui.raven.Terminal;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PingChecker {
    private static boolean e;
    private static long s;

    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (e && Utils.Player.isPlayerInGame()) {
            if (Utils.Java.str(event.message.getUnformattedText()).startsWith("Unknown")) {
                event.setCanceled(true);
                e = false;
                this.getPing();
            }
        }
    }

    public static void checkPing() {
        Terminal.print("Checking...");
        if (e) {
            Terminal.print("Please wait.");
        } else {
            Utils.mc.thePlayer.sendChatMessage("/...");
            e = true;
            s = System.currentTimeMillis();
        }
    }

    private void getPing() {
        int ping = (int) (System.currentTimeMillis() - s) - 20;
        if (ping < 0) {
            ping = 0;
        }

        Terminal.print("Your ping: " + ping + "ms");
        reset();
    }

    public static void reset() {
        e = false;
        s = 0L;
    }
}
