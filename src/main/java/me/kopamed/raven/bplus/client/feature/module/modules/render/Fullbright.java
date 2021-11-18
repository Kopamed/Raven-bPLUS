package me.kopamed.raven.bplus.client.feature.module.modules.render;

import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.Description;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fullbright extends Module {
    private float defaultGamma;
    private final float clientGamma;

    public Fullbright() {
        super("Fullbright", ModuleCategory.render, 0);

        Description description;
        this.registerSetting(description = new Description("No more darkness!"));
        this.clientGamma = 10000;
    }

    @Override
    public void onEnable() {
        this.defaultGamma = mc.gameSettings.gammaSetting;
        super.onEnable();
    }

    @Override
    public void onDisable(){
        super.onEnable();
        mc.gameSettings.gammaSetting = this.defaultGamma;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!Utils.Player.isPlayerInGame()) {
            onDisable();
            return;
        }

        if (mc.gameSettings.gammaSetting != clientGamma)
            mc.gameSettings.gammaSetting = clientGamma;
    }
}
