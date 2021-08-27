//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.client;

import keystrokesmod.*;
import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;

public class Gui extends Module {
   public static final int bind = 54;
   public static ModuleSettingSlider guiTheme;
   public static ModuleDesc guiThemeDesc;

   public Gui() {
      super("Gui", Module.category.client, 54);
      this.registerSetting(guiTheme = new ModuleSettingSlider("Theme", 4.0D, 1.0D, 3.0D, 1.0D));
      this.registerSetting(guiThemeDesc = new ModuleDesc(ay.md + "b" + 3));
   }

   public void onEnable() {
      if (ay.isPlayerInGame() && mc.currentScreen != NotAName.clickGui) {
         mc.displayGuiScreen(NotAName.clickGui);
         NotAName.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int) guiTheme.getInput()) {
      case 1:
         guiThemeDesc.setDesc(ay.md + "b" + 1);
         break;
      case 2:
         guiThemeDesc.setDesc(ay.md + "b" + 2);
         break;
      case 3:
         guiThemeDesc.setDesc(ay.md + "b" + 3);
         break;

      }
   }
}
