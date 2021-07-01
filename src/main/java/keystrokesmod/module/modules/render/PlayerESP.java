//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import keystrokesmod.*;
import keystrokesmod.main.Ravenb3;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.module.modules.world.AntiBot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerESP extends Module {
   public static ModuleDesc g;
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   public static ModuleSetting2 c;
   public static ModuleSetting2 i;
   public static ModuleSetting2 j;
   public static ModuleSetting d;
   public static ModuleSetting f;
   public static ModuleSetting h;
   public static ModuleSetting t1;
   public static ModuleSetting t2;
   public static ModuleSetting t3;
   public static ModuleSetting t4;
   public static ModuleSetting t5;
   public static ModuleSetting t6;
   private int rgb_c = 0;

   public PlayerESP() {
      super(new char[]{'P', 'l', 'a', 'y', 'e', 'r', 'E', 'S', 'P'}, Module.category.render, 0);
      this.registerSetting(a = new ModuleSetting2(new char[]{'R', 'e', 'd'}, 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(b = new ModuleSetting2(new char[]{'G', 'r', 'e', 'e', 'n'}, 255.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(c = new ModuleSetting2(new char[]{'B', 'l', 'u', 'e'}, 0.0D, 0.0D, 255.0D, 1.0D));
      this.registerSetting(d = new ModuleSetting(new char[]{'R', 'a', 'i', 'n', 'b', 'o', 'w'}, false));
      this.registerSetting(g = new ModuleDesc(new String(new char[]{'E', 'S', 'P', ' ', 'T', 'y', 'p', 'e', 's'})));
      this.registerSetting(t3 = new ModuleSetting(new char[]{'2', 'D'}, false));
      this.registerSetting(t5 = new ModuleSetting(new char[]{'A', 'r', 'r', 'o', 'w'}, false));
      this.registerSetting(t1 = new ModuleSetting(new char[]{'B', 'o', 'x'}, false));
      this.registerSetting(t4 = new ModuleSetting(new char[]{'H', 'e', 'a', 'l', 't', 'h'}, true));
      this.registerSetting(t6 = new ModuleSetting(new char[]{'R', 'i', 'n', 'g'}, false));
      this.registerSetting(t2 = new ModuleSetting(new char[]{'S', 'h', 'a', 'd', 'e', 'd'}, false));
      this.registerSetting(i = new ModuleSetting2(new char[]{'E', 'x', 'p', 'a', 'n', 'd'}, 0.0D, -0.3D, 2.0D, 0.1D));
      this.registerSetting(j = new ModuleSetting2(new char[]{'X', '-', 'S', 'h', 'i', 'f', 't'}, 0.0D, -35.0D, 10.0D, 1.0D));
      this.registerSetting(f = new ModuleSetting(new char[]{'S', 'h', 'o', 'w', ' ', 'i', 'n', 'v', 'i', 's'}, true));
      this.registerSetting(h = new ModuleSetting(new char[]{'R', 'e', 'd', ' ', 'o', 'n', ' ', 'd', 'a', 'm', 'a', 'g', 'e'}, true));
   }

   public void onDisable() {
      ru.ring_c = false;
   }

   public void guiUpdate() {
      this.rgb_c = (new Color((int)a.getInput(), (int)b.getInput(), (int)c.getInput())).getRGB();
   }

   @SubscribeEvent
   public void r1(RenderWorldLastEvent e) {
      if (ay.isPlayerInGame()) {
         int rgb = d.isToggled() ? 0 : this.rgb_c;
         Iterator var3;
         if (Ravenb3.debugger) {
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
         ru.ee(en, 1, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t2.isToggled()) {
         ru.ee(en, 2, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t3.isToggled()) {
         ru.ee(en, 3, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t4.isToggled()) {
         ru.ee(en, 4, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t5.isToggled()) {
         ru.ee(en, 5, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

      if (t6.isToggled()) {
         ru.ee(en, 6, i.getInput(), j.getInput(), rgb, h.isToggled());
      }

   }
}
