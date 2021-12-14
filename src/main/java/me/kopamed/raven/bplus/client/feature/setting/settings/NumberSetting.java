package me.kopamed.raven.bplus.client.feature.setting.settings;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.feature.setting.SettingType;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.RangeSliderComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.SliderComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberSetting extends Setting {
   private final String name;
   private double defaultVal;
   private final double max;
   private final double min;
   private final double interval;

   public NumberSetting(String settingName, double defaultValue, double min, double max, double intervals) {
      super(settingName, SettingType.NUMBER);
      this.name = settingName;
      this.defaultVal = defaultValue;
      this.min = min;
      this.max = max;
      this.interval = intervals;
   }

   public String getName() {
      return this.name;
   }

   public double getInput() {
      return r(this.defaultVal, 2);
   }

   public double getMin() {
      return this.min;
   }

   public double getMax() {
      return this.max;
   }

   public void setValue(double n) {
      n = check(n, this.min, this.max);
      n = (double)Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
      this.defaultVal = n;
   }

   public static double check(double v, double i, double a) {
      v = Math.max(i, v);
      v = Math.min(a, v);
      return v;
   }

   public static double r(double v, int p) {
      if (p < 0) {
         return 0.0D;
      } else {
         BigDecimal bd = new BigDecimal(v);
         bd = bd.setScale(p, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   @Override
   public Component createComponent(ModuleComponent moduleComponent) {
      return new SliderComponent(this, moduleComponent);
   }

   @Override
   public JsonObject getConfigAsJson() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("type", this.getSettingType().toString());
      jsonObject.addProperty("value", defaultVal);
      return jsonObject;
   }
}
