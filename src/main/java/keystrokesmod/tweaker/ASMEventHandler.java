//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.tweaker;

import keystrokesmod.module.modules.render.AntiShuffle;
import keystrokesmod.module.modules.combat.AutoClicker;
import keystrokesmod.module.modules.movement.KeepSprint;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.modules.other.NameHider;
import keystrokesmod.module.modules.movement.NoSlow;
import keystrokesmod.module.modules.combat.Reach;
import keystrokesmod.module.modules.player.SafeWalk;
import keystrokesmod.module.modules.other.StringEncrypt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Mouse;

public class ASMEventHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static String getUnformattedTextForChat(String s) {
      if (ModuleManager.nameHider.isEnabled()) {
         s = NameHider.getUnformattedTextForChat(s);
      }

      if (ModuleManager.antiShuffle.isEnabled()) {
         s = AntiShuffle.getUnformattedTextForChat(s);
      }

      if (ModuleManager.stringEncrypt.isEnabled()) {
         s = StringEncrypt.getUnformattedTextForChat(s);
      }

      return s;
   }

   public static boolean onEntityMove(Entity en) {
      if (en == mc.thePlayer && mc.thePlayer.onGround) {
         if (ModuleManager.safeWalk.isEnabled() && !SafeWalk.a.isToggled()) {
            if (SafeWalk.b.isToggled()) {
               ItemStack i = mc.thePlayer.getHeldItem();
               if (i == null || !(i.getItem() instanceof ItemBlock)) {
                  return mc.thePlayer.isSneaking();
               }
            }

            return true;
         } else {
            return mc.thePlayer.isSneaking();
         }
      } else {
         return false;
      }
   }

   public static void onLivingUpdate() {
      if (ModuleManager.noSlow.isEnabled()) {
         NoSlow.sl();
      } else {
         MovementInput movementInput = mc.thePlayer.movementInput;
         movementInput.moveStrafe *= 0.2F;
         movementInput = mc.thePlayer.movementInput;
         movementInput.moveForward *= 0.2F;
      }
   }

   public static void onAttackTargetEntityWithCurrentItem(Entity en) {
      if (ModuleManager.keepSprint.isEnabled()) {
         KeepSprint.sl(en);
      } else {
         EntityPlayerSP player = mc.thePlayer;
         player.motionX *= 0.6D;
         player = mc.thePlayer;
         player.motionZ *= 0.6D;
      }
   }

   public static void onTick() {
      if (!ModuleManager.autoClicker.isEnabled() || !AutoClicker.leftClick.isToggled() || !Mouse.isButtonDown(0) || !Reach.call()) {
         mc.entityRenderer.getMouseOver(1.0F);
      }
   }
}
