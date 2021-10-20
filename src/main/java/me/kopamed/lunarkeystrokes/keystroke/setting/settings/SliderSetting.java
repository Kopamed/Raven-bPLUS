package me.kopamed.lunarkeystrokes.keystroke.setting.settings;

import me.kopamed.lunarkeystrokes.keystroke.setting.Setting;

public class SliderSetting extends Setting {
    private double value;
    private double min;
    private double max;

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public SliderSetting(String name, double defaultValue, double min, double max){
        super(name, Type.SLIDER);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
    }

    public void setValue(double value){
        this.value = value;
    }

    public static double check(double v, double i, double a) {
        v = Math.max(i, v);
        v = Math.min(a, v);
        return v;
    }

    public double getValue(){
        return this.value;
    }
}
