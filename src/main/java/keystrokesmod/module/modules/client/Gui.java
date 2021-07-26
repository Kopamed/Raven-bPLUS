//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.client;

import keystrokesmod.*;
import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;

public class Gui extends Module {
   public static final int bind = 54;
   public static ModuleSettingSlider a;
   public static ModuleDesc b;

   public Gui() {
      super("Gui", Module.category.client, 54);
      this.registerSetting(a = new ModuleSettingSlider("Theme", 3.0D, 1.0D, 3.0D, 1.0D));
      this.registerSetting(b = new ModuleDesc(ay.md + "b" + 3));
   }

   public void onEnable() {
      if (ay.isPlayerInGame() && mc.currentScreen != NotAName.clickGui) {
         mc.displayGuiScreen(NotAName.clickGui);
         NotAName.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int)a.getInput()) {
      case 1:
         b.setDesc(ay.md + "b" + 1);
         break;
      case 2:
         b.setDesc(ay.md + "b" + 2);
         break;
      case 3:
         b.setDesc(ay.md + "b" + 3);
      }

   }
}
