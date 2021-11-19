//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import org.lwjgl.input.Keyboard;

public class SelfDestruct extends Module {
   public static boolean destructed = false;

   public SelfDestruct() {
      super("Self Destruct", ModuleCategory.Client, Keyboard.KEY_BACK);
   }

   public void onEnable() {
      this.disable();
      Raven.client.destruct();
   }
}
