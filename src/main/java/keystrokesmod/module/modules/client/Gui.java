//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.client;

import keystrokesmod.NotificationRenderer;
import keystrokesmod.main.NotAName;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.utils.Utils;

public class Gui extends Module {
   public static final int bind = 54;
   public static ModuleSettingSlider guiTheme, backgroundOpacity;
   public static ModuleDesc guiThemeDesc;
   public static ModuleSettingTick categoryBackground;
   public static ModuleSettingTick toggleNotification;
   public static ModuleSettingTick rainbowNotification;

   public Gui() {
      super("Gui", Module.category.client, 54);
      this.registerSetting(guiTheme = new ModuleSettingSlider("Theme", 3.0D, 1.0D, 4.0D, 1.0D));
      this.registerSetting(guiThemeDesc = new ModuleDesc(Utils.md + "b+"));
      this.registerSetting(backgroundOpacity = new ModuleSettingSlider("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(categoryBackground = new ModuleSettingTick("Category Background", true));
      this.registerSetting(toggleNotification = new ModuleSettingTick("Toggle Notifications", true));
      this.registerSetting(rainbowNotification = new ModuleSettingTick("Rainbow Notifications", true));
   }

   public void onEnable() {
      if (Utils.Player.isPlayerInGame() && mc.currentScreen != NotAName.clickGui) {
         mc.displayGuiScreen(NotAName.clickGui);
            NotAName.clickGui.initMain();
      }

      this.disable();
   }

   public void guiUpdate() {
      switch((int) guiTheme.getInput()) {
      case 1:
         guiThemeDesc.setDesc(Utils.md + "b" + 1);
         break;
      case 2:
         guiThemeDesc.setDesc(Utils.md + "b" + 2);
         break;
      case 3:
         guiThemeDesc.setDesc(Utils.md + "b" + 3);
         break;

      case 4:
         guiThemeDesc.setDesc(Utils.md + "b+");
         break;
      }
   }
}
