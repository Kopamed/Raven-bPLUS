//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.fun;

import java.util.Iterator;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.utils.ay;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;

public class Fun {
   public static class Spin extends Module {
      public static ModuleSettingSlider a;
      public static ModuleSettingSlider b;
      private float yaw;

      public Spin() {
         super("Spin", Module.category.fun, 0);
         this.registerSetting(a = new ModuleSettingSlider("Rotation", 360.0D, 30.0D, 360.0D, 1.0D));
         this.registerSetting(b = new ModuleSettingSlider("Speed", 25.0D, 1.0D, 60.0D, 1.0D));
      }

      public void onEnable() {
         this.yaw = mc.thePlayer.rotationYaw;
      }

      public void onDisable() {
         this.yaw = 0.0F;
      }

      public void update() {
         double left = (double)this.yaw + a.getInput() - (double)mc.thePlayer.rotationYaw;
         EntityPlayerSP var10000;
         if (left < b.getInput()) {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + left);
            this.disable();
         } else {
            var10000 = mc.thePlayer;
            var10000.rotationYaw = (float)((double)var10000.rotationYaw + b.getInput());
            if ((double)mc.thePlayer.rotationYaw >= (double)this.yaw + a.getInput()) {
               this.disable();
            }
         }

      }
   }

   public static class SlyPort extends Module {
      public static ModuleDesc f;
      public static ModuleSettingSlider r;
      public static ModuleSettingTick b;
      public static ModuleSettingTick d;
      public static ModuleSettingTick e;
      private final boolean s = false;

      public SlyPort() {
         super("SlyPort", Module.category.fun, 0);
         this.registerSetting(f = new ModuleDesc("Teleport behind enemies."));
         this.registerSetting(r = new ModuleSettingSlider("Range", 6.0D, 2.0D, 15.0D, 1.0D));
         this.registerSetting(e = new ModuleSettingTick("Aim", true));
         this.registerSetting(b = new ModuleSettingTick("Play sound", true));
         this.registerSetting(d = new ModuleSettingTick("Players only", true));
      }

      public void onEnable() {
         Entity en = this.ge();
         if (en != null) {
            this.tp(en);
         }

         this.disable();
      }

      private void tp(Entity en) {
         if (b.isToggled()) {
            mc.thePlayer.playSound("mob.endermen.portal", 1.0F, 1.0F);
         }

         Vec3 vec = en.getLookVec();
         double x = en.posX - vec.xCoord * 2.5D;
         double z = en.posZ - vec.zCoord * 2.5D;
         mc.thePlayer.setPosition(x, mc.thePlayer.posY, z);
         if (e.isToggled()) {
            ay.aim(en, 0.0F, false);
         }

      }

      private Entity ge() {
         Entity en = null;
         double r = Math.pow(SlyPort.r.getInput(), 2.0D);
         double dist = r + 1.0D;
         Iterator var6 = mc.theWorld.loadedEntityList.iterator();

         while(true) {
            Entity ent;
            do {
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           return en;
                        }

                        ent = (Entity)var6.next();
                     } while(ent == mc.thePlayer);
                  } while(!(ent instanceof EntityLivingBase));
               } while(((EntityLivingBase)ent).deathTime != 0);
            } while(SlyPort.d.isToggled() && !(ent instanceof EntityPlayer));

            if (!AntiBot.bot(ent)) {
               double d = mc.thePlayer.getDistanceSqToEntity(ent);
               if (!(d > r) && !(dist < d)) {
                  dist = d;
                  en = ent;
               }
            }
         }
      }
   }

   public static class FlameTrail extends Module {
      public static ModuleSettingSlider a;

      public FlameTrail() {
         super("Flame Trail", Module.category.fun, 0);
      }

      public void update() {
         Vec3 vec = mc.thePlayer.getLookVec();
         double x = mc.thePlayer.posX - vec.xCoord * 2.0D;
         double y = mc.thePlayer.posY + ((double)mc.thePlayer.getEyeHeight() - 0.2D);
         double z = mc.thePlayer.posZ - vec.zCoord * 2.0D;
         mc.thePlayer.worldObj.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D, 0);
      }
   }

   public static class ExtraBobbing extends Module {
      public static ModuleSettingSlider a;
      private boolean b;

      public ExtraBobbing() {
         super("Extra Bobbing", Module.category.fun, 0);
         this.registerSetting(a = new ModuleSettingSlider("Level", 1.0D, 0.0D, 8.0D, 0.1D));
      }

      public void onEnable() {
         this.b = mc.gameSettings.viewBobbing;
         if (!this.b) {
            mc.gameSettings.viewBobbing = true;
         }

      }

      public void onDisable() {
         mc.gameSettings.viewBobbing = this.b;
      }

      public void update() {
         if (!mc.gameSettings.viewBobbing) {
            mc.gameSettings.viewBobbing = true;
         }

         if (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.cameraYaw = (float)((double)var10000.cameraYaw + a.getInput() / 2.0D);
         }

      }
   }
}
