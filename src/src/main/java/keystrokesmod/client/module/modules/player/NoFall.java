package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;

public class NoFall extends Module {
    public static DescriptionSetting warning;
    public static ComboSetting mode;

    int ticks;
    double dist;
    boolean spoofing;

    public NoFall() {
        super("NoFall", ModuleCategory.player);

        this.registerSetting(warning = new DescriptionSetting("HypixelSpoof silent flags."));
        this.registerSetting(mode = new ComboSetting("Mode", Mode.Spoof));
    }

    @Subscribe
    public void onTick(TickEvent e) {
        switch ((Mode) mode.getMode()) {
        case Spoof:
            if ((double) mc.thePlayer.fallDistance > 2.5D) {
                mc.thePlayer.onGround = true;
            }
            break;

        case HypixelSpoof:
            if (mc.thePlayer.onGround) {
                ticks = 0;
                dist = 0;
                spoofing = false;
            } else {
                if (mc.thePlayer.fallDistance > 2) {
                    if (spoofing) {
                        ticks++;
                        mc.thePlayer.onGround = true;

                        if (ticks >= 2) {
                            spoofing = false;
                            ticks = 0;
                            dist = mc.thePlayer.fallDistance;
                        }
                    } else {
                        if (mc.thePlayer.fallDistance - dist > 2) {
                            spoofing = true;
                        }
                    }
                }
            }
            break;

        case Verus:
            if (mc.thePlayer.onGround) {
                dist = 0;
                spoofing = false;
            } else {
                if (mc.thePlayer.fallDistance > 2) {
                    if (spoofing) {
                        mc.thePlayer.onGround = true;
                        mc.thePlayer.motionY = 0;
                        spoofing = false;
                        dist = mc.thePlayer.fallDistance;
                    } else {
                        if (mc.thePlayer.fallDistance - dist > 2) {
                            spoofing = true;
                        }
                    }
                }
            }
            break;
        }
    }

    public enum Mode {
        Spoof, HypixelSpoof, Verus
    }
}
