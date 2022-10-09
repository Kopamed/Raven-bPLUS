package keystrokesmod.client.module.modules.other;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;

public class Spin extends Module {

    private SliderSetting time, fov;
    private CoolDown cd = new CoolDown(1);
    private ComboSetting mode;
    private float yaw, finalYaw, lastYaw, yawOffSet;

    public Spin() {
        super("Spin", ModuleCategory.other);
        this.registerSetting(mode = new ComboSetting("Mode", aimMode.SMOOTH));
        this.registerSetting(fov = new SliderSetting("fov", 30, -360, 360, 1));
        this.registerSetting(time = new SliderSetting("time (ms)", 200, 0, 1000, 1));
    }

    @Override
    public void onEnable() {
        cd.setCooldown((long) time.getInput());
        cd.start();
        yawOffSet = 0;
        lastYaw = mc.thePlayer.rotationYaw;
        yaw = mc.thePlayer.rotationYaw;
        finalYaw = (float) (mc.thePlayer.rotationYaw + fov.getInput());
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if(cd.hasFinished()) {
            this.disable();
            return;
        }
        float timeleft = (float) (cd.getElapsedTime()/(float) cd.getCooldownTime());
        float percent = 0;
        if(mode.getMode() == aimMode.LINEAR) {
            percent = timeleft;
            yawOffSet += lastYaw -  mc.thePlayer.rotationYaw;
            mc.thePlayer.rotationYaw = yaw + ((finalYaw - yaw) * timeleft);
        } else if (mode.getMode() == aimMode.SMOOTH) {
            percent = (float) ((0.5f * (Math.sin(Math.toRadians(180f * (timeleft - 0.5f)))))+ 0.5f);
            yawOffSet += mc.thePlayer.rotationYaw - lastYaw;
            mc.thePlayer.rotationYaw = (float) (yaw + ((finalYaw - yaw) * percent));
        }
        lastYaw =  mc.thePlayer.rotationYaw;
    }

    public enum aimMode {
        SMOOTH,
        LINEAR,
    }
}
