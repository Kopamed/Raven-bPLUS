package me.kopamed.lunarkeystrokes.module.setting.settings;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.setting.Setting;

public class Tick extends Setting {
   private final String name;
   static String settingType = "tick";
   private boolean isEnabled;

   public Tick(String name, boolean isEnabled) {
      super(name, settingType);
      this.name = name;
      this.isEnabled = isEnabled;
   }

   public String getName() {
      return this.name;
   }

   public boolean isToggled() {
      return this.isEnabled;
   }

   public void toggle() {
      this.isEnabled = !this.isEnabled;
       if(Ravenbplus.configManager != null){
           Ravenbplus.configManager.save();
       }
   }

   public void enable() {
      this.isEnabled = true;
       if(Ravenbplus.configManager != null){
           Ravenbplus.configManager.save();
       }
   }

   public void disable() {
      this.isEnabled = false;
       if(Ravenbplus.configManager != null){
           Ravenbplus.configManager.save();
       }
   }

   public void setEnabled(boolean b) {
      this.isEnabled = b;
       if(Ravenbplus.configManager != null){
           Ravenbplus.configManager.save();
       }
   }
}
