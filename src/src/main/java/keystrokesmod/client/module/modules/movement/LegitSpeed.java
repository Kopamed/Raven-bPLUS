package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;

public class LegitSpeed extends Module {

    public static TickSetting speed, fastFall, legitStrafe;
    public static SliderSetting speedInc;

    public LegitSpeed() {
        super("LegitSpeed", ModuleCategory.movement);

        this.registerSetting(speed = new TickSetting("Increase Speed", true));
        this.registerSetting(speedInc = new SliderSetting("Speed", 1.12, 1, 1.4, 0.01));
        this.registerSetting(fastFall = new TickSetting("Fast Fall", false));
        this.registerSetting(legitStrafe = new TickSetting("Legit Strafe", false));
    }

    @Subscribe
    public void onMoveInput(MoveInputEvent e) {
        if(speed.isToggled()) {
            e.setFriction((float) (e.getFriction()*speedInc.getInput()));
        }

        if(fastFall.isToggled()) {
            if(mc.thePlayer.fallDistance > 1.5) {
                mc.thePlayer.motionY *= 1.075;
            }
        }

        if(legitStrafe.isToggled() && !mc.thePlayer.onGround && (e.getStrafe() != 0 || e.getForward() != 0)) {
            e.setYaw(Utils.Player.getStrafeYaw(e.getForward(), e.getStrafe()));
            e.setForward(1);
            e.setStrafe(0);
        }
    }

}
