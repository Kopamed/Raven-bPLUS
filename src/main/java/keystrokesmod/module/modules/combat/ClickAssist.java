package keystrokesmod.module.modules.combat;

import java.awt.AWTException;
import java.awt.Robot;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.utils.Utils;
import keystrokesmod.utils.mouseManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class ClickAssist extends Module {
   public static ModuleDesc desc;
   public static ModuleSettingSlider chance;
   public static ModuleSettingTick L;
   public static ModuleSettingTick R;
   public static ModuleSettingTick blocksOnly;
   public static ModuleSettingTick weaponOnly;
   public static ModuleSettingTick onlyWhileTargeting;
   public static ModuleSettingTick above5;
   private Robot bot;
   private boolean engagedLeft = false;
   private boolean engagedRight = false;

   public ClickAssist() {
      super("ClickAssist", Module.category.combat, 0);
      this.registerSetting(desc = new ModuleDesc("Boost your CPS."));
      this.registerSetting(chance = new ModuleSettingSlider("Chance", 80.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(L = new ModuleSettingTick("Left click", true));
      this.registerSetting(weaponOnly = new ModuleSettingTick("Weapon only", true));
      this.registerSetting(onlyWhileTargeting = new ModuleSettingTick("Only while targeting", false));
      this.registerSetting(R = new ModuleSettingTick("Right click", false));
      this.registerSetting(blocksOnly = new ModuleSettingTick("Blocks only", true));
      this.registerSetting(above5 = new ModuleSettingTick("Above 5 cps", false));
   }

   public void onEnable() {
      try {
         this.bot = new Robot();
      } catch (AWTException var2) {
         this.disable();
      }

   }

   public void onDisable() {
      this.engagedLeft = false;
      this.engagedRight = false;
      this.bot = null;
   }

   @SubscribeEvent(
           priority = EventPriority.HIGH
   )
   public void onMouseUpdate(MouseEvent ev) {
      if (ev.button >= 0 && ev.buttonstate && chance.getInput() != 0.0D && Utils.Player.isPlayerInGame()) {
         if (mc.currentScreen == null && !mc.thePlayer.isEating() && !mc.thePlayer.isBlocking()) {
            double ch;
            if (ev.button == 0 && L.isToggled()) {
               if (this.engagedLeft) {
                  this.engagedLeft = false;
               } else {
                  if (weaponOnly.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) {
                     return;
                  }

                  if (onlyWhileTargeting.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                     return;
                  }

                  if (chance.getInput() != 100.0D) {
                     ch = Math.random();
                     if (ch >= chance.getInput() / 100.0D) {
                        this.fix(0);
                        return;
                     }
                  }

                  this.bot.mouseRelease(16);
                  this.bot.mousePress(16);
                  this.engagedLeft = true;
               }
            } else if (ev.button == 1 && R.isToggled()) {
               if (this.engagedRight) {
                  this.engagedRight = false;
               } else {
                  if (blocksOnly.isToggled()) {
                     ItemStack item = mc.thePlayer.getHeldItem();
                     if (item == null || !(item.getItem() instanceof ItemBlock)) {
                        this.fix(1);
                        return;
                     }
                  }

                  if (above5.isToggled() && mouseManager.getRightClickCounter() <= 5) {
                     this.fix(1);
                     return;
                  }

                  if (chance.getInput() != 100.0D) {
                     ch = Math.random();
                     if (ch >= chance.getInput() / 100.0D) {
                        this.fix(1);
                        return;
                     }
                  }

                  this.bot.mouseRelease(4);
                  this.bot.mousePress(4);
                  this.engagedRight = true;
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
         if (this.engagedLeft && !Mouse.isButtonDown(0)) {
            this.bot.mouseRelease(16);
         }
      } else if (t == 1 && this.engagedRight && !Mouse.isButtonDown(1)) {
         this.bot.mouseRelease(4);
      }

   }
}