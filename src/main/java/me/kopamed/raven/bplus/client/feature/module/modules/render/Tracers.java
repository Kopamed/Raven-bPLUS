//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Tracers extends Module {
   public static BooleanSetting a;
   public static NumberSetting b;
   public static NumberSetting c;
   public static NumberSetting d;
   public static BooleanSetting e;
   public static NumberSetting f;
   private boolean g;
   private int rgb_c = 0;

   public Tracers() {
      super("Tracers", ModuleCategory.Render, 0);
      this.registerSetting(a = new BooleanSetting("Show invis", true));
      this.registerSetting(f = new NumberSetting("Line Width", 1.0D, 1.0D, 5.0D, 1.0D));
      this.registerSetting(b = new NumberSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new NumberSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new NumberSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(e = new BooleanSetting("Rainbow", false));
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
         if (Raven.client.getDebugManager().isDebugging()) {
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
