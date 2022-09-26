package keystrokesmod.client.module.modules.config;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import org.lwjgl.input.Keyboard;

public class ConfigModule extends Module {

    public static ConfigModule currentConfig;
    public boolean checked;

    public ConfigModule(String cfgName) {
        super(cfgName, Module.ModuleCategory.config);
        clientConfig = true;
        showInHud = false;
    }

    @Override
    public void toggle() {
        Raven.configManager.save();
        Raven.configManager.loadConfigByName(getName());
        currentConfig = this;
    }

    @Override
    public boolean isEnabled() {
        return currentConfig == this;
    }

    @Override
    public void keybind() {
        if (!this.isEnabled() && this.keycode != 0 && Keyboard.isKeyDown(this.keycode)) {
            this.toggle();
        }
    }
}
