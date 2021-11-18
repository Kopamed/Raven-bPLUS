//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.helper.utils;

import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.client.feature.module.modules.client.SelfDestruct;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatHelper {
   private static boolean e = false;
   private static long s = 0L;

   @SubscribeEvent
   public void onChatMessageRecieved(ClientChatReceivedEvent event) {
      if (e && Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
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
