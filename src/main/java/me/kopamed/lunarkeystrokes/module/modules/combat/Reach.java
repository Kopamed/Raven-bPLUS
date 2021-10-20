//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.combat;

import java.util.List;

import me.kopamed.lunarkeystrokes.module.*;
import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Reach extends Module {
   public static RangeSlider reach;
   public static Tick weapon_only;
   public static Tick moving_only;
   public static Tick sprint_only;
   public static Tick hit_through_blocks;

   public Reach() {
      super("Reach", Module.category.combat, 0);
      this.registerSetting(reach = new RangeSlider("Reach (Blocks)", 3.1, 3.3, 3, 6, 0.05));
      this.registerSetting(weapon_only = new Tick("Weapon only", false));
      this.registerSetting(moving_only = new Tick("Moving only", false));
      this.registerSetting(sprint_only = new Tick("Sprint only", false));
      this.registerSetting(hit_through_blocks = new Tick("Hit through blocks", false));
   }

   @SubscribeEvent
   public void onMouse(MouseEvent ev) {
      // legit event
      if(!Utils.Player.isPlayerInGame()) return;
      if (ModuleManager.autoClicker.isEnabled() && AutoClicker.leftClick.isToggled() && Mouse.isButtonDown(0)) return;
      if (ev.button >= 0 && ev.buttonstate) {
         call();
      }
   }

   @SubscribeEvent
   public void onRenderTick(TickEvent.RenderTickEvent ev) {
      // autoclick event
      if(!Utils.Player.isPlayerInGame()) return;

      if(!ModuleManager.autoClicker.isEnabled() || !AutoClicker.leftClick.isToggled()) return;

      if (ModuleManager.autoClicker.isEnabled() && AutoClicker.leftClick.isToggled() && Mouse.isButtonDown(0)){
         call();
      }
   }

   public static boolean call() {
      if (!Utils.Player.isPlayerInGame()) return false;
      if (weapon_only.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) return false;
      if (moving_only.isToggled() && (double)mc.thePlayer.moveForward == 0.0D && (double)mc.thePlayer.moveStrafing == 0.0D) return false;
      if (sprint_only.isToggled() && !mc.thePlayer.isSprinting()) return false;
      if (!hit_through_blocks.isToggled() && mc.objectMouseOver != null) {
         BlockPos p = mc.objectMouseOver.getBlockPos();
         if (p != null && mc.theWorld.getBlockState(p).getBlock() != Blocks.air) {
            return false;
         }
      }

      double r = Utils.Client.ranModuleVal(reach, Utils.Java.rand());
      Object[] o = zz(r, 0.0D);
      if (o == null) {
         return false;
      } else {
         Entity en = (Entity)o[0];
         mc.objectMouseOver = new MovingObjectPosition(en, (Vec3)o[1]);
         mc.pointedEntity = en;
         return true;
      }
   }

   private static Object[] zz(double zzD, double zzE) {
      if (!ModuleManager.reach.isEnabled()) {
         zzD = mc.playerController.extendedReach() ? 6.0D : 3.0D;
      }

      Entity entity1 = mc.getRenderViewEntity();
      Entity entity = null;
      if (entity1 == null) {
         return null;
      } else {
         mc.mcProfiler.startSection("pick");
         Vec3 eyes_positions = entity1.getPositionEyes(1.0F);
         Vec3 look = entity1.getLook(1.0F);
         Vec3 new_eyes_pos = eyes_positions.addVector(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD);
         Vec3 zz6 = null;
         List<Entity> zz8 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().addCoord(look.xCoord * zzD, look.yCoord * zzD, look.zCoord * zzD).expand(1.0D, 1.0D, 1.0D));
         double zz9 = zzD;

         for (Object o : zz8) {
            Entity zz11 = (Entity) o;
            if (zz11.canBeCollidedWith()) {
               float ex = (float) ((double) zz11.getCollisionBorderSize() * HitBox.exp(zz11));
               AxisAlignedBB zz13 = zz11.getEntityBoundingBox().expand(ex, ex, ex);
               zz13 = zz13.expand(zzE, zzE, zzE);
               MovingObjectPosition zz14 = zz13.calculateIntercept(eyes_positions, new_eyes_pos);
               if (zz13.isVecInside(eyes_positions)) {
                  if (0.0D < zz9 || zz9 == 0.0D) {
                     entity = zz11;
                     zz6 = zz14 == null ? eyes_positions : zz14.hitVec;
                     zz9 = 0.0D;
                  }
               } else if (zz14 != null) {
                  double zz15 = eyes_positions.distanceTo(zz14.hitVec);
                  if (zz15 < zz9 || zz9 == 0.0D) {
                     if (zz11 == entity1.ridingEntity) {
                        if (zz9 == 0.0D) {
                           entity = zz11;
                           zz6 = zz14.hitVec;
                        }
                     } else {
                        entity = zz11;
                        zz6 = zz14.hitVec;
                        zz9 = zz15;
                     }
                  }
               }
            }
         }

         if (zz9 < zzD && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
         }

         mc.mcProfiler.endSection();
         if (entity != null && zz6 != null) {
            return new Object[]{entity, zz6};
         } else {
            return null;
         }
      }
   }
}
