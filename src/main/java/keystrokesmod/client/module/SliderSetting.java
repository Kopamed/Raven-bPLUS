package keystrokesmod.client.module;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderSetting extends Setting {
   private final String name;
   public static final String settingType = "slider";
   private double value;
   private final double max;
   private final double min;
   private final double interval;
   private final double defaultVal;

   public SliderSetting(String settingName, double defaultValue, double min, double max, double intervals) {
      super(settingName, settingType);
      this.name = settingName;
      this.value = defaultValue;
      this.min = min;
      this.max = max;
      this.interval = intervals;
      this.defaultVal = defaultValue;
   }

   public String getName() {
      return this.name;
   }

   @Override
   public void resetToDefaults() {
      this.value = defaultVal;
   }

   public double getInput() {
      return r(this.value, 2);
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
      this.value = n;
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
}
