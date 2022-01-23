package keystrokesmod.module.modules.movement;

import keystrokesmod.module.Module;
import keystrokesmod.utils.Utils;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Sprint extends Module {
   public static ModuleSettingTick a;

   public Sprint() {
      super("Sprint", Module.category.movement, 0);
      a = new ModuleSettingTick("OmniSprint", false);
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
