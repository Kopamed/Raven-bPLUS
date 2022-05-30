package keystrokesmod.client.module;

import keystrokesmod.client.main.Raven;

public class TickSetting extends Setting {
   private final String name;
   public static final String settingType = "tick";
   private boolean isEnabled;
   private final boolean defaultValue;

   public TickSetting(String name, boolean isEnabled) {
      super(name, settingType);
      this.name = name;
      this.isEnabled = isEnabled;
      this.defaultValue = isEnabled;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public void resetToDefaults() {
      this.isEnabled = defaultValue;
   }

   public boolean isToggled() {
      return this.isEnabled;
   }

   public void toggle() {
      this.isEnabled = !this.isEnabled;
   }

   public void enable() {
      this.isEnabled = true;
   }

   public void disable() {
      this.isEnabled = false;
   }

   public void setEnabled(boolean b) {
      this.isEnabled = b;
   }
}
