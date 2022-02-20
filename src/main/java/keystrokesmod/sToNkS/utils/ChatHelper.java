package keystrokesmod.sToNkS.utils;

import keystrokesmod.sToNkS.clickgui.raven.CommandLine;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;

public class ChatHelper {
   private static boolean e = false;
   private static long s = 0L;

   @FMLEvent
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
      CommandLine.print("§3Checking...", 1);
      if (e) {
         CommandLine.print("§cPlease wait.", 0);
      } else {
         Utils.mc.thePlayer.sendChatMessage("/...");
         e = true;
         s = System.currentTimeMillis();
      }
   }

   private void getPing() {
      int ping = (int)(System.currentTimeMillis() - s) - 20;
      if (ping < 0) {
         ping = 0;
      }

      CommandLine.print("Your ping: " + ping + "ms", 0);
      reset();
   }

   public static void reset() {
      e = false;
      s = 0L;
   }
}
