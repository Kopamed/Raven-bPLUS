package me.kopamed.raven.bplus.client.feature.setting.settings;

import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.feature.setting.SettingType;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.TickComponent;

public class BooleanSetting extends Setting {
   private final String name;
   private boolean isEnabled;

   public BooleanSetting(String name, boolean isEnabled) {
      super(name, SettingType.BOOLEAN);
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

   @Override
   public Component createComponent(ModuleComponent moduleComponent) {
      return new TickComponent(this, moduleComponent);
   }
}
