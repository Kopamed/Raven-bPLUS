package keystrokesmod.sToNkS.module.modules.player;

import keystrokesmod.sToNkS.main.Ravenbplus;
import keystrokesmod.sToNkS.module.*;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class SafeWalk extends Module {
   public static ModuleSettingTick doShift;
   public static ModuleSettingTick blocksOnly;
   public static ModuleSettingTick shiftOnJump;
   public static ModuleSettingTick onHold;
   public static ModuleSettingTick showBlockAmount;
   public static ModuleSettingTick lookDown;
   public static ModuleSettingDoubleSlider pitchRange;
   public static ModuleSettingSlider blockShowMode;
   public static ModuleDesc blockShowModeDesc;
   private static boolean shouldBridge = false;
   private static boolean isShifting = false;

   public SafeWalk() {
      super("SafeWalk", Module.category.player, 0);
      this.registerSetting(doShift = new ModuleSettingTick("Shift", false));
      this.registerSetting(shiftOnJump = new ModuleSettingTick("Shift during jumps", false));
      this.registerSetting(onHold = new ModuleSettingTick("On shift hold", false));
      this.registerSetting(blocksOnly = new ModuleSettingTick("Blocks only", true));
      this.registerSetting(showBlockAmount = new ModuleSettingTick("Show amount of blocks", true));
      this.registerSetting(blockShowMode = new ModuleSettingSlider("Block display info:", 2D, 1D, 2D, 1D));
      this.registerSetting(blockShowModeDesc = new ModuleDesc("Mode: "));
      this.registerSetting(lookDown = new ModuleSettingTick("Only when looking down", true));
      this.registerSetting(pitchRange = new ModuleSettingDoubleSlider("Pitch min range:", 70D, 85, 0D, 90D, 1D));
   }

   public void onDisable() {
      if (doShift.isToggled() && Utils.Player.playerOverAir()) {
         this.setShift(false);
      }

      shouldBridge = false;
      isShifting = false;
   }

   public void guiUpdate() {
      blockShowModeDesc.setDesc(Utils.md + BlockAmountInfo.values()[(int)blockShowMode.getInput() - 1]);
   }

   @FMLEvent
   public void p(PlayerTickEvent e) {
      if(!Utils.Client.currentScreenMinecraft())
         return;

      if (!Utils.Player.isPlayerInGame()) {
         return;
      }
      if(doShift.isToggled()) {
         if(lookDown.isToggled()) {
            if(mc.thePlayer.rotationPitch < pitchRange.getInputMin() || mc.thePlayer.rotationPitch > pitchRange.getInputMax()) {
               shouldBridge = false;
               if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                  setShift(true);
               }
               return;
            }
         }
         if (onHold.isToggled()) {
            if  (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
               shouldBridge = false;
               return;
            }
         }
         if (mc.thePlayer.onGround) {
            if (Utils.Player.playerOverAir()) {
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
            }
            else if (mc.thePlayer.isSneaking() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && onHold.isToggled()) {
               isShifting = false;
               shouldBridge = false;
               this.setShift(false);
            }
            else if(onHold.isToggled() && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
               isShifting = false;
               shouldBridge = false;
               this.setShift(false);
            }
            else if(mc.thePlayer.isSneaking() && (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && onHold.isToggled())) {
               isShifting = false;
               this.setShift(false);
               shouldBridge = true;
            }
            else if(mc.thePlayer.isSneaking() && !onHold.isToggled()) {
               isShifting = false;
               this.setShift(false);
               shouldBridge = true;
            }
         }

         else if (shouldBridge && mc.thePlayer.capabilities.isFlying) {
            this.setShift(false);
            shouldBridge = false;
         }

         else if (shouldBridge && Utils.Player.playerOverAir() && shiftOnJump.isToggled()) {
            isShifting = true;
            this.setShift(true);
         } else {
            // rn we are in the air and we are not flying, meaning that we are in a jump. and since shiftOnJump is turned off, we just unshift and uhh... nyoooom
            isShifting = false;
            this.setShift(false);
         }
      }
   }

   @FMLEvent
   public void onRenderTick(TickEvent.RenderTickEvent e) {
      if(!showBlockAmount.isToggled() || !Utils.Player.isPlayerInGame()) return;
      if (e.phase == TickEvent.Phase.END) {
         if (mc.currentScreen == null) {
            if(shouldBridge) {
               ScaledResolution res = new ScaledResolution(mc);

               int totalBlocks = 0;
               if(BlockAmountInfo.values()[(int)blockShowMode.getInput() - 1] == BlockAmountInfo.BLOCKS_IN_CURRENT_STACK) {
                  totalBlocks = Utils.Player.getBlockAmountInCurrentStack(mc.thePlayer.inventory.currentItem);
               } else {
                  for (int slot = 0; slot < 36; slot++){
                     totalBlocks += Utils.Player.getBlockAmountInCurrentStack(slot);
                  }
               }

               if(totalBlocks <= 0){
                  return;
               }

               int rgb;
               if (totalBlocks < 16.0D) {
                  rgb = Color.red.getRGB();
               } else if (totalBlocks < 32.0D) {
                  rgb = Color.orange.getRGB();
               } else if (totalBlocks < 128.0D) {
                  rgb = Color.yellow.getRGB();
               } else if (totalBlocks > 128.0D) {
                  rgb = Color.green.getRGB();
               } else {
                  rgb = Color.black.getRGB();
               }

               String t = totalBlocks + " blocks";
               int x = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2;
               int y;
               if(Ravenbplus.debugger) {
                  y = res.getScaledHeight() / 2 + 17 + mc.fontRendererObj.FONT_HEIGHT;
               } else {
                  y = res.getScaledHeight() / 2 + 15;
               }
               mc.fontRendererObj.drawString(t, (float)x, (float)y, rgb, false);
            }
         }
      }
   }

   private void setShift(boolean sh) {
      KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
   }

   public enum BlockAmountInfo {
      BLOCKS_IN_TOTAL,
      BLOCKS_IN_CURRENT_STACK
   }
}
