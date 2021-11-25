package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

public class TickComponent extends Component {
    private final BooleanSetting booleanSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;

    public TickComponent(BooleanSetting booleanSetting, ModuleComponent moduleComponent){
        this.booleanSetting = booleanSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }
}
