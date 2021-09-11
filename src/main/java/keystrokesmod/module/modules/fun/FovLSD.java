package keystrokesmod.module.modules.fun;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.utils.Utils;

public class FovLSD extends Module {
    public static ModuleSettingSlider speed;
    public static ModuleSettingSlider minFov;
    public static ModuleSettingSlider maxFov;
    private boolean up = true;

    public FovLSD() {
        super("FovLSD", category.fun, 0);
        this.registerSetting(speed = new ModuleSettingSlider("Speed:", 0.1D, 0.01D, 16D, 0.01D));
        this.registerSetting(minFov = new ModuleSettingSlider("Min FOV:", 15, 0, 360, 1));
        this.registerSetting(maxFov = new ModuleSettingSlider("Max FOV:", 180, 0, 360, 1));
    }

    public void guiUpdate(){
        Utils.Client.correctSliders(minFov, maxFov);
    }

    public void update(){
        if(!Utils.Player.isPlayerInGame()) return;

        guiUpdate();
        if(maxFov.getInput() == 0){
            mc.gameSettings.fovSetting = 0;
        }else {
            if(mc.gameSettings.fovSetting >= maxFov.getInput()){
                up = false;
            } else if(mc.gameSettings.fovSetting <= minFov.getInput()){
                up = true;
            }

            if(up){
                mc.gameSettings.fovSetting += speed.getInput();
            } else {
                mc.gameSettings.fovSetting += speed.getInput()*-1;
            }
        }
    }
}
