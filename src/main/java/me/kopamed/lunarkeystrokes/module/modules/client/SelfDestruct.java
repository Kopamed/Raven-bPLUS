//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.client;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.main.NotAName;
import org.lwjgl.input.Keyboard;

public class SelfDestruct extends Module {
   public static boolean destructed = false;

   public SelfDestruct() {
      super("Self Destruct", Module.category.client, Keyboard.KEY_BACK);
   }

   public void onEnable() {
      this.disable();

      mc.displayGuiScreen(null);
      destructed = true;

      for (Module module : NotAName.moduleManager.getModules()) {
         if (module != this) {
            module.disable();
         }
      }
   }
}
