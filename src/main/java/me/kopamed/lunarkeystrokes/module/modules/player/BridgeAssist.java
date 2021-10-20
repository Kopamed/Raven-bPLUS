package me.kopamed.lunarkeystrokes.module.modules.player;

import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BridgeAssist extends Module {
    private final Tick setLook;
    private final Tick onSneak;
    private final Tick workWithSafeWalk;
    private final Slider waitFor;
    private final Slider glideTime;
    private final Slider assistMode;
    private final Slider assistRange;
    private final Description assistModeDesc;
    private boolean waitingForAim;
    private boolean gliding;
    private long startWaitTime;
    private final float[] godbridgePos = {75.6f, -315, -225, -135, -45, 0, 45, 135, 225, 315};
    private final float[] moonwalkPos = {79.6f, -340, -290, -250, -200, -160, -110, -70, -20, 0, 20, 70, 110, 160, 200, 250, 290, 340};
    private final float[] breezilyPos = {79.9f, -360, -270, -180, -90, 0, 90, 180, 270, 360};
    private final float[] normalPos = {78f, -315, -225, -135, -45, 0, 45, 135, 225, 315};
    private double speedYaw, speedPitch;
    private float waitingForYaw, waitingForPitch;


    public BridgeAssist() {
        super("Bridge Assist", category.player, 0);
        Description goodAdvice;
        this.registerSetting(goodAdvice = new Description("Best with fastplace, not autoplace"));
        this.registerSetting(waitFor = new Slider("Wait time (ms)", 500, 0, 5000, 25));
        this.registerSetting(setLook = new Tick("Set look pos", true));
        this.registerSetting(onSneak = new Tick("Work only when sneaking", true));
        this.registerSetting(workWithSafeWalk= new Tick("Work with safewalk", false));
        this.registerSetting(assistRange = new Slider("Assist range", 10.0D, 1.0D, 40.0D, 1.0D));
        this.registerSetting(glideTime = new Slider("Glide speed", 500, 1, 201, 5));
        this.registerSetting(assistMode = new Slider("Value", 1.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(assistModeDesc = new Description("Mode: GodBridge"));
    }

    public void guiUpdate() {
        assistModeDesc.setDesc(Utils.md + Utils.Modes.BridgeMode.values()[(int)(assistMode.getInput() - 1.0D)].name());
    }

    @Override
    public void onEnable() {
        this.waitingForAim = false;
        this.gliding = false;
        super.onEnable();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame()) {
            return;
        }

        if (ModuleManager.safeWalk.isEnabled()) {
            if (!workWithSafeWalk.isToggled()) {
                return;
            }
        }

        if (!(Utils.Player.playerOverAir() && mc.thePlayer.onGround)) {
            //////////System.out.println("Return bc not on air ");
            return;
        }

        if (onSneak.isToggled()) {
            if (!mc.thePlayer.isSneaking()) {
                //////////System.out.println("Nibba aint shiftin");
                return;
            }
        }


        if (gliding){
            float fuckedYaw = mc.thePlayer.rotationYaw;
            float fuckedPitch = mc.thePlayer.rotationPitch;

            float yaw = fuckedYaw - ((int)fuckedYaw/360) * 360;
            float pitch = fuckedPitch - ((int)fuckedPitch/360) * 360;

            double ilovebloat1 = yaw - speedYaw,
                    ilovebloat2 = yaw + speedYaw,
                    ilovebloat3 = pitch - speedPitch,
                    ilovebloat4 = pitch + speedPitch;

            if (ilovebloat1 < 0)
                ilovebloat1 *= -1;

            if (ilovebloat2 < 0)
                ilovebloat2 *= -1;

            if (ilovebloat3 < 0)
                ilovebloat3 *= -1;

            if (ilovebloat4 < 0)
                ilovebloat4 *= -1;

            if (this.speedYaw > ilovebloat1 || this.speedYaw > ilovebloat2)
                mc.thePlayer.rotationYaw = this.waitingForYaw;

            if (this.speedPitch > ilovebloat3 || this.speedPitch > ilovebloat4)
                mc.thePlayer.rotationPitch = this.waitingForPitch;

            if (mc.thePlayer.rotationYaw < this.waitingForYaw)
                mc.thePlayer.rotationYaw += this.speedYaw;

            if (mc.thePlayer.rotationYaw > this.waitingForYaw)
                mc.thePlayer.rotationYaw -= this.speedYaw;

            if (mc.thePlayer.rotationPitch > this.waitingForPitch)
                mc.thePlayer.rotationPitch -= this.speedPitch;

            if (mc.thePlayer.rotationYaw == this.waitingForYaw && mc.thePlayer.rotationPitch == this.waitingForPitch) {
                gliding = false;
                this.waitingForAim = false;
            }
            //////////System.out.println("gliding to " + this.waitingForPitch + " " + this.waitingForYaw);
            return;
        }

        //////////System.out.println("Checking if waiting for aim");
        if (!waitingForAim) {
            waitingForAim = true;
            startWaitTime = System.currentTimeMillis();
            ////////////System.out.println("Timer start");
            //////////System.out.println("Started waiting for aim");
            return;
        }

        if (System.currentTimeMillis() - startWaitTime < waitFor.getInput())
            return;

        //////////System.out.println("Yes starting");
        float fuckedYaw = mc.thePlayer.rotationYaw;
        float fuckedPitch = mc.thePlayer.rotationPitch;

        float yaw = fuckedYaw - ((int)fuckedYaw/360) * 360;
        float pitch = fuckedPitch - ((int)fuckedPitch/360) * 360;

        //45, 75 west
        //135, 75 north
        //-135 south
        //-45 east
        float range = (float) assistRange.getInput();

        switch (Utils.Modes.BridgeMode.values()[(int)(assistMode.getInput() - 1.0D)]) {
            case GODBRIDGE:
                if (godbridgePos[0] >= (pitch - range) && godbridgePos[0] <= (pitch + range)) {
                    for (int k = 1; k < godbridgePos.length; k++) {
                        if (godbridgePos[k] >= (yaw - range) && godbridgePos[k] <= (yaw + range)) {
                            aimAt(godbridgePos[0], godbridgePos[k], fuckedYaw, fuckedPitch);
                            this.waitingForAim = false;
                            return;
                        }
                    }
                    //////////System.out.println(pitch + " " + yaw);
                }


            case MOONWALK:
                if (moonwalkPos[0] >= (pitch - range) && moonwalkPos[0] <= (pitch + range)) {
                    for (int k = 1; k < moonwalkPos.length; k++) {
                        if (moonwalkPos[k] >= (yaw - range) && moonwalkPos[k] <= (yaw + range)) {
                            aimAt(moonwalkPos[0], moonwalkPos[k], fuckedYaw, fuckedPitch);
                            this.waitingForAim = false;
                            return;
                        }
                    }
                    //////////System.out.println(pitch + " " + yaw);
                }

            case BREEZILY:
                if (breezilyPos[0] >= (pitch - range) && breezilyPos[0] <= (pitch + range)) {
                    for (int k = 1; k < breezilyPos.length; k++) {
                        if (breezilyPos[k] >= (yaw - range) && breezilyPos[k] <= (yaw + range)) {
                            aimAt(breezilyPos[0], breezilyPos[k], fuckedYaw, fuckedPitch);
                            this.waitingForAim = false;
                            return;
                        }
                    }
                    //////////System.out.println(pitch + " " + yaw);
                }

            case NORMAL:
                if (normalPos[0] >= (pitch - range) && normalPos[0] <= (pitch + range)) {
                    for (int k = 1; k < normalPos.length; k++) {
                        if (normalPos[k] >= (yaw - range) && normalPos[k] <= (yaw + range)) {
                            aimAt(normalPos[0], normalPos[k], fuckedYaw, fuckedPitch);
                            this.waitingForAim = false;
                            return;
                        }
                    }
                    //////////System.out.println(pitch + " " + yaw);
                }
        }
        this.waitingForAim = false;
    }

    public void aimAt(float pitch, float yaw, float fuckedYaw, float fuckedPitch){
       if(setLook.isToggled()) {
               mc.thePlayer.rotationPitch = pitch + ((int)fuckedPitch/360) * 360;
               mc.thePlayer.rotationYaw = yaw;
        }
    }
}
