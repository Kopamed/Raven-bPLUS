package me.kopamed.lunarkeystrokes.module.modules.world;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;

public class PingSpoof extends Module {
    public static Slider spoof;
    public static boolean toggled = false;

    public PingSpoof(){
        super("PingSpoof", category.world, 0);
        this.registerSetting(spoof = new Slider("Increase ping by", 300, 1, 100000, 10));
    }

    @Override
    public void onEnable() {
        toggled = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        toggled = false;
        super.onDisable();
    }
}
