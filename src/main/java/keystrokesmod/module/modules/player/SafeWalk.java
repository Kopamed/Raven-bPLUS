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
   public static ModuleSetting doSneak;
   public static ModuleSetting blocksOnly;
   public static ModuleSetting sneakOnJump;
   public static ModuleSetting onHold;
   private static boolean shouldBridge = false;
   private static boolean isSneaking = false;

   public SafeWalk() {
      super("SafeWalk", Module.category.player, 0);
      this.registerSetting(doSneak = new ModuleSetting("Sneak", false));
      this.registerSetting(sneakOnJump = new ModuleSetting("Sneak during jumps", false));
      this.registerSetting(onHold = new ModuleSetting("On sneak hold", false));
      this.registerSetting(blocksOnly = new ModuleSetting("Blocks only", true));
   }

   public void onDisable() {
      if (doSneak.isToggled() && ay.playerOverAir()) {
         this.setSneak(false);
      }

      shouldBridge = false;
      isSneaking = false;
   }

   @SubscribeEvent
   public void p(PlayerTickEvent e) {
      if (ay.isPlayerInGame() && doSneak.isToggled()) {
         if (onHold.isToggled()) {
            if  (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()))
               return;
         }
         if (mc.thePlayer.onGround) {
            if (ay.playerOverAir()) {
               if (blocksOnly.isToggled()) {
                  ItemStack i = mc.thePlayer.getHeldItem();
                  if (i == null || !(i.getItem() instanceof ItemBlock)) {
                     if (isSneaking) {
                        isSneaking = false;
                        this.setSneak(false);
                     }

                     return;
                  }
               }

               isSneaking = true;
               this.setSneak(true);
               shouldBridge = true;
            } else if (isSneaking) {
               isSneaking = false;
               this.setSneak(false);
            }
         }

         if (shouldBridge && mc.thePlayer.capabilities.isFlying) {
            this.setSneak(false);
            shouldBridge = false;
         }

         if (shouldBridge && ay.playerOverAir() && sneakOnJump.isToggled()) {
            isShifting = true;
            this.setShift(true);
         }
      }
   }

   private void setSneak(boolean sh) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
   }
}
