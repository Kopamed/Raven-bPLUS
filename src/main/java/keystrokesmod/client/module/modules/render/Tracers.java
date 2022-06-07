package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Iterator;

public class Tracers extends Module {
   public static TickSetting a;
   public static SliderSetting b;
   public static SliderSetting c;
   public static SliderSetting d;
   public static TickSetting e;
   public static SliderSetting f;
   private boolean g;
   private int rgb_c = 0;

   public Tracers() {
      super("Tracers", ModuleCategory.render);
      this.registerSetting(a = new TickSetting("Show invis", true));
      this.registerSetting(f = new SliderSetting("Line Width", 1.0D, 1.0D, 5.0D, 1.0D));
      this.registerSetting(b = new SliderSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new SliderSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new SliderSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(e = new TickSetting("Rainbow", false));
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
         if (Raven.debugger) {
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
