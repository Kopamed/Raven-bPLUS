//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.client;

import me.kopamed.lunarkeystrokes.main.NotAName;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class Gui extends Module {
   public static final int bind = 54;
   public static Slider guiTheme, backgroundOpacity;
   public static Description guiThemeDesc;
   public static Tick categoryBackground;
   public static Tick toggleNotification;
   public static Tick rainbowNotification;

   public Gui() {
      super("Gui", Module.category.client, 54);
      this.registerSetting(guiTheme = new Slider("Theme", 3.0D, 1.0D, 4.0D, 1.0D));
      this.registerSetting(guiThemeDesc = new Description(Utils.md + "b+"));
      this.registerSetting(backgroundOpacity = new Slider("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(categoryBackground = new Tick("Category Background", true));
      this.registerSetting(toggleNotification = new Tick("Toggle Notifications", true));
      this.registerSetting(rainbowNotification = new Tick("Rainbow Notifications", true));
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
