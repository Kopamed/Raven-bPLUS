//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.player;

import keystrokesmod.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
   public NoFall() {
      super("NoFall", Module.category.player, 0);
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance > 2.5D) {
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }
}
