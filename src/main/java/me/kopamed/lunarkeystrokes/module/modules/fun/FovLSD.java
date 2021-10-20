package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;

public class FovLSD extends Module {
    public static Slider speed;
    public static RangeSlider fov;
    private boolean up = true;

    public FovLSD() {
        super("FovLSD", category.fun, 0);
        this.registerSetting(speed = new Slider("Speed:", 0.1D, 0.01D, 16D, 0.01D));
        this.registerSetting(fov = new RangeSlider("Fov Min/Max", 25, 180, 0, 360, 1));
    }



    public void update(){
        if(!Utils.Player.isPlayerInGame()) return;

        guiUpdate();
        if(fov.getInputMax() == 0){
            mc.gameSettings.fovSetting = 0;
        }else {
            if(mc.gameSettings.fovSetting >= fov.getInputMax()){
                up = false;
            } else if(mc.gameSettings.fovSetting <= fov.getInputMin()){
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
