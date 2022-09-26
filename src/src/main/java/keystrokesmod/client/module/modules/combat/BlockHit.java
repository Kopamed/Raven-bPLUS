package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BlockHit extends Module {
    public static SliderSetting range, chance;
    public static TickSetting onlyPlayers, onlyForward;
    public static DoubleSliderSetting waitMs;
    public static DoubleSliderSetting actionMs;
    public static DoubleSliderSetting hitPer;
    public static boolean executingAction;
    public static int hits, rHit;
    public static boolean call, tryStartCombo;
    private final CoolDown actionTimer = new CoolDown(0);
    private final CoolDown waitTimer = new CoolDown(0);
    public Random r = new Random();

    public BlockHit() {
        super("BlockHit", ModuleCategory.combat);

        this.registerSetting(onlyPlayers = new TickSetting("Only combo players", true));
        this.registerSetting(onlyForward = new TickSetting("Only blockhit when walking forward", false));
        this.registerSetting(waitMs = new DoubleSliderSetting("Action Time (MS)", 30, 40, 1, 300, 1));
        this.registerSetting(actionMs = new DoubleSliderSetting("Block after ... ms", 20, 30, 1, 300, 1));
        this.registerSetting(hitPer = new DoubleSliderSetting("Once every ... hits", 1, 1, 1, 10, 1));
        this.registerSetting(chance = new SliderSetting("Chance %", 100, 0, 100, 1));
        this.registerSetting(range = new SliderSetting("Range: ", 3, 1, 6, 0.05));

    }

    @Subscribe
    public void onRender(Render2DEvent e) {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (tryStartCombo && waitTimer.hasFinished()) {
            tryStartCombo = false;
            startCombo();
        }
        if (actionTimer.hasFinished() && executingAction) {
            finishCombo();
        }

    }

    @Subscribe
    public void onHit(ForgeEvent fe) {
        if (fe.getEvent() instanceof AttackEntityEvent) {
            AttackEntityEvent e = (AttackEntityEvent) fe.getEvent();

            if (isSecondCall() || executingAction)
                return;

            hits++;

            if (hits > rHit) {
                hits = 1;
                int eaSports = (int) (hitPer.getInputMax() - hitPer.getInputMin() + 1);
                rHit = ThreadLocalRandom.current().nextInt((eaSports));
                rHit += (int) hitPer.getInputMin();
            }

            if (!(e.target instanceof EntityPlayer) && onlyPlayers.isToggled()
                    || !(Math.random() <= chance.getInput() / 100) || !Utils.Player.isPlayerHoldingSword()
                    || mc.thePlayer.getDistanceToEntity(e.target) > range.getInput() || !(rHit == hits))
                return;

            tryStartCombo();
        }
    }

    private void finishCombo() {
        executingAction = false;
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, false);
        Utils.Client.setMouseButtonState(1, false);
    }

    private void startCombo() {
        if (!(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) && onlyForward.isToggled())
            return;

        executingAction = true;
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, true);
        KeyBinding.onTick(key);
        Utils.Client.setMouseButtonState(1, true);
        actionTimer.setCooldown(
                (long) ThreadLocalRandom.current().nextDouble(waitMs.getInputMin(), waitMs.getInputMax() + 0.01));
        actionTimer.start();
    }

    public void tryStartCombo() {
        tryStartCombo = true;
        waitTimer.setCooldown(
                (long) ThreadLocalRandom.current().nextDouble(actionMs.getInputMin(), actionMs.getInputMax() + 0.01));
        waitTimer.start();
    }

    private static boolean isSecondCall() {
        if (call) {
            call = false;
            return true;
        } else {
            call = true;
            return false;
        }
    }
}
