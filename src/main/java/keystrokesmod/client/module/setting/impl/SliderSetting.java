package keystrokesmod.client.module.setting.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.components.KvSliderComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;
import keystrokesmod.client.clickgui.raven.components.SliderComponent;
import keystrokesmod.client.module.setting.Setting;

public class SliderSetting extends Setting {
    private final String name;
    private double value;
    private final double max;
    private final double min;
    private final double interval;
    private final double defaultVal;

    public SliderSetting(String settingName, double defaultValue, double min, double max, double intervals) {
        super(settingName);
        this.name = settingName;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.interval = intervals;
        this.defaultVal = defaultValue;
    }

    @Override
	public String getName() {
        return this.name;
    }

    @Override
    public void resetToDefaults() {
        this.value = defaultVal;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", getInput());
        return data;
    }

    @Override
    public String getSettingType() {
        return "slider";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setValue(data.get("value").getAsDouble());
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
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
        n = (double) Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.value = n;
    }

    public static double check(double v, double i, double a) {
        v = Math.max(i, v);
        v = Math.min(a, v);
        return v;
    }

    public static double r(double v, int p) {
        if (p < 0)
			return 0.0D;
		BigDecimal bd = new BigDecimal(v);
		bd = bd.setScale(p, RoundingMode.HALF_UP);
		return bd.doubleValue();
    }

	@Override
	public Class<? extends KvComponent> getComponentType() {
		return KvSliderComponent.class;
	}

    @Override
    public Class<? extends SettingComponent> getRavenComponentType() {
        return SliderComponent.class;
    }
}
