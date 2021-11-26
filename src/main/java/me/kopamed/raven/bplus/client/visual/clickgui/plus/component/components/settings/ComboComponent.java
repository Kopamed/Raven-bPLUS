package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

public class ComboComponent extends Component {
    private final ModuleComponent moduleComponent;
    private final Module module;
    private final ComboSetting comboSetting;

    public ComboComponent(ComboSetting comboSetting, ModuleComponent moduleComponent){
        this.comboSetting = comboSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }
}