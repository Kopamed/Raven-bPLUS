package keystrokesmod.module;

import keystrokesmod.main.Ravenbplus;

public class ModuleSettingTick extends ModuleSettingsList {
   private final String name;
   static String settingType = "tick";
   private boolean isEnabled;

   public ModuleSettingTick(String name, boolean isEnabled) {
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
      if(Ravenbplus.config != null)
         Ravenbplus.config.updateConfigFile();
   }

   public void enable() {
      this.isEnabled = true;
      if(Ravenbplus.config != null)
         Ravenbplus.config.updateConfigFile();
   }

   public void disable() {
      this.isEnabled = false;
      if(Ravenbplus.config != null)
         Ravenbplus.config.updateConfigFile();
   }

   public void setEnabled(boolean b) {
      this.isEnabled = b;
      if(Ravenbplus.config != null)
         Ravenbplus.config.updateConfigFile();
   }
}
