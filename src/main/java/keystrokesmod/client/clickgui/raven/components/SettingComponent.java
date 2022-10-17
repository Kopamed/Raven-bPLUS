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
        visable = !visable;
    }

    public void hideComponent(boolean visable) {
        this.visable = visable;
    }
}
