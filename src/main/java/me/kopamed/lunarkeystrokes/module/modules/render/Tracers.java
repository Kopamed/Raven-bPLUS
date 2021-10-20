//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Tracers extends Module {
   public static Tick a;
   public static Slider b;
   public static Slider c;
   public static Slider d;
   public static Tick e;
   public static Slider f;
   private boolean g;
   private int rgb_c = 0;

   public Tracers() {
      super("Tracers", Module.category.render, 0);
      this.registerSetting(a = new Tick("Show invis", true));
      this.registerSetting(f = new Slider("Line Width", 1.0D, 1.0D, 5.0D, 1.0D));
      this.registerSetting(b = new Slider("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new Slider("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new Slider("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(e = new Tick("Rainbow", false));
   }

   public void onEnable() {
      this.g = mc.gameSettings.viewBobbing;
      if (this.g) {
         mc.gameSettings.viewBobbing = false;
      }

   }

   public void onDisable() {
      mc.gameSettings.viewBobbing = this.g;
   }

   public void update() {
      if (mc.gameSettings.viewBobbing) {
         mc.gameSettings.viewBobbing = false;
      }

   }

   public void guiUpdate() {
      this.rgb_c = (new Color((int)b.getInput(), (int)c.getInput(), (int)d.getInput())).getRGB();
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent ev) {
      if (Utils.Player.isPlayerInGame()) {
         int rgb = e.isToggled() ? Utils.Client.rainbowDraw(2L, 0L) : this.rgb_c;
         Iterator var3;
         if (Ravenbplus.debugger) {
            var3 = mc.theWorld.loadedEntityList.iterator();

            while(var3.hasNext()) {
               Entity en = (Entity)var3.next();
               if (en instanceof EntityLivingBase && en != mc.thePlayer) {
                  Utils.HUD.dtl(en, rgb, (float)f.getInput());
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
               } while(!a.isToggled() && en.isInvisible());

               if (!AntiBot.bot(en)) {
                  Utils.HUD.dtl(en, rgb, (float)f.getInput());
               }
            }
         }
      }
   }
}
