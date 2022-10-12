package keystrokesmod.client.module.setting.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.components.KvDoubleSliderComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.DoubleSliderComponent;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;
import keystrokesmod.client.module.setting.Setting;

public class DoubleSliderSetting extends Setting {
    private final String name;
    private double valMax, valMin;
    private final double max;
    private final double min;
    private final double interval;

    private final double defaultValMin, defaultValMax;

    public DoubleSliderSetting(String settingName, double defaultValueMin, double defaultValueMax, double min,
            double max, double intervals) {
        super(settingName);
        this.name = settingName;
        this.valMin = defaultValueMin;
        this.valMax = defaultValueMax;
        this.min = min;
        this.max = max;
        this.interval = intervals;
        this.defaultValMin = valMin;
        this.defaultValMax = valMax;
    }

    @Override
	public String getName() {
        return this.name;
    }

    @Override
    public void resetToDefaults() {
        this.setValueMin(defaultValMin);
        this.setValueMax(defaultValMax);
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("valueMin", getInputMin());
        data.addProperty("valueMax", getInputMax());
        return data;
    }

    @Override
    public String getSettingType() {
        return "doubleslider";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setValueMax(data.get("valueMax").getAsDouble());
        setValueMin(data.get("valueMin").getAsDouble());
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
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
        n = (double) Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMin = n;
    }

    public void setValueMax(double n) {
        n = correct(n, this.valMin, this.max);
        n = (double) Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMax = n;
    }

    public static double correct(double val, double min, double max) {
        val = Math.max(min, val);
        val = Math.min(max, val);
        return val;
    }

    public static double round(double val, int p) {
        if (p < 0)
			return 0.0D;
		BigDecimal bd = new BigDecimal(val);
		bd = bd.setScale(p, RoundingMode.HALF_UP);
		return bd.doubleValue();
    }

	@Override
	public Class<? extends KvComponent> getComponentType() {
		return KvDoubleSliderComponent.class;
	}

    @Override
    public Class<? extends SettingComponent> getRavenComponentType() {
        return DoubleSliderComponent.class;
    }
}
