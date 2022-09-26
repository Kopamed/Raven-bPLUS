package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class SpeedTest extends Module {

    private CoolDown coolDown = new CoolDown(1);
    private SliderSetting delay, stopPercent;
    
    public SpeedTest() {
        super("SpeedTest", ModuleCategory.movement);
        this.registerSetting(delay = new SliderSetting("Delay", 20, 0, 300 ,1));
        this.registerSetting(stopPercent = new SliderSetting("Stop Percent", 0, 0, 200 ,1));
    }
    
    @Subscribe
    public void onRender2D(Render2DEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;
        if(mc.thePlayer.onGround && coolDown.hasFinished()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            coolDown.setCooldown((long)delay.getInput());
            coolDown.start();
        }
        if(coolDown.firstFinish()) {
            mc.thePlayer.motionY *= stopPercent.getInput()/100f;
        }
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
    }
}
