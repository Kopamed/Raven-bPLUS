package keystrokesmod.client.module.modules.config;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;

public class ConfigSettings extends Module {

    public TickSetting updateConfig;

    public ConfigSettings() {
        super("Config", ModuleCategory.config);
        this.registerSetting(updateConfig = new TickSetting("Update ConfigList", false));
    }

    @Override
    public boolean canBeEnabled() {
        return false;
    }

    public void guiButtonToggled(TickSetting b) {
        b.setEnabled(false);
        Raven.configManager.discoverConfigs();
    }
}
