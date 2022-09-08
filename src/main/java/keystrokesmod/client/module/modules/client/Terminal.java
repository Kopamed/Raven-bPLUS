package keystrokesmod.client.module.modules.client;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Timer;
import keystrokesmod.client.utils.Utils;

public class Terminal extends Module {
    public static boolean b;
    public static Timer animation;
    public static SliderSetting opacity;

    public Terminal() {
        super("Terminal", ModuleCategory.client);
        withEnabled(true);

        this.registerSetting(opacity = new SliderSetting("Terminal background opacity", 100, 0, 255, 1));
    }

    public void onEnable() {
        Raven.clickGui.terminal.show();
        (animation = new Timer(500.0F)).start();
    }

    @Subscribe
    public void onGameLoop(GameLoopEvent e) {
        if (Utils.Player.isPlayerInGame() && mc.currentScreen instanceof ClickGui && Raven.clickGui.terminal.hidden())
            Raven.clickGui.terminal.show();
    }

    public void onDisable() {
        Raven.clickGui.terminal.hide();

        if (animation != null) {
            animation.start();
        }
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        try {
            this.keycode = data.get("keycode").getAsInt();
            JsonObject settingsData = data.get("settings").getAsJsonObject();
            for (Setting setting : getSettings()) {
                if (settingsData.has(setting.getName())) {
                    setting.applyConfigFromJson(settingsData.get(setting.getName()).getAsJsonObject());
                }
            }
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public void resetToDefaults() {
        this.keycode = defualtKeyCode;

        for (Setting setting : this.settings) {
            setting.resetToDefaults();
        }
    }
}
