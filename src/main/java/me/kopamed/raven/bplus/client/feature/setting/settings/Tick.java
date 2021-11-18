package me.kopamed.raven.bplus.client.feature.setting.settings;

import me.kopamed.raven.bplus.client.feature.setting.Setting;

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
