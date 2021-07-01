//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.movement;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSetting2;

public class Boost extends Module {
   public static ModuleDesc c;
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   private int i = 0;
   private boolean t = false;

   public Boost() {
      super("Boost", Module.category.movement, 0);
      this.registerSetting(c = new ModuleDesc("20 ticks are in 1 second"));
      this.registerSetting(a = new ModuleSetting2("Multiplier", 2.0D, 1.0D, 3.0D, 0.05D));
      this.registerSetting(b = new ModuleSetting2("Time (ticks)", 15.0D, 1.0D, 80.0D, 1.0D));
   }

   public void onEnable() {
      if (ModuleManager.timer.isEnabled()) {
         this.t = true;
         ModuleManager.timer.disable();
      }

   }

   public void onDisable() {
      this.i = 0;
      if (ay.gt().timerSpeed != 1.0F) {
         ay.rt();
      }

      if (this.t) {
         ModuleManager.timer.enable();
      }

      this.t = false;
   }

   public void update() {
      if (this.i == 0) {
         this.i = mc.thePlayer.ticksExisted;
      }

      ay.gt().timerSpeed = (float)a.getInput();
      if ((double)this.i == (double)mc.thePlayer.ticksExisted - b.getInput()) {
         ay.rt();
         this.disable();
      }

   }
}
