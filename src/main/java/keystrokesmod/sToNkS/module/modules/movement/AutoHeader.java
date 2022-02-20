package keystrokesmod.sToNkS.module.modules.movement;

import io.netty.util.internal.ThreadLocalRandom;
import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.utils.Utils;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoHeader extends Module {
    public static ModuleDesc desc;
    public static ModuleSettingTick cancelDuringShift, onlyWhenHoldingSpacebar;
    public static ModuleSettingSlider pbs;
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

    @FMLEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;
        if (cancelDuringShift.isToggled() && mc.thePlayer.isSneaking())
            return;

        if(onlyWhenHoldingSpacebar.isToggled()){
            if(!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())){
                return;
            }
        }


        if (Utils.Player.playerUnderBlock() && mc.thePlayer.onGround){
            if(startWait + (1000 / ThreadLocalRandom.current().nextDouble(pbs.getInput() - 0.543543, pbs.getInput() + 1.32748923)) < System.currentTimeMillis()){
                mc.thePlayer.jump();
                startWait = System.currentTimeMillis();
            }
        }

    }
}
