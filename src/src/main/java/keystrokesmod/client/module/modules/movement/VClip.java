package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;

public class VClip extends Module {
    public static SliderSetting a;

    public VClip() {
        super("VClip", ModuleCategory.movement);
        this.registerSetting(a = new SliderSetting("Distace", 2.0D, -10.0D, 10.0D, 0.5D));
    }

    public void onEnable() {
        if (a.getInput() != 0.0D) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + a.getInput(), mc.thePlayer.posZ);
        }

        this.disable();
    }
}
