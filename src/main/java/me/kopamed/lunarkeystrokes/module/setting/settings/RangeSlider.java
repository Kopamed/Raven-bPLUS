package me.kopamed.lunarkeystrokes.module.setting.settings;

import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.setting.Setting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RangeSlider extends Setting {
    private final String name;
    static String settingType = "doubleslider";
    private double valMax, valMin;
    private final double max;
    private final double min;
    private final double interval;

    public RangeSlider(String settingName, double defaultValueMin, double defaultValueMax, double min, double max, double intervals) {
        super(settingName, settingType);
        this.name = settingName;
        this.valMin = defaultValueMin;
        this.valMax = defaultValueMax;
        this.min = min;
        this.max = max;
        this.interval = intervals;
    }

    public String getName() {
        return this.name;
    }

    public double getInputMin() {
        return round(this.valMin, 2);
    }
    public double getInputMax() {
        return round(this.valMax, 2);
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public void setValueMin(double n) {
        n = correct(n, this.min, this.valMax);
        n = (double)Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMin = n;
        if(Ravenbplus.configManager != null){
            Ravenbplus.configManager.save();
        }
    }

    public void setValueMax(double n) {
        n = correct(n, this.valMin, this.max);
        n = (double)Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMax = n;
        if(Ravenbplus.configManager != null){
            Ravenbplus.configManager.save();
        }
    }

    public static double correct(double val, double min, double max) {
        val = Math.max(min, val);
        val = Math.min(max, val);
        return val;
    }

    public static double round(double val, int p) {
        if (p < 0) {
            return 0.0D;
        } else {
            BigDecimal bd = new BigDecimal(val);
            bd = bd.setScale(p, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }
}
