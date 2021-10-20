package me.kopamed.lunarkeystrokes.module.modules.render;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;

public class CustomFOV extends Module {
    public static Slider FOV;

    public CustomFOV(){
        super("CustomFOV", category.render, 0);
        this.registerSetting(FOV = new Slider("FOV:", 180, 0, 420, 1));
    }


    public void update() {
        mc.gameSettings.fovSetting = (int)FOV.getInput();
    }
}
