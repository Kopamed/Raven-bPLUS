package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import keystrokesmod.client.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Sprint extends Module {
   public static TickSetting a;

   public Sprint() {
      super("Sprint", Module.category.movement, 0);
      a = new TickSetting("OmniSprint", false);
      this.registerSetting(a);
   }

   @FMLEvent
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
