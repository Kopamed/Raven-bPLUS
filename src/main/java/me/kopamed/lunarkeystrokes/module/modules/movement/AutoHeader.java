package me.kopamed.lunarkeystrokes.module.modules.movement;

import io.netty.util.internal.ThreadLocalRandom;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AutoHeader extends Module {
    public static Description desc;
    public static Tick cancelDuringShift, onlyWhenHoldingSpacebar;
    public static Slider pbs;
    private double startWait;

    public AutoHeader() {
        super("AutoHeadHitter", category.movement, 0);
        this.registerSetting(desc = new Description("Spams spacebar when under blocks"));
        this.registerSetting(cancelDuringShift = new Tick("Cancel if snkeaing", true));
        this.registerSetting(onlyWhenHoldingSpacebar = new Tick("Only when holding jump", true));
        this.registerSetting(pbs = new Slider("Jump Presses per second", 12, 1, 20, 1));

        boolean jumping = false;
    }

    @Override
    public void onEnable(){
        startWait = System.currentTimeMillis();
        super.onEnable();
    }

    @SubscribeEvent
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
