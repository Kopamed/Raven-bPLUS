package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Parkour extends Module {

    private final CoolDown cd = new CoolDown(1);

    public Parkour() {
        super("Parkour", ModuleCategory.player);
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && cd.firstFinish())
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);

        if (mc.thePlayer.onGround && Utils.Player.playerOverAir()
                && (mc.thePlayer.motionX != 0 || mc.thePlayer.motionZ != 0)) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            cd.setCooldown(10);
            cd.start();
        }
    }

}
