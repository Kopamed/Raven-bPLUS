package me.kopamed.raven.bplus.client.feature.setting.settings;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.feature.setting.SettingType;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.TickComponent;

import java.util.Map;

public class BooleanSetting extends Setting {
   private final String name;
   private boolean isEnabled;

   public BooleanSetting(String name, boolean isEnabled) {
      this(name, "", isEnabled);
   }

   public BooleanSetting(String name, String description, boolean isEnabled) {
      super(name, description, SettingType.BOOLEAN);
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

   @Override
   public JsonObject getConfigAsJson() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", this.getSettingType().toString());
      jsonObject.addProperty("value", isEnabled);
      return jsonObject;
   }

   @Override
   public void setConfigFromJson(Map<String, Object> settings) {
      // NOTE : a boolean setting is a boolean.
      //this.setSettingType(SettingType.getOrDefault(jsonObject.get("type").getAsString(), SettingType.BOOLEAN));
      this.isEnabled = (boolean) settings.get("value");
   }
}
