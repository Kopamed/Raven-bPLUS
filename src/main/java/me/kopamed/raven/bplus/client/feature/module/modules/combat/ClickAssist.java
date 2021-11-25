//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.MouseManager;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class ClickAssist extends Module {
   // settings
   public static DescriptionSetting desc, linux;
   public static ComboSetting mode;
   public static NumberSetting leftChance, rightChance;
   public static NumberSetting minCPSLeft, minCPSRight;
   public static BooleanSetting left;
   public static BooleanSetting right;
   public static BooleanSetting blocksOnly;
   public static BooleanSetting weaponOnly;
   public static BooleanSetting onlyWhileTargeting;

   // misc
   private Robot bot;
   private boolean engagedLeft = false;
   private boolean engagedRight = false;
   private boolean skipLeft;
   private boolean skipRight;

   public ClickAssist() {
      super("ClickAssist", ModuleCategory.Combat, 0);

      // registering settings
      this.registerSetting(desc = new DescriptionSetting("Boost your CPS."));
      this.registerSetting(mode = new ComboSetting("Assist__ click", new String[] {"After", "Before"}, 0));
      this.registerSetting(linux = new DescriptionSetting("Use AFTER on linux"));

      this.registerSetting(left = new BooleanSetting("Left click", true));
      this.registerSetting(weaponOnly = new BooleanSetting("Weapon only", true));
      this.registerSetting(onlyWhileTargeting = new BooleanSetting("Only while targeting", false));
      this.registerSetting(leftChance = new NumberSetting("Left chance", 80.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(minCPSLeft = new NumberSetting("Above _ cps (left)", 3, 0, 20, 1)); // todo

      this.registerSetting(right = new BooleanSetting("Right click", false));
      this.registerSetting(blocksOnly = new BooleanSetting("Blocks only", true));
      this.registerSetting(rightChance = new NumberSetting("Right chance", 95.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(minCPSRight = new NumberSetting("Above _ cps (right)", 3, 0, 20, 1));
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

   private void click(MouseEvent ev){
      if(ev.button == 0 && left.isToggled() && !skipLeft){ // ad hecks for skip left
         if(leftChance.getInput()/100 < Math.random()){
            return;
         }

         //checks
         if (weaponOnly.isToggled() && !Utils.Player.isPlayerHoldingWeapon()) {
            return;
         }

         if (onlyWhileTargeting.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
            return;
         }


         if(MouseManager.getLeftClickCounter() < minCPSLeft.getInput())
            return;

         // click
         this.bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
         this.bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
         skipLeft = true;
         this.fix(0);
         // execute left
      } else if(ev.button == 1 && right.isToggled()  && !skipRight){
         if(rightChance.getInput()/100 < Math.random()){
            return;
         }

         //checks
         if (blocksOnly.isToggled()) {
            ItemStack item = mc.thePlayer.getHeldItem();
            if (item == null || !(item.getItem() instanceof ItemBlock)) {
               this.fix(1);
               return;
            }
         }

         if(MouseManager.getRightClickCounter() < minCPSRight.getInput())
            return;

         // click
         this.bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
         this.bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
         skipRight = true;
         this.fix(1);
         // execute right
      } else if(skipLeft || skipRight){
         skipLeft = false;
         skipRight = false;
      }
   }

   @SubscribeEvent(
           priority = EventPriority.HIGH
   )
   public void onMouseUpdate(MouseEvent ev) {
      if(mode.getMode().equals("After")){
         if(ev.buttonstate)  // make sure that the button is released
            return;

         click(ev);

      } else {
         if(!ev.buttonstate)  // make sure that the button is pressed
            return;

         click(ev);
      }
   }



   private void fix(int t) {
      if (t == 0) {
         if (skipLeft && !Mouse.isButtonDown(0)) {
            this.bot.mouseRelease(16);
         }
      } else if (t == 1 && skipRight && !Mouse.isButtonDown(1)) {
         this.bot.mouseRelease(4);
      }

   }
}