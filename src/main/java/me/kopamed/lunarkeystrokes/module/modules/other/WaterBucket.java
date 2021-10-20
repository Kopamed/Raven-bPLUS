//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.other;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class WaterBucket extends Module {
   public static Description a;
   private boolean handling;

   public WaterBucket() {
      super("Water bucket", Module.category.other, 0);
      this.registerSetting(a = new Description("Credits: aycy"));
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent ev) {
      if (ev.phase != Phase.END && Utils.Player.isPlayerInGame() && !mc.isGamePaused()) {
         if (this.inPosition() && this.holdWaterBucket()) {
            this.handling = true;
         }

         if (this.handling) {
            this.mlg();
            if (mc.thePlayer.onGround || mc.thePlayer.motionY > 0.0D) {
               this.reset();
            }
         }

      }
   }

   private boolean inPosition() {
      if (mc.thePlayer.motionY < -0.6D && !mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying && !mc.thePlayer.capabilities.isCreativeMode && !this.handling) {
         BlockPos playerPos = mc.thePlayer.getPosition();

         for(int i = 1; i < 3; ++i) {
            BlockPos blockPos = playerPos.down(i);
            Block block = mc.theWorld.getBlockState(blockPos).getBlock();
            if (block.isBlockSolid(mc.theWorld, blockPos, EnumFacing.UP)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean holdWaterBucket() {
      if (this.containsItem(mc.thePlayer.getHeldItem(), Items.water_bucket)) {
         return true;
      } else {
         for(int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
            if (this.containsItem(mc.thePlayer.inventory.mainInventory[i], Items.water_bucket)) {
               mc.thePlayer.inventory.currentItem = i;
               return true;
            }
         }

         return false;
      }
   }

   private void mlg() {
      ItemStack heldItem = mc.thePlayer.getHeldItem();
      if (this.containsItem(heldItem, Items.water_bucket) && mc.thePlayer.rotationPitch >= 70.0F) {
         MovingObjectPosition object = mc.objectMouseOver;
         if (object.typeOfHit == MovingObjectType.BLOCK && object.sideHit == EnumFacing.UP) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, heldItem);
         }
      }

   }

   private void reset() {
      ItemStack heldItem = mc.thePlayer.getHeldItem();
      if (this.containsItem(heldItem, Items.bucket)) {
         mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, heldItem);
      }

      this.handling = false;
   }

   private boolean containsItem(ItemStack itemStack, Item item) {
      return itemStack != null && itemStack.getItem() == item;
   }
}
