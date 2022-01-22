package me.kopamed.raven.bplus.client.feature.setting.settings;

import com.google.gson.JsonObject;
import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.feature.setting.SettingType;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.ComboComponent;

public class ComboSetting<T extends Enum<?>> extends Setting {
    private T[] options;
    private T currentOption;

    public ComboSetting(String settingName, T defaultOption){
        super(settingName, SettingType.COMBO);

        this.currentOption = defaultOption;
        try {
            this.options = (T[]) defaultOption.getClass().getMethod("values").invoke(null);
            //System.out.println("SUCCESSFULL ACCESS OFF" + defaultOption.getClass()  + "  fdsf hdsof sdf ush fhsuidfh");
        } catch (Exception e) {
            System.out.println("FUCKED UP " + defaultOption.getClass());
        }
    }

    public T getMode(){
        return this.currentOption;
    }

    public void setMode(T value){
        this.currentOption = value;
    }

    public void nextMode(){
        for(int i = 0; i < options.length; i++){
            if(options[i] == currentOption) {
                System.out.println(i);
                System.out.println(options.length);
                System.out.println((i + 1) % (options.length));
                currentOption = options[(i + 1) % (options.length)];
                return;
            }
        }
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return new ComboComponent(this, moduleComponent);
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", this.getSettingType().toString());
        jsonObject.addProperty("value", currentOption + "");
        return jsonObject;
    }
}
