package keystrokesmod.sToNkS.module.modules.render;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;

public class CustomFOV extends Module {
    public static ModuleSettingSlider FOV;

    public CustomFOV(){
        super("CustomFOV", category.render, 0);
        this.registerSetting(FOV = new ModuleSettingSlider("FOV:", 180, 0, 420, 1));
    }


    public void update() {
        mc.gameSettings.fovSetting = (int)FOV.getInput();
    }
}
