package me.kopamed.raven.bplus.client.feature.setting.settings;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.feature.setting.SettingType;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.RangeSliderComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RangeSetting extends Setting {
    private final String name;
    private double valMax, valMin;
    private double max;
    private double min;
    private double interval;

    public RangeSetting(String settingName, double defaultValueMin, double defaultValueMax, double min, double max, double intervals) {
        super(settingName, SettingType.RANGE);
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
    }

    public void setValueMax(double n) {
        n = correct(n, this.valMin, this.max);
        n = (double)Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMax = n;
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

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return new RangeSliderComponent(this, moduleComponent);
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", this.getSettingType().toString());
        jsonObject.addProperty("valueMin", valMin);
        jsonObject.addProperty("valueMax", valMax);
        return jsonObject;
    }

    public void setInterval(double interval){
        this.interval = interval;
    }

    public void setMax(double max){
        this.max = max;
    }

    public void setMin(double min){
        this.min = min;
    }
}
