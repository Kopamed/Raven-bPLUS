package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class AutoJump extends Module {
    public static TickSetting b;
    private boolean c;

    public AutoJump() {
        super("AutoJump", ModuleCategory.player);
        this.registerSetting(b = new TickSetting("Cancel when shifting", true));
    }

    public void onDisable() {
        this.ju(this.c = false);
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (Utils.Player.isPlayerInGame()) {
            if (mc.thePlayer.onGround && (!b.isToggled() || !mc.thePlayer.isSneaking())) {
                if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox()
                        .offset(mc.thePlayer.motionX / 3.0D, -1.0D, mc.thePlayer.motionZ / 3.0D)).isEmpty()) {
                    this.ju(this.c = true);
                } else if (this.c) {
                    this.ju(this.c = false);
                }
            } else if (this.c) {
                this.ju(this.c = false);
            }

        }
    }

    private void ju(boolean ju) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), ju);
    }
}
