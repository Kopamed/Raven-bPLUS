package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

public class SliderComponent extends Component {
    private final NumberSetting numberSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;

    public SliderComponent(NumberSetting numberSetting, ModuleComponent moduleComponent){
        this.numberSetting = numberSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }
}
