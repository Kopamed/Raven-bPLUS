package keystrokesmod.client.module.setting.impl;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.components.KvTickComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;
import keystrokesmod.client.clickgui.raven.components.TickComponent;
import keystrokesmod.client.module.setting.Setting;

public class TickSetting extends Setting {
    private final String name;
    private boolean isEnabled;
    private final boolean defaultValue;

    public TickSetting(String name, boolean isEnabled) {
        super(name);
        this.name = name;
        this.isEnabled = isEnabled;
        this.defaultValue = isEnabled;
    }

    @Override
	public String getName() {
        return this.name;
    }

    @Override
    public void resetToDefaults() {
        this.isEnabled = defaultValue;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", isToggled());
        return data;
    }

    @Override
    public String getSettingType() {
        return "tick";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setEnabled(data.get("value").getAsBoolean());
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
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
    public Class<? extends SettingComponent> getRavenComponentType() {
        return TickComponent.class;
    }

	@Override
	public Class<? extends KvComponent> getComponentType() {
		return KvTickComponent.class;
	}
}
