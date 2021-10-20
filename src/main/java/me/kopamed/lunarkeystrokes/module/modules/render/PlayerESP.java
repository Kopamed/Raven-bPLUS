//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerESP extends Module {
   public static Description g;
   public static Slider a;
   public static Slider b;
   public static Slider c;
   public static Slider i;
   public static Slider j;
   public static Tick d;
   public static Tick f;
   public static Tick h;
   public static Tick t1;
   public static Tick t2;
   public static Tick t3;
   public static Tick t4;
   public static Tick t5;
   public static Tick t6;
   private int rgb_c = 0;

   public PlayerESP() {
      super("PlayerESP", Module.category.render, 0);
      this.registerSetting(a = new Slider("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(b = new Slider("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new Slider("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new Tick("Rainbow", false));
      this.registerSetting(g = new Description("ESP Types"));
      this.registerSetting(t3 = new Tick("2D", false));
      this.registerSetting(t5 = new Tick("Arrow", false));
      this.registerSetting(t1 = new Tick("Box", false));
      this.registerSetting(t4 = new Tick("Health", true));
      this.registerSetting(t6 = new Tick("Ring", false));
      this.registerSetting(t2 = new Tick("Shaded", false));
      this.registerSetting(i = new Slider("Expand", 0.0D, -0.3D, 2.0D, 0.1D));
      this.registerSetting(j = new Slider("X-Shift", 0.0D, -35.0D, 10.0D, 1.0D));
      this.registerSetting(f = new Tick("Show invis", true));
      this.registerSetting(h = new Tick("Red on damage", true));
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
