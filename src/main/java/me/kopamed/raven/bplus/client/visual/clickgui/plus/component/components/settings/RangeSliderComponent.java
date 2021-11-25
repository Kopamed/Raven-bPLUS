package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;

public class RangeSliderComponent extends Component {
    private final RangeSetting rangeSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;

    public RangeSliderComponent(RangeSetting rangeSetting, ModuleComponent moduleComponent){
        this.rangeSetting = rangeSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }
}
