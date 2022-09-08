package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    public static SliderSetting a, b, c, ap, bp, cp, dt;
    public static TickSetting d, e, f;
    public static ComboSetting g;
    public Mode mode = Mode.Distance;

    public Velocity() {
        super("Velocity", ModuleCategory.combat);
        this.registerSetting(a = new SliderSetting("Horizontal", 90.0D, -100.0D, 100.0D, 1.0D));
        this.registerSetting(b = new SliderSetting("Vertical", 100.0D, -100.0D, 100.0D, 1.0D));
        this.registerSetting(c = new SliderSetting("Chance", 100.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(d = new TickSetting("Only while targeting", false));
        this.registerSetting(e = new TickSetting("Disable while holding S", false));
        this.registerSetting(f = new TickSetting("Different velo for projectiles", false));
        this.registerSetting(g = new ComboSetting("Projectiles Mode", mode));
        this.registerSetting(ap = new SliderSetting("Horizontal projectiles", 90.0D, -100.0D, 100.0D, 1.0D));
        this.registerSetting(bp = new SliderSetting("Vertical projectiles", 100.0D, -100.0D, 100.0D, 1.0D));
        this.registerSetting(cp = new SliderSetting("Chance projectiles", 100.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(dt = new SliderSetting("Distance projectiles", 3D, 0.0D, 20D, 0.1D));
    }

    @Subscribe
    public void onLivingUpdate(ForgeEvent fe) {
        if (fe.getEvent() instanceof LivingUpdateEvent) {
            if (Utils.Player.isPlayerInGame() && mc.thePlayer.maxHurtTime > 0
                    && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
                if (d.isToggled() && (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null)) {
                    return;
                }

                if (e.isToggled() && Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                    return;
                }
                if (mc.thePlayer.getLastAttacker() instanceof EntityPlayer) {
                    EntityPlayer attacker = (EntityPlayer) mc.thePlayer.getLastAttacker();
                    Item item = attacker.getCurrentEquippedItem() != null ? attacker.getCurrentEquippedItem().getItem()
                            : null;
                    if ((item instanceof ItemEgg || item instanceof ItemBow || item instanceof ItemSnow
                            || item instanceof ItemFishingRod) && mode == Mode.ItemHeld) {
                        velo();
                        return;
                    } else if (attacker.getDistanceToEntity(mc.thePlayer) > dt.getInput()) {
                        velo();
                        return;
                    }
                }

                if (c.getInput() != 100.0D) {
                    double ch = Math.random();
                    if (ch >= c.getInput() / 100.0D) {
                        return;
                    }
                }

                if (a.getInput() != 100.0D) {
                    mc.thePlayer.motionX *= a.getInput() / 100.0D;
                    mc.thePlayer.motionZ *= a.getInput() / 100.0D;
                }

                if (b.getInput() != 100.0D) {
                    mc.thePlayer.motionY *= b.getInput() / 100.0D;
                }
            }
        }
    }

    public void velo() {
        if (cp.getInput() != 100.0D) {
            double ch = Math.random();
            if (ch >= cp.getInput() / 100.0D) {
                return;
            }
        }

        if (ap.getInput() != 100.0D) {
            mc.thePlayer.motionX *= ap.getInput() / 100.0D;
            mc.thePlayer.motionZ *= ap.getInput() / 100.0D;
        }

        if (bp.getInput() != 100.0D) {
            mc.thePlayer.motionY *= bp.getInput() / 100.0D;
        }
    }

    public enum Mode {
        Distance, ItemHeld
    }
}
