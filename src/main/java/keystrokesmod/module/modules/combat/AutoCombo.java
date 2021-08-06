package keystrokesmod.module.modules.combat;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;

import javax.management.Descriptor;

public class AutoCombo extends Module {
    public static ModuleSettingSlider comboMode;
    public static ModuleDesc comboModeDesc;
    public AutoCombo() {
        super("Auto Combo", category.combat, 0);
        this.registerSetting(comboMode = new ModuleSettingSlider("Value: ", 1, 1 ,3, 1));
        this.registerSetting(comboModeDesc = new ModuleDesc("Mode: BlockHit"));
    }


    public void guiUpdate() {
        comboModeDesc.setDesc(ay.md + ComboMode.values()[(int)(comboMode.getInput() - 1)]);
    }





    public static enum ComboMode {
        BLOCKHIT,
        WTAP,
        STAP;
    }
}
