//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.util.List;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
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
import org.lwjgl.input.Mouse;

public class Reach extends Module {
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   public static ModuleSetting c;
   public static ModuleSetting d;
   public static ModuleSetting e;
   public static ModuleSetting f;

   public Reach() {
      super("Reach", Module.category.combat, 0);
      this.registerSetting(a = new ModuleSetting2("Min", 3.1D, 3.0D, 6.0D, 0.05D));
      this.registerSetting(b = new ModuleSetting2("Max", 3.3D, 3.0D, 6.0D, 0.05D));
      this.registerSetting(c = new ModuleSetting("Weapon only", false));
      this.registerSetting(d = new ModuleSetting("Moving only", false));
      this.registerSetting(e = new ModuleSetting("Sprint only", false));
      this.registerSetting(f = new ModuleSetting("Hit through blocks", false));
   }

   public void guiUpdate() {
      ay.b(a, b);
   }

   @SubscribeEvent
   public void e(MouseEvent ev) {
      if (ev.button >= 0 && ev.buttonstate && ay.isPlayerInGame() && (!ModuleManager.autoClicker.isEnabled() || !AutoClicker.leftClick.isToggled() || !Mouse.isButtonDown(0))) {
         call();
      }
   }

   public static boolean call() {
      if (!ay.isPlayerInGame()) {
         return false;
      } else if (c.isToggled() && !ay.wpn()) {
         return false;
      } else if (d.isToggled() && (double)mc.thePlayer.moveForward == 0.0D && (double)mc.thePlayer.moveStrafing == 0.0D) {
         return false;
      } else if (e.isToggled() && !mc.thePlayer.isSprinting()) {
         return false;
      } else {
         if (!f.isToggled() && mc.objectMouseOver != null) {
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null && mc.theWorld.getBlockState(p).getBlock() != Blocks.air) {
               return false;
            }
         }

         double r = ay.mmVal(a, b, ay.rand());
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
   }

   private static Object[] zz(double zzD, double zzE) {
      if (!ModuleManager.reach.isEnabled()) {
         zzD = mc.playerController.extendedReach() ? 6.0D : 3.0D;
      }

      Entity zz2 = mc.getRenderViewEntity();
      Entity entity = null;
      if (zz2 == null) {
         return null;
      } else {
         mc.mcProfiler.startSection("pick");
         Vec3 zz3 = zz2.getPositionEyes(1.0F);
         Vec3 zz4 = zz2.getLook(1.0F);
         Vec3 zz5 = zz3.addVector(zz4.xCoord * zzD, zz4.yCoord * zzD, zz4.zCoord * zzD);
         Vec3 zz6 = null;
         List zz8 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(zz2, zz2.getEntityBoundingBox().addCoord(zz4.xCoord * zzD, zz4.yCoord * zzD, zz4.zCoord * zzD).expand(1.0D, 1.0D, 1.0D));
         double zz9 = zzD;

         for(int zz10 = 0; zz10 < zz8.size(); ++zz10) {
            Entity zz11 = (Entity)zz8.get(zz10);
            if (zz11.canBeCollidedWith()) {
               float ex = (float)((double)zz11.getCollisionBorderSize() * HitBox.exp(zz11));
               AxisAlignedBB zz13 = zz11.getEntityBoundingBox().expand((double)ex, (double)ex, (double)ex);
               zz13 = zz13.expand(zzE, zzE, zzE);
               MovingObjectPosition zz14 = zz13.calculateIntercept(zz3, zz5);
               if (zz13.isVecInside(zz3)) {
                  if (0.0D < zz9 || zz9 == 0.0D) {
                     entity = zz11;
                     zz6 = zz14 == null ? zz3 : zz14.hitVec;
                     zz9 = 0.0D;
                  }
               } else if (zz14 != null) {
                  double zz15 = zz3.distanceTo(zz14.hitVec);
                  if (zz15 < zz9 || zz9 == 0.0D) {
                     if (zz11 == zz2.ridingEntity) {
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
