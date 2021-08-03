package keystrokesmod.module.modules.player;

import io.netty.util.internal.ThreadLocalRandom;
import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoHeader extends Module {
    public static ModuleDesc desc;
    public static ModuleSettingTick cancelDuringShift;
    public static ModuleSettingSlider pbs;
    private boolean jumping, holding;
    private double pressSpeed, holdLength;
    private double lastPress, holdingSince, releasedSince, ppsDelay, holdTime, releaseTime, holdStart, releaseStart;
    private double startWait;

    public AutoHeader() {
        super("AutoHeadHeadrer", category.player, 0);
        this.registerSetting(desc = new ModuleDesc("Spams spacebar when under blocks"));
        this.registerSetting(cancelDuringShift = new ModuleSettingTick("Cancel if snkeaing", true));
        this.registerSetting(pbs = new ModuleSettingSlider("Jump Presses per second", 12, 1, 20, 1));

        jumping = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable(){
        startWait = System.currentTimeMillis();
        super.onEnable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if (!ay.isPlayerInGame())
            return;
        if (cancelDuringShift.isToggled() && mc.thePlayer.isSneaking())
            return;



        if (ay.playerUnderBlock() && mc.thePlayer.onGround){
            if(startWait + (1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.543543, pbs.getInput() + 1.32748923)) < System.currentTimeMillis()){
                mc.thePlayer.jump();
                startWait = System.currentTimeMillis();
            }
        }

/*
        pressSpeed = 1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.5D, pbs.getInput() + 0.5D);
        holdLength = pressSpeed / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.5D, pbs.getInput() + 0.5D);
*//*
        if(ay.playerUnderBlock()){
            if (holding && System.currentTimeMillis() - holdStart > holdTime){
                holding = false;
                releaseStart = System.currentTimeMillis();
                if (holdStart + holdTime < releaseStart){
                    releaseStart = holdStart + holdTime;
                }
                jump(false);
            } else if (!holding && System.currentTimeMillis() - releaseStart > releaseTime) {
                holding = true;
                holdStart = System.currentTimeMillis();
                jump(true);
                genTimings();
            }
*/
/*

            if (System.currentTimeMillis() - lastPress > pressSpeed * 1000) {
                lastPress = System.currentTimeMillis();
                if (holdingSince < lastPress){
                    holdingSince = lastPress;
                }
                jump(true);
            } else if (System.currentTimeMillis() - holdingSince > holdLength * 1000) {
                jump(false);
            }*/

    }


    private void jump(boolean jump) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), jump);
        //KeyBinding.onTick(mc.gameSettings.keyBindJump.getKeyCode());
    }

    private void genTimings() {
        ppsDelay = 1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.5, pbs.getInput() + 0.5);
        holdTime = ppsDelay * ThreadLocalRandom.current().nextDouble(57.6432889, 86.84237846);
        releasedSince = ppsDelay - holdTime;
    }
}
