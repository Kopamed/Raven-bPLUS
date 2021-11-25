package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

public class DescriptionComponent extends Component {
    private DescriptionSetting descriptionSetting;
    private ModuleComponent moduleComponent;
    private Module module;

    public DescriptionComponent(DescriptionSetting descriptionSetting, ModuleComponent moduleComponent){
        this.descriptionSetting = descriptionSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }
}
