package keystrokesmod.client.module.setting;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;

public abstract class Setting {
    public String settingName;
    private SettingComponent component;

    public Setting(String name) {
        this.settingName = name;
    }

    public String getName() {
        return this.settingName;
    }

    public abstract void resetToDefaults();

    public abstract JsonObject getConfigAsJson();

    public abstract String getSettingType();

    public abstract void applyConfigFromJson(JsonObject data);

    public abstract Component createComponent(ModuleComponent moduleComponent);

    public abstract Class<? extends KvComponent> getComponentType();

    public abstract Class<? extends SettingComponent> getRavenComponentType();

    public void setComponent(SettingComponent component) {
        this.component = component;
    }

    public void hideComponent() {
        component.hideComponent();
    }

    public void hideComponent(boolean hidden) {
        component.hideComponent(hidden);
    }
}
