package me.kopamed.raven.bplus.client.feature.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerESP extends Module {
   public static DescriptionSetting g;
   public static NumberSetting a;
   public static NumberSetting b;
   public static NumberSetting c;
   public static NumberSetting i;
   public static NumberSetting j;
   public static BooleanSetting d;
   public static BooleanSetting f;
   public static BooleanSetting h;
   public static BooleanSetting t1;
   public static BooleanSetting t2;
   public static BooleanSetting t3;
   public static BooleanSetting t4;
   public static BooleanSetting t5;
   public static BooleanSetting t6;
   private int rgb_c = 0;

   public PlayerESP() {
      super("PlayerESP", ModuleCategory.Render, 0);
      this.registerSetting(a = new NumberSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(b = new NumberSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new NumberSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new BooleanSetting("Rainbow", false));
      this.registerSetting(g = new DescriptionSetting("ESP Types"));
      this.registerSetting(t3 = new BooleanSetting("2D", false));
      this.registerSetting(t5 = new BooleanSetting("Arrow", false));
      this.registerSetting(t1 = new BooleanSetting("Box", false));
      this.registerSetting(t4 = new BooleanSetting("Health", true));
      this.registerSetting(t6 = new BooleanSetting("Ring", false));
      this.registerSetting(t2 = new BooleanSetting("Shaded", false));
      this.registerSetting(i = new NumberSetting("Expand", 0.0D, -0.3D, 2.0D, 0.1D));
      this.registerSetting(j = new NumberSetting("X-Shift", 0.0D, -35.0D, 10.0D, 1.0D));
      this.registerSetting(f = new BooleanSetting("Show invis", true));
      this.registerSetting(h = new BooleanSetting("Red on damage", true));
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
         if (Raven.client.getDebugManager().isDebugging()) {
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
         Utils.HUD.drawBoxAroundEntity(en, 1, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t2.isToggled()) {
         Utils.HUD.drawBoxAroundEntity(en, 2, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t3.isToggled()) {
         Utils.HUD.drawBoxAroundEntity(en, 3, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t4.isToggled()) {
         Utils.HUD.drawBoxAroundEntity(en, 4, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t5.isToggled()) {
         Utils.HUD.drawBoxAroundEntity(en, 5, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t6.isToggled()) {
         Utils.HUD.drawBoxAroundEntity(en, 6, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

   }
}
