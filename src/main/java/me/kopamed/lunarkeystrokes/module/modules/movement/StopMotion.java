//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.movement;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class StopMotion extends Module {
   public static Tick a;
   public static Tick b;
   public static Tick c;

   public StopMotion() {
      super("Stop Motion", Module.category.movement, 0);
      this.registerSetting(a = new Tick("Stop X", true));
      this.registerSetting(b = new Tick("Stop Y", true));
      this.registerSetting(c = new Tick("Stop Z", true));
   }

   public void onEnable() {
      if(!Utils.Player.isPlayerInGame()){
         this.disable();
         return;
      }

      if(a.isToggled())
         mc.thePlayer.motionX = 0;

      if(b.isToggled())
         mc.thePlayer.motionY = 0;

      if(c.isToggled())
         mc.thePlayer.motionZ = 0;

      this.disable();
   }

}
