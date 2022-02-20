package keystrokesmod.sToNkS.module.modules.player;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AutoJump extends Module {
   public static ModuleSettingTick b;
   private boolean c = false;

   public AutoJump() {
      super("AutoJump", Module.category.player, 0);
      this.registerSetting(b = new ModuleSettingTick("Cancel when shifting", true));
   }

   public void onDisable() {
      this.ju(this.c = false);
   }

   @FMLEvent
   public void p(PlayerTickEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         if (mc.thePlayer.onGround && (!b.isToggled() || !mc.thePlayer.isSneaking())) {
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX / 3.0D, -1.0D, mc.thePlayer.motionZ / 3.0D)).isEmpty()) {
               this.ju(this.c = true);
            } else if (this.c) {
               this.ju(this.c = false);
            }
         } else if (this.c) {
            this.ju(this.c = false);
         }

      }
   }

   private void ju(boolean ju) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), ju);
   }
}
