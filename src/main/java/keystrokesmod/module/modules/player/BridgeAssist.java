package keystrokesmod.module.modules.player;

import keystrokesmod.ay;
import keystrokesmod.module.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

public class BridgeAssist extends Module {
    private ModuleSettingTick setLook;
    private ModuleSettingTick workWithSafeWalk;
    private ModuleSettingSlider waitFor;
    private ModuleSettingSlider assistMode;
    private ModuleSettingSlider assistRange;
    private ModuleDesc assistModeDesc;
    private boolean waitingForAim;
    private long waitTime;
    private long startWaitTime;
    private float range;
    private float godbridgePitch, godbridgeYawWest, godbridgeYawEast, godbridgeYawNorth, godbridgeYawSouth;

    public BridgeAssist() {
        super("Bridge Assist", category.player, 0);

        this.registerSetting(waitFor = new ModuleSettingSlider("Wait time (ms)", 500, 0, 5000, 25));
        this.registerSetting(setLook = new ModuleSettingTick("Set look pos", true));
        this.registerSetting(workWithSafeWalk= new ModuleSettingTick("Work with safewalk", false));
        this.registerSetting(assistRange = new ModuleSettingSlider("Assist range", 10.0D, 1.0D, 40.0D, 1.0D));
        this.registerSetting(assistMode = new ModuleSettingSlider("Value", 1.0D, 1.0D, 2.0D, 1.0D));
        this.registerSetting(assistModeDesc = new ModuleDesc("Mode: GodBridge"));
        this.godbridgePitch = 75.9f;
        this.godbridgeYawWest = 45;
        this.godbridgeYawEast = -45;
        this.godbridgeYawNorth = 135;
        this.godbridgeYawSouth = -135;
    }

    public void guiUpdate() {
        assistModeDesc.setDesc(ay.md + ay.BridgeMode.values()[(int)(assistMode.getInput() - 1.0D)].name());
    }

    @Override
    public void onEnable() {
        this.waitingForAim = false;
        super.onEnable();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (ModuleManager.safeWalk.isEnabled()) {
            if (!workWithSafeWalk.isToggled())
                return;
        }

        if (!(ay.playerOverAir() && mc.thePlayer.onGround))
             return;



        if (!waitingForAim) {
            waitingForAim = true;
            startWaitTime = System.currentTimeMillis();
            //System.out.println("Timer start");
            return;
        }

        if (System.currentTimeMillis() - startWaitTime < waitFor.getInput())
            return;

        //System.out.println("Yes starting");
        float yaw = mc.thePlayer.rotationYaw;
        float pitch = mc.thePlayer.rotationPitch;

        //45, 75 west
        //135, 75 north
        //-135 south
        //-45 east
        range = (float)assistRange.getInput();

        if (pitch >= (godbridgePitch - range) && pitch <= (godbridgePitch + range)) {
            //System.out.println("deciding yaw");
            if (yaw >= (godbridgeYawWest - range) && yaw <= (godbridgeYawWest + range))
                aimAt(godbridgePitch, godbridgeYawWest);
            else if (yaw >= (godbridgeYawEast - range) && yaw <= (godbridgeYawEast + range))
                aimAt(godbridgePitch, godbridgeYawEast);
            else if (yaw >= (godbridgeYawNorth - range) && yaw <= (godbridgeYawNorth + range))
                aimAt(godbridgePitch, godbridgeYawNorth);
            else if (yaw >= (godbridgeYawSouth - range) && yaw <= (godbridgeYawSouth + range))
                aimAt(godbridgePitch, godbridgeYawSouth);
            else{
                //System.out.println("Nithing");
                }
        }
        this.waitingForAim = false;
    }

    public void aimAt(float pitch, float yaw){
       if(setLook.isToggled()) {
           //System.out.println("Setting aim");
           mc.thePlayer.rotationPitch = pitch;
           mc.thePlayer.rotationYaw = yaw;
        }
    }
}
