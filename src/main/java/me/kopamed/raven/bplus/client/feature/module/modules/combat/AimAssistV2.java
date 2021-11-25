package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;

public class AimAssistV2 extends Module {
    public static NumberSetting verticalSpeed; // todo
    public static BooleanSetting ignoreTeam;
    public static ComboSetting priority; // todo
    public static BooleanSetting lock; // todo


    public static NumberSetting speed, compliment;
    public static NumberSetting fov;
    public static NumberSetting distance;
    public static BooleanSetting clickAim;
    public static BooleanSetting weaponOnly;
    public static BooleanSetting aimInvis;
    public static BooleanSetting breakBlocks;
    public static BooleanSetting blatantMode;
    public static BooleanSetting ignoreFriends;

    public AimAssistV2(){
        super("AimAssist", ModuleCategory.Combat, 0);
        this.registerSetting(speed = new NumberSetting("Speed 1", 45.0D, 5.0D, 100.0D, 1.0D));// todo
        this.registerSetting(compliment = new NumberSetting("Speed 2", 15.0D, 2D, 97.0D, 1.0D));// todo
        this.registerSetting(fov = new NumberSetting("FOV", 90.0D, 15.0D, 360.0D, 1.0D));
        this.registerSetting(distance = new NumberSetting("Distance", 3.3D, 1.0D, 10.0D, 0.1D));
        this.registerSetting(clickAim = new BooleanSetting("Click aim", true));
        this.registerSetting(breakBlocks = new BooleanSetting("Break blocks", true));
        this.registerSetting(ignoreFriends = new BooleanSetting("Ignore Friends", true));
        this.registerSetting(ignoreTeam = new BooleanSetting("Ignore teammates", true)); // todo
        this.registerSetting(weaponOnly = new BooleanSetting("Weapon only", false));
        this.registerSetting(aimInvis = new BooleanSetting("Aim invis", false));
        this.registerSetting(blatantMode = new BooleanSetting("Blatant mode", false));
    }
}
