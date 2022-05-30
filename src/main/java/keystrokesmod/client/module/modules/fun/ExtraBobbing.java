package keystrokesmod.client.module.modules.fun;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.SliderSetting;
import net.minecraft.client.entity.EntityPlayerSP;

public class ExtraBobbing extends Module {
    public static SliderSetting a;
    private boolean viewBobbing;

    public ExtraBobbing() {
        super("Extra Bobbing", Module.category.fun, 0);
        this.registerSetting(a = new SliderSetting("Level", 1.0D, 0.0D, 8.0D, 0.1D));
    }

    public void onEnable() {
        this.viewBobbing = mc.gameSettings.viewBobbing;
        if (!this.viewBobbing) {
            mc.gameSettings.viewBobbing = true;
        }

    }

    public void onDisable() {
        mc.gameSettings.viewBobbing = this.viewBobbing;
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