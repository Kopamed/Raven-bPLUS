package me.kopamed.raven.bplus.client.feature.module.modules.player;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {
   public NoFall() {
      super("NoFall", ModuleCategory.Player, 0);
   }

   public void update() {
      if ((double)mc.thePlayer.fallDistance > 2.5D) {
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      }

   }
}
