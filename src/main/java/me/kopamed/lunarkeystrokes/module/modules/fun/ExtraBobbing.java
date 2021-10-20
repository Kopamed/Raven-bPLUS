package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import net.minecraft.client.entity.EntityPlayerSP;

public class ExtraBobbing extends Module {
    public static Slider a;
    private boolean b;

    public ExtraBobbing() {
        super("Extra Bobbing", Module.category.fun, 0);
        this.registerSetting(a = new Slider("Level", 1.0D, 0.0D, 8.0D, 0.1D));
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