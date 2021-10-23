package me.kopamed.lunarkeystrokes.module.modules.combat;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Mode;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;

public class AimAssistV2 extends Module {
    public static Slider verticalSpeed; // todo
    public static Tick ignoreTeam;
    public static Mode priority; // todo
    public static Tick lock; // todo


    public static Slider speed, compliment;
    public static Slider fov;
    public static Slider distance;
    public static Tick clickAim;
    public static Tick weaponOnly;
    public static Tick aimInvis;
    public static Tick breakBlocks;
    public static Tick blatantMode;
    public static Tick ignoreFriends;

    public AimAssistV2(){
        super("AimAssist", Module.category.combat, 0);
        this.registerSetting(speed = new Slider("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));// todo
        this.registerSetting(compliment = new Slider("Speed 2", 15.0D, 2D, 97.0D, 1.0D));// todo
        this.registerSetting(fov = new Slider("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
        this.registerSetting(distance = new Slider("Distance", 3.3D, 1.0D, 10.0D, 0.1D));
        this.registerSetting(clickAim = new Tick("Click aim", true));
        this.registerSetting(breakBlocks = new Tick("Break blocks", true));
        this.registerSetting(ignoreFriends = new Tick("Ignore Friends", true));
        this.registerSetting(ignoreTeam = new Tick("Ignore teammates", true)); // todo
        this.registerSetting(weaponOnly = new Tick("Weapon only", false));
        this.registerSetting(aimInvis = new Tick("Aim invis", false));
        this.registerSetting(blatantMode = new Tick("Blatant mode", false));
    }
}
