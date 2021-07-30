package keystrokesmod.module.modules.player;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;

public class AutoTool extends Module {
    private ModuleSettingTick hotkeyBack;
    private ModuleSettingTick doDelay;
    private ModuleSettingSlider minDelay;
    private ModuleSettingSlider maxDelay;
    private double startWaitTime;
    private boolean isWaiting;

    public AutoTool() {
        super("Auto Tool", category.player, 0);

        this.registerSetting(hotkeyBack = new ModuleSettingTick("Hotkey back", true));
        this.registerSetting(doDelay = new ModuleSettingTick("Random delay", true));
        this.registerSetting(minDelay = new ModuleSettingSlider("Min delay", 100, 0, 3000, 5));
        this.registerSetting(maxDelay = new ModuleSettingSlider("Max delay", 390, 0, 3000, 5));
    }


}
