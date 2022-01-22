package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.SelectorRunnable;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class LeftClicker extends Module {
    public static final BooleanSetting blatantMode = new BooleanSetting("Blatant", false);
    private static final RangeSetting blatantCps = new RangeSetting("CPS range", 20D, 30D, 1D, 100D, 1D);
    private static final RangeSetting cps = new RangeSetting("CPS range", 9D, 15D, 1D, 20D, 0.5D);

    public static final BooleanSetting firstHit = new BooleanSetting("First hit", "Nearly always get the first hit. Downside: other players can tell that you are autoclicking", false);

    private boolean startedClicking = false;
    private boolean pressed = false;
    private double releaseTime;
    private double pressTime;

    public LeftClicker() {
        super("Left Clicker", "Automatically left clicks for you", ModuleCategory.Combat);

        blatantCps.addSelector(new SelectorRunnable() {
            @Override
            public boolean showOnlyIf() {
                return blatantMode.isToggled();
            }
        });

        cps.addSelector(new SelectorRunnable() {
            @Override
            public boolean showOnlyIf() {
                return !blatantMode.isToggled();
            }
        });

        this.registerSetting(blatantMode);
        this.registerSetting(blatantCps);
        this.registerSetting(cps);
        this.registerSetting(firstHit);
    }

    @Override
    public void onEnable() {
        startedClicking = false;
        genTimings();
        pressed = false;
        super.onEnable();
    }

    public static RangeSetting getCPSSetting(){
        return blatantMode.isToggled() ? blatantCps : cps;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if ((event.phase == TickEvent.Phase.START && !firstHit.isToggled()) ||
                (event.phase == TickEvent.Phase.END && firstHit.isToggled()) ||
                (!Utils.Player.isPlayerInGame()) ||
                mc.currentScreen != null
        )
            return;

        Mouse.poll();
        if (Mouse.isButtonDown(0)) {
            if(!startedClicking){
                startedClicking = true;
                pressed = true;
                genTimings();
            }

            if(System.currentTimeMillis() >= releaseTime){
                if(pressed) {
                    pressed = false;
                    setPressed(false);
                } else if (System.currentTimeMillis() >= pressTime){
                    pressed = true;
                    setPressed(true);
                    genTimings();
                }
            }
            // on release gen timings
        } else if(startedClicking && !Mouse.isButtonDown(0)) {
            startedClicking = false;
            pressed = false;
            setPressed(false);
        }
    }

    private void genTimings() {
        double totalLastTime = 1000 / (
                Math.random() * (getCPSSetting().getInputMax() - getCPSSetting().getInputMin())
                        + getCPSSetting().getInputMin()
        );

        double downTime = Math.random() * totalLastTime;

        releaseTime = System.currentTimeMillis() + downTime;
        pressTime = System.currentTimeMillis() + totalLastTime;
    }

    private void setPressed(boolean b) {
        int key = mc.gameSettings.keyBindAttack.getKeyCode();
        KeyBinding.setKeyBindState(key, b);
        KeyBinding.onTick(key);
    }
}
