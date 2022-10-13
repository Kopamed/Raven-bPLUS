package keystrokesmod.client.module.setting.impl;

import com.google.gson.JsonObject;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.clickgui.kv.components.KvComboComponent;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.components.ComboComponent;
import keystrokesmod.client.clickgui.raven.components.ModuleComponent;
import keystrokesmod.client.clickgui.raven.components.SettingComponent;
import keystrokesmod.client.module.setting.Setting;

public class ComboSetting<T extends Enum<?>> extends Setting {
    private T[] options;
    private T currentOption;
    private final T defaultOption;

    public ComboSetting(String settingName, T defaultOption) {
        super(settingName);

        this.currentOption = defaultOption;
        this.defaultOption = defaultOption;

        try {
            this.options = (T[]) defaultOption.getClass().getMethod("values").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetToDefaults() {
        this.currentOption = defaultOption;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", getMode().toString());
        return data;
    }

    @Override
    public String getSettingType() {
        return "mode";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        String bruh = data.get("value").getAsString();
        for (T opt : options)
            if (opt.toString().equals(bruh))
                setMode(opt);
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
    }

    public T getMode() {
        return this.currentOption;
    }

    public void setMode(T value) {
        this.currentOption = value;
    }

    public void nextMode() {
        currentOption = options[(currentOption.ordinal() + 1) % (options.length)];
    }

    public void prevMode() {
        currentOption = options[currentOption.ordinal() == 0 ? options.length - 1 : currentOption.ordinal() - 1];
    }

    public T getPrevMode() {
        return options[currentOption.ordinal() == 0 ? options.length - 1 : currentOption.ordinal() - 1];
    }

	@Override
	public Class<? extends KvComponent> getComponentType() {
		return KvComboComponent.class;
	}

    @Override
    public Class<? extends SettingComponent> getRavenComponentType() {
        return ComboComponent.class;
    }
}
