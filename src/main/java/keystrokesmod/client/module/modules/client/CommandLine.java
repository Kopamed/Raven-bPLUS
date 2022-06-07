package keystrokesmod.client.module.modules.client;

import com.google.gson.JsonObject;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Timer;

public class CommandLine extends Module {
   public static boolean a = false;
   public static boolean b = false;
   public static Timer an;
   public static TickSetting animate;

   public CommandLine() {
      super("Command line", ModuleCategory.client);
      this.registerSetting(animate = new TickSetting("Animate", true));
   }

   public void onEnable() {
      keystrokesmod.client.clickgui.raven.CommandLine.setccs();
      a = true;
      b = false;
      (an = new Timer(500.0F)).start();
   }

   public void onDisable() {
      b = true;
      if (an != null) {
         an.start();
      }

      keystrokesmod.client.clickgui.raven.CommandLine.od();
   }

   @Override
   public void applyConfigFromJson(JsonObject data){
      try {
         this.keycode = data.get("keycode").getAsInt();
         // no need to set this to disabled
         JsonObject settingsData = data.get("settings").getAsJsonObject();
         for (Setting setting : getSettings()) {
            if (settingsData.has(setting.getName())) {
               setting.applyConfigFromJson(
                       settingsData.get(setting.getName()).getAsJsonObject()
               );
            }
         }
      } catch (NullPointerException ignored){

      }
   }

   @Override
   public void resetToDefaults() {
      this.keycode = defualtKeyCode;

      for(Setting setting : this.settings){
         setting.resetToDefaults();
      }
   }
}
