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
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
   public static ModuleSetting doShift;
   public static ModuleSetting blocksOnly;
   public static ModuleSetting shiftOnJump;
   public static ModuleSetting onHold;
   private static boolean shouldBridge = false;
   private static boolean isShifting = false;

   public SafeWalk() {
      super("SafeWalk", Module.category.player, 0);
      this.registerSetting(doShift = new ModuleSetting("Shift", false));
      this.registerSetting(shiftOnJump = new ModuleSetting("Shift during jumps", false));
      this.registerSetting(onHold = new ModuleSetting("On shift hold", false));
      this.registerSetting(blocksOnly = new ModuleSetting("Blocks only", true));
   }

   public void onDisable() {
      if (doShift.isToggled() && ay.playerOverAir()) {
         this.setShift(false);
      }

      shouldBridge = false;
      isShifting = false;
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if (ay.isPlayerInGame() && doShift.isToggled()) {
         if (onHold.isToggled()) {
            if  (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
               return;
         }
         if (mc.thePlayer.onGround) {
            if (ay.playerOverAir()) {
               if (blocksOnly.isToggled()) {
                  ItemStack i = mc.thePlayer.getHeldItem();
                  if (i == null || !(i.getItem() instanceof ItemBlock)) {
                     if (isShifting) {
                        isShifting = false;
                        this.setShift(false);
                     }

                     return;
                  }
               }

               isShifting = true;
               this.setShift(true);
               shouldBridge = true;
            } else if (isShifting) {
               isShifting = false;
               this.setShift(false);
            }
         }

         if (shouldBridge && mc.thePlayer.capabilities.isFlying) {
            this.setShift(false);
            shouldBridge = false;
         }

         if (shouldBridge && ay.playerOverAir() && shiftOnJump.isToggled()) {
            isShifting = true;
            this.setShift(true);
         }
      }
   }

   private void setShift(boolean sh) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
   }
}
