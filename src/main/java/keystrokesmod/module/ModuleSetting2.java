package keystrokesmod.module;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ModuleSetting2 extends ModuleSettingsList {
   private String n;
   private double v;
   private double a;
   private double m;
   private double i;

   public ModuleSetting2(char[] settingName, double defaultValue, double min, double max, double intervals) {
      super(new String(settingName));
      this.n = new String(settingName);
      this.v = defaultValue;
      this.m = min;
      this.a = max;
      this.i = intervals;
   }

   public String get() {
      return this.n;
   }

   public double getInput() {
      return r(this.v, 2);
   }

   public double g3ti() {
      return this.m;
   }

   public double g3ta() {
      return this.a;
   }

   public void setValue(double n) {
      n = c(n, this.m, this.a);
      n = (double)Math.round(n * (1.0D / this.i)) / (1.0D / this.i);
      this.v = n;
   }

   public static double c(double v, double i, double a) {
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
