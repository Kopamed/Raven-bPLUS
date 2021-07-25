//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.combat;

import java.awt.AWTException;
import java.awt.Robot;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class ClickAssist extends Module {
   public static ModuleDesc a;
   public static ModuleSetting2 b;
   public static ModuleSetting L;
   public static ModuleSetting R;
   public static ModuleSetting c;
   public static ModuleSetting d;
   public static ModuleSetting e;
   public static ModuleSetting f;
   private Robot bot;
   private boolean ignNL = false;
   private boolean ignNR = false;

   public ClickAssist() {
      super("ClickAssist", Module.category.combat, 0);
      this.registerSetting(a = new ModuleDesc("Boost your CPS."));
      this.registerSetting(b = new ModuleSetting2("Chance", 80.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(L = new ModuleSetting("Left click", true));
      this.registerSetting(d = new ModuleSetting("Weapon only", true));
      this.registerSetting(e = new ModuleSetting("Only while targeting", false));
      this.registerSetting(R = new ModuleSetting("Right click", false));
      this.registerSetting(c = new ModuleSetting("Blocks only", true));
      this.registerSetting(f = new ModuleSetting("Above 5 cps", false));
   }

   public void onEnable() {
      try {
         this.bot = new Robot();
      } catch (AWTException var2) {
         this.disable();
      }

   }

   public void onDisable() {
      this.ignNL = false;
      this.ignNR = false;
      this.bot = null;
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void onMouseUpdate(MouseEvent ev) {
      if (ev.button >= 0 && ev.buttonstate && b.getInput() != 0.0D && ay.isPlayerInGame()) {
         if (mc.currentScreen == null && !mc.thePlayer.isEating()) {
            double ch;
            if (ev.button == 0 && L.isToggled()) {
               if (this.ignNL) {
                  this.ignNL = false;
               } else {
                  if (d.isToggled() && !ay.wpn()) {
                     return;
                  }

                  if (e.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                     return;
                  }

                  if (b.getInput() != 100.0D) {
                     ch = Math.random();
                     if (ch >= b.getInput() / 100.0D) {
                        this.fix(0);
                        return;
                     }
                  }

                  this.bot.mouseRelease(16);
                  this.bot.mousePress(16);
                  this.ignNL = true;
               }
            } else if (ev.button == 1 && R.isToggled()) {
               if (this.ignNR) {
                  this.ignNR = false;
               } else {
                  if (c.isToggled()) {
                     ItemStack item = mc.thePlayer.getHeldItem();
                     if (item == null || !(item.getItem() instanceof ItemBlock)) {
                        this.fix(1);
                        return;
                     }
                  }

                  if (f.isToggled() && cl.getRightClickCounter() <= 5) {
                     this.fix(1);
                     return;
                  }

                  if (b.getInput() != 100.0D) {
                     ch = Math.random();
                     if (ch >= b.getInput() / 100.0D) {
                        this.fix(1);
                        return;
                     }
                  }

                  this.bot.mouseRelease(4);
                  this.bot.mousePress(4);
                  this.ignNR = true;
               }
            }

            this.fix(0);
            this.fix(1);
         } else {
            this.fix(0);
            this.fix(1);
         }
      }
   }

   private void fix(int t) {
      if (t == 0) {
         if (this.ignNL && !Mouse.isButtonDown(0)) {
            this.bot.mouseRelease(16);
         }
      } else if (t == 1 && this.ignNR && !Mouse.isButtonDown(1)) {
         this.bot.mouseRelease(4);
      }

   }
}
