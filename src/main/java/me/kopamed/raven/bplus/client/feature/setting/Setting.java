package me.kopamed.raven.bplus.client.feature.setting;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Setting {
   private final SettingType settingType;
   private final String settingName;
   private final ArrayList<SelectorRunnable> selectors = new ArrayList<>();

   public Setting(String name, SettingType settingType) {
      this.settingName = name;
      this.settingType = settingType;
   }

   public String getName() {
      return settingName;
   }

   public boolean canShow(){
      if(selectors.isEmpty())
         return true;

      for(SelectorRunnable selectorRunnable : selectors){
         if(selectorRunnable.showOnlyIf())
            return false;
      }
      return true;
   }

   public void addSelector(SelectorRunnable selectorRunnable){
      this.selectors.add(selectorRunnable);
   }

   public JsonObject getSettingsAsJson(){
      //todo
      return new JsonObject();
   }

    public Component createComponent(ModuleComponent moduleComponent) {
      return new Component() {
      };
    }
}
