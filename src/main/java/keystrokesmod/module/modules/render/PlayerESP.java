package keystrokesmod.module.modules.render;

import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Iterator;

public class PlayerESP extends Module {
   public static ModuleDesc g;
   public static ModuleSettingSlider a;
   public static ModuleSettingSlider b;
   public static ModuleSettingSlider c;
   public static ModuleSettingSlider i;
   public static ModuleSettingSlider j;
   public static ModuleSettingTick d;
   public static ModuleSettingTick f;
   public static ModuleSettingTick h;
   public static ModuleSettingTick t1;
   public static ModuleSettingTick t2;
   public static ModuleSettingTick t3;
   public static ModuleSettingTick t4;
   public static ModuleSettingTick t5;
   public static ModuleSettingTick t6;
   private int rgb_c = 0;

   public PlayerESP() {
      super("PlayerESP", Module.category.render, 0);
      this.registerSetting(a = new ModuleSettingSlider("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(b = new ModuleSettingSlider("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new ModuleSettingSlider("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new ModuleSettingTick("Rainbow", false));
      this.registerSetting(g = new ModuleDesc("ESP Types"));
      this.registerSetting(t3 = new ModuleSettingTick("2D", false));
      this.registerSetting(t5 = new ModuleSettingTick("Arrow", false));
      this.registerSetting(t1 = new ModuleSettingTick("Box", false));
      this.registerSetting(t4 = new ModuleSettingTick("Health", true));
      this.registerSetting(t6 = new ModuleSettingTick("Ring", false));
      this.registerSetting(t2 = new ModuleSettingTick("Shaded", false));
      this.registerSetting(i = new ModuleSettingSlider("Expand", 0.0D, -0.3D, 2.0D, 0.1D));
      this.registerSetting(j = new ModuleSettingSlider("X-Shift", 0.0D, -35.0D, 10.0D, 1.0D));
      this.registerSetting(f = new ModuleSettingTick("Show invis", true));
      this.registerSetting(h = new ModuleSettingTick("Red on damage", true));
   }

   public void onDisable() {
      Utils.HUD.ring_c = false;
   }

   public void guiUpdate() {
      this.rgb_c = (new Color((int)a.getInput(), (int)b.getInput(), (int)c.getInput())).getRGB();
   }

   @SubscribeEvent
   public void r1(RenderWorldLastEvent e) {
      if (Utils.Player.isPlayerInGame()) {
         int rgb = d.isToggled() ? 0 : this.rgb_c;
         Iterator var3;
         if (Ravenbplus.debugger) {
            var3 = mc.theWorld.loadedEntityList.iterator();

            while(var3.hasNext()) {
               Entity en = (Entity)var3.next();
               if (en instanceof EntityLivingBase && en != mc.thePlayer) {
                  this.r(en, rgb);
               }
            }

         } else {
            var3 = mc.theWorld.playerEntities.iterator();

            while(true) {
               EntityPlayer en;
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        en = (EntityPlayer)var3.next();
                     } while(en == mc.thePlayer);
                  } while(en.deathTime != 0);
               } while(!f.isToggled() && en.isInvisible());

               if (!AntiBot.bot(en)) {
                  this.r(en, rgb);
               }
            }
         }
      }
   }

   private void r(Entity en, int rgb) {
      if (t1.isToggled()) {
         Utils.HUD.ee(en, 1, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t2.isToggled()) {
         Utils.HUD.ee(en, 2, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t3.isToggled()) {
         Utils.HUD.ee(en, 3, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t4.isToggled()) {
         Utils.HUD.ee(en, 4, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t5.isToggled()) {
         Utils.HUD.ee(en, 5, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t6.isToggled()) {
         Utils.HUD.ee(en, 6, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

   }
}
