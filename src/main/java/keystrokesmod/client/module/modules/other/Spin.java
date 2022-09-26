package keystrokesmod.client.module.modules.other;

import java.util.Arrays;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;

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
            Utils.Player.sendMessageToSelf(" " + timeleft);
            //Utils.Player.sendMessageToSelf(" " + (yaw + ((finalYaw - yaw))) + " " + (float) (cd.getElapsedTime()/ (float) cd.getCooldownTime()));
            yawOffSet += lastYaw -  mc.thePlayer.rotationYaw;
            mc.thePlayer.rotationYaw = yaw + ((finalYaw - yaw) * timeleft);
        } else if (mode.getMode() == aimMode.SMOOTH) {
            percent = (float) (0.5f * (Math.sin(Math.toRadians(180f * (timeleft - 0.5f))))+ 0.5f);
            int times = (int) (percent * 100);
            String[] arr = new String[times];
            Arrays.fill(arr,"l");
            Utils.Player.sendMessageToSelf(String.join("",arr));
            //Utils.Player.sendMessageToSelf(yawOffSet + "");
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
