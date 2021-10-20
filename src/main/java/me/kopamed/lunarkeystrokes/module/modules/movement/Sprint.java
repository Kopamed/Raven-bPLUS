//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Sprint extends Module {
   public static Tick a;

   public Sprint() {
      super("Sprint", Module.category.movement, 0);
      a = new Tick("OmniSprint", false);
      this.registerSetting(a);
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if (Utils.Player.isPlayerInGame() && mc.inGameHasFocus) {
         EntityPlayerSP p = mc.thePlayer;
         if (a.isToggled()) {
            if (Utils.Player.isMoving() && p.getFoodStats().getFoodLevel() > 6) {
               p.setSprinting(true);
            }
         } else {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
         }
      }

   }
}
