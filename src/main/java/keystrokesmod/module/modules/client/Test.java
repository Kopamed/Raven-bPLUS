package keystrokesmod.module.modules.client;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingDoubleSlider;

public class Test extends Module {
    public static ModuleSettingDoubleSlider doubleSlider;

    public Test(){
        super("Test", category.client, 0);
        this.registerSetting(doubleSlider = new ModuleSettingDoubleSlider("yes: ", 40, 30, 0, 100, 1));
    }
}
