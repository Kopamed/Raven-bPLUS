package me.kopamed.raven.bplus.client.feature.module.modules.fun;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import net.minecraft.client.entity.EntityPlayerSP;

public class ExtraBobbing extends Module {
    public static NumberSetting a;
    private boolean b;

    public ExtraBobbing() {
        super("Extra Bobbing", ModuleCategory.Misc, 0);
        this.registerSetting(a = new NumberSetting("Level", 1.0D, 0.0D, 8.0D, 0.1D));
    }

    public void onEnable() {
        this.b = mc.gameSettings.viewBobbing;
        if (!this.b) {
            mc.gameSettings.viewBobbing = true;
        }

    }

    public void onDisable() {
        mc.gameSettings.viewBobbing = this.b;
    }

    public void update() {
        if (!mc.gameSettings.viewBobbing) {
            mc.gameSettings.viewBobbing = true;
        }

        if (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.cameraYaw = (float)((double)var10000.cameraYaw + a.getInput() / 2.0D);
        }

    }
}