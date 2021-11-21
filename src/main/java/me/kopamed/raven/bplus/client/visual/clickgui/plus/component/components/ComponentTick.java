package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components;

import me.kopamed.raven.bplus.client.feature.setting.settings.Tick;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.Component;

public class ComponentTick extends Component {
    private final Tick tickSetting;
    private final ModuleComponent moduleComponent;

    public ComponentTick(Tick tick, ModuleComponent moduleComponent){
        this.tickSetting = tick;
        this.moduleComponent = moduleComponent;
    }


}
