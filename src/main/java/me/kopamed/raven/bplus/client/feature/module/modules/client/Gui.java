//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.Description;
import me.kopamed.raven.bplus.client.feature.setting.settings.Slider;
import me.kopamed.raven.bplus.client.feature.setting.settings.Tick;
import me.kopamed.raven.bplus.helper.utils.Utils;

public class Gui extends Module {
   public static final int bind = 54;
   public static Slider guiTheme, backgroundOpacity;
   public static Description guiThemeDesc;
   public static Tick categoryBackground;
   public static Tick toggleNotification;
   public static Tick rainbowNotification;

   public Gui() {
      super("Gui", ModuleCategory.Misc, 54);
      this.registerSetting(guiTheme = new Slider("Theme", 3.0D, 1.0D, 4.0D, 1.0D));
      this.registerSetting(guiThemeDesc = new Description(Utils.md + "b+"));
      this.registerSetting(backgroundOpacity = new Slider("Background Opacity %", 43.0D, 0.0D, 100.0D, 1.0D));
      this.registerSetting(categoryBackground = new Tick("Category Background", true));
      this.registerSetting(toggleNotification = new Tick("Toggle Notifications", true));
      this.registerSetting(rainbowNotification = new Tick("Rainbow Notifications", true));
   }

   public void onEnable() {
      if (Utils.Player.isPlayerInGame() && mc.currentScreen != Raven.client.getClickGui()) {
         mc.displayGuiScreen(Raven.client.getClickGui());
            Raven.client.getClickGui().initMain();
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
