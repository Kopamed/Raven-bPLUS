//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.player;

import keystrokesmod.module.Module;
import keystrokesmod.ay;
import keystrokesmod.module.ModuleSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class SafeWalk extends Module {
   public static ModuleSetting a;
   public static ModuleSetting b;
   private static boolean c = false;
   private static boolean d = false;

   public SafeWalk() {
      super(new char[]{'S', 'a', 'f', 'e', 'W', 'a', 'l', 'k'}, Module.category.player, 0);
      this.registerSetting(a = new ModuleSetting(new char[]{'S', 'h', 'i', 'f', 't'}, false));
      this.registerSetting(b = new ModuleSetting(new char[]{'B', 'l', 'o', 'c', 'k', 's', ' ', 'o', 'n', 'l', 'y'}, true));
   }

   public void onDisable() {
      if (a.isToggled() && ay.eob()) {
         this.sh(false);
      }

      c = false;
      d = false;
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if (ay.isPlayerInGame() && a.isToggled()) {
         if (mc.thePlayer.onGround) {
            if (ay.eob()) {
               if (b.isToggled()) {
                  ItemStack i = mc.thePlayer.getHeldItem();
                  if (i == null || !(i.getItem() instanceof ItemBlock)) {
                     if (d) {
                        d = false;
                        this.sh(false);
                     }

                     return;
                  }
               }

               d = true;
               this.sh(true);
               c = true;
            } else if (d) {
               d = false;
               this.sh(false);
            }
         }

         if (c && mc.thePlayer.capabilities.isFlying) {
            this.sh(false);
            c = false;
         }

      }
   }

   private void sh(boolean sh) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
   }
}
