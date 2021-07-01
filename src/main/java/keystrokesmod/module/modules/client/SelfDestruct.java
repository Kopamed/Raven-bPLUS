//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.client;

import keystrokesmod.module.Module;
import keystrokesmod.main.NotAName;
import net.minecraft.client.gui.GuiScreen;

public class SelfDestruct extends Module {
   public static boolean destructed = false;

   public SelfDestruct() {
      super("Self Destruct", Module.category.client, 0);
   }

   public void onEnable() {
      mc.displayGuiScreen((GuiScreen)null);
      destructed = true;

      for (Module module : NotAName.moduleManager.listofmods()) {
         if (module != this) {
            module.disable();
         }
      }

      this.disable();
   }
}
