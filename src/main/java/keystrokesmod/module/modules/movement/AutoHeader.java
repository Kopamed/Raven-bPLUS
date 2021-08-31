package keystrokesmod.module.modules.movement;

import io.netty.util.internal.ThreadLocalRandom;
import keystrokesmod.utils.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoHeader extends Module {
    public static ModuleDesc desc;
    public static ModuleSettingTick cancelDuringShift, onlyWhenHoldingSpacebar;
    public static ModuleSettingSlider pbs;
    private boolean holding;
    private double pressSpeed, holdLength;
    private double lastPress;
    private double holdingSince;
    private double releaseTime;
    private double holdStart;
    private double releaseStart;
    private double startWait;

    public AutoHeader() {
        super("AutoHeadHitter", category.movement, 0);
        this.registerSetting(desc = new ModuleDesc("Spams spacebar when under blocks"));
        this.registerSetting(cancelDuringShift = new ModuleSettingTick("Cancel if snkeaing", true));
        this.registerSetting(onlyWhenHoldingSpacebar = new ModuleSettingTick("Only when holding jump", true));
        this.registerSetting(pbs = new ModuleSettingSlider("Jump Presses per second", 12, 1, 20, 1));

        boolean jumping = false;
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

        if(onlyWhenHoldingSpacebar.isToggled()){
            if(!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())){
                return;
            }
        }


        if (ay.playerUnderBlock() && mc.thePlayer.onGround){
            if(startWait + (1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.543543, pbs.getInput() + 1.32748923)) < System.currentTimeMillis()){
                mc.thePlayer.jump();
                startWait = System.currentTimeMillis();
            }
        }

/*
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

    }


    private void jump(boolean jump) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), jump);
        //KeyBinding.onTick(mc.gameSettings.keyBindJump.getKeyCode());
    }

    private void genTimings() {
        double ppsDelay = 1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.5, pbs.getInput() + 0.5);
        double holdTime = ppsDelay * ThreadLocalRandom.current().nextDouble(57.6432889, 86.84237846);
        double releasedSince = ppsDelay - holdTime;
    }
}
