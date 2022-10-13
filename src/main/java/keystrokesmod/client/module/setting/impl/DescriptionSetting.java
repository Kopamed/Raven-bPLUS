package keystrokesmod.client.module.setting.impl;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.components.KvDescriptionComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.DescriptionComponent;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;
import keystrokesmod.client.module.setting.Setting;

public class DescriptionSetting extends Setting {
    private String desc;
    private final String defaultDesc;

    public DescriptionSetting(String t) {
        super(t);
        this.desc = t;
        this.defaultDesc = t;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String t) {
        this.desc = t;
    }

    @Override
    public void resetToDefaults() {
        this.desc = defaultDesc;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", getDesc());
        return data;
    }

    @Override
    public String getSettingType() {
        return "desc";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setDesc(data.get("value").getAsString());
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
    }

    @Override
    public Class<? extends SettingComponent> getRavenComponentType() {
        return DescriptionComponent.class;
    }

	@Override
	public Class<? extends KvComponent> getComponentType() {
		return KvDescriptionComponent.class;
	}
}
