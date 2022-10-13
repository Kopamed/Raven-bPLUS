package keystrokesmod.client.clickgui.raven.components;



import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.module.setting.Setting;

public abstract class SettingComponent extends Component {

    protected Setting setting;
    protected ModuleComponent moduleComponent;

    public SettingComponent(Setting setting, ModuleComponent moduleComponent) {
        this.setting = setting;
        this.moduleComponent = moduleComponent;
        setting.setComponent(this);
    }

    public void hideComponent() {
        if(moduleComponent.settings.contains(this)) moduleComponent.settings.remove(this);
        else moduleComponent.settings.add(this);
    }

    public void hideComponent(boolean hidden) {
        if(moduleComponent.settings.contains(this) && hidden) moduleComponent.settings.remove(this);
        else if (!hidden) moduleComponent.settings.add(this);
    }
}
