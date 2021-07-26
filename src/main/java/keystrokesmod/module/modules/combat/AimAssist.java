//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.util.Iterator;

import keystrokesmod.*;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.modules.world.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AimAssist extends Module {
   public static ModuleSettingSlider speed;
   public static ModuleSettingSlider fov;
   public static ModuleSettingSlider distance;
   public static ModuleSettingTick clickAim;
   public static ModuleSettingTick weaponOnly;
   public static ModuleSettingTick aimInvis;
   public static ModuleSettingTick blatantMode;

   public AimAssist() {
      super("AimAssist", Module.category.combat, 0);
      this.registerSetting(speed = new ModuleSettingSlider("Speed", 45.0D, 1.0D, 100.0D, 1.0D));
      this.registerSetting(fov = new ModuleSettingSlider("FOV", 90.0D, 15.0D, 180.0D, 1.0D));
      this.registerSetting(distance = new ModuleSettingSlider("Distance", 4.5D, 1.0D, 10.0D, 0.5D));
      this.registerSetting(clickAim = new ModuleSettingTick("Click aim", true));
      this.registerSetting(weaponOnly = new ModuleSettingTick("Weapon only", false));
      this.registerSetting(aimInvis = new ModuleSettingTick("Aim invis", false));
      this.registerSetting(blatantMode = new ModuleSettingTick("Blatant mode", false));
   }

   public void update() {
      if (mc.currentScreen == null && mc.inGameHasFocus) {
         if (!weaponOnly.isToggled() || ay.wpn()) {
            if (!clickAim.isToggled() || ay.ilc()) {
               Entity en = this.getEnemy();
               if (en != null) {
                  if (Ravenb3.debugger) {
                     ay.sm(this.getName() + " &e" + en.getName());
                  }

                  if (blatantMode.isToggled()) {
                     ay.aim(en, 0.0F, false);
                  } else {
                     double n = ay.n(en);
                     if (n > 1.0D || n < -1.0D) {
                        float val = (float)(-(n / (101.0D - speed.getInput())));
                        mc.thePlayer.rotationYaw += val;
                     }
                  }
               }

            }
         }
      }
   }

   public Entity getEnemy() {
      int fov = (int) AimAssist.fov.getInput();
      Iterator var2 = mc.theWorld.playerEntities.iterator();

      EntityPlayer en;
      do {
         do {
            do {
               do {
                  do {
                     do {
                        if (!var2.hasNext()) {
                           return null;
                        }

                        en = (EntityPlayer)var2.next();
                     } while(en == mc.thePlayer);
                  } while(en.deathTime != 0);
               } while(!aimInvis.isToggled() && en.isInvisible());
            } while((double)mc.thePlayer.getDistanceToEntity(en) > distance.getInput());
         } while(AntiBot.bot(en));
      } while(!blatantMode.isToggled() && !ay.fov(en, (float)fov));

      return en;
   }
}
