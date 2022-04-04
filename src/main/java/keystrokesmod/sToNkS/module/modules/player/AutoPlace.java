package keystrokesmod.sToNkS.module.modules.player;

import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import keystrokesmod.sToNkS.module.*;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.input.Mouse;

public class AutoPlace extends Module {
   public static ModuleDesc moduleDesc;
   public static ModuleSettingTick onlyHoldRight;
   public static ModuleSettingSlider delaySlider;
   private double delay = 0.0D;
   private long time = 0L;
   private int frame = 0;
   private MovingObjectPosition mouseObject = null;
   private BlockPos blockPos = null;

   public AutoPlace() {
      super("AutoPlace", Module.category.player, 0);
      this.registerSetting(moduleDesc = new ModuleDesc("FD: FPS/80"));
      this.registerSetting(delaySlider = new ModuleSettingSlider("Frame delay", 8.0D, 1.0D, 30.0D, 1.0D));
      this.registerSetting(onlyHoldRight = new ModuleSettingTick("Hold right", true));
   }

   public void guiUpdate() {
      if (this.delay != delaySlider.getInput()) {
         this.reset();
      }

      this.delay = delaySlider.getInput();
   }

   public void onDisable() {
      if (onlyHoldRight.isToggled()) {
         this.setRightClickDelay(4);
      }

      this.reset();
   }

   public void update() {
      Module fastPlace = ModuleManager.getModuleByClazz(FastPlace.class);
      if (onlyHoldRight.isToggled() && Mouse.isButtonDown(1) && !mc.thePlayer.capabilities.isFlying && fastPlace != null && !fastPlace.isEnabled()) {
         ItemStack item = mc.thePlayer.getHeldItem();
         if (item == null || !(item.getItem() instanceof ItemBlock)) {
            return;
         }

         this.setRightClickDelay(mc.thePlayer.motionY > 0 ? 1 : 1000);
      }
   }

   @FMLEvent
   public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
      if (Utils.Player.isPlayerInGame()) {
         if (mc.currentScreen == null && !mc.thePlayer.capabilities.isFlying) {
            ItemStack item = mc.thePlayer.getHeldItem();
            if (item != null && item.getItem() instanceof ItemBlock) {
               MovingObjectPosition objectMouseOver = mc.objectMouseOver;
               if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectType.BLOCK && objectMouseOver.sideHit != EnumFacing.UP && objectMouseOver.sideHit != EnumFacing.DOWN) {
                  if (this.mouseObject != null && this.frame < delaySlider.getInput()) {
                     ++this.frame;
                  } else {
                     this.mouseObject = objectMouseOver;
                     BlockPos pos = objectMouseOver.getBlockPos();
                     if (this.blockPos == null || pos.getX() != this.blockPos.getX() || pos.getY() != this.blockPos.getY() || pos.getZ() != this.blockPos.getZ()) {
                        Block block = mc.theWorld.getBlockState(pos).getBlock();
                        if (block != null && block != Blocks.air && !(block instanceof BlockLiquid)) {
                           if (!onlyHoldRight.isToggled() || Mouse.isButtonDown(1)) {
                              long currentTimeMillis = System.currentTimeMillis();
                              if (currentTimeMillis - this.time >= 25L) {
                                 this.time = currentTimeMillis;
                                 if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, item, pos, objectMouseOver.sideHit, objectMouseOver.hitVec)) {
                                    Utils.Client.setMouseButtonState(1, true);
                                    mc.thePlayer.swingItem();
                                    mc.getItemRenderer().resetEquippedProgress();
                                    Utils.Client.setMouseButtonState(1, false);
                                    this.blockPos = pos;
                                    this.frame = 0;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void setRightClickDelay(int value) {
      try {
         if (FastPlace.rightClickDelayTimerField != null) {
            FastPlace.rightClickDelayTimerField.set(mc, value);
         }
      } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {

      }
   }

   private void reset() {
      this.blockPos = null;
      this.mouseObject = null;
      this.frame = 0;
   }
}
