package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.SelectorRunnable;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class RightClicker extends Module {
    public static final BooleanSetting blatantMode = new BooleanSetting("Blatant", false);
    private static final RangeSetting blatantCps = new RangeSetting("CPS range", 20D, 30D, 1D, 100D, 1D);
    private static final RangeSetting cps = new RangeSetting("CPS range", 9D, 15D, 1D, 20D, 0.5D);

    public static final ComboSetting eventType = new ComboSetting("On", Listeners.TICK);

    public static final BooleanSetting onlyBlocks = new BooleanSetting("Only with blocks", true);

    public static final NumberSetting clickAfter = new NumberSetting("Click after (MS)", 65D, 0D, 200D, 5D);

    private boolean startedClicking = false;
    private boolean pressed = false;
    private double releaseTime;
    private double pressTime;
    private boolean generatedDelay = false;
    private double startClickTime;

    public RightClicker() {
        super("Right Clicker", "Automatically right clicks for you", ModuleCategory.Combat);

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
        this.registerSetting(eventType);
        this.registerSetting(onlyBlocks);
        this.registerSetting(clickAfter);
    }

    @Override
    public void onEnable() {
        startedClicking = false;
        genTimings();
        pressed = false;
        super.onEnable();
        generatedDelay = false;
    }

    public static RangeSetting getCPSSetting() {
        return blatantMode.isToggled() ? blatantCps : cps;
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if ((!Utils.Player.isPlayerInGame()) || mc.currentScreen != null || eventType.getMode() != Listeners.TICK)
            return;

        click();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if ((!Utils.Player.isPlayerInGame()) || mc.currentScreen != null || eventType.getMode() != Listeners.RENDER)
            return;

        click();
    }

    private void click() {
        if(onlyBlocks.isToggled() && mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))
            return;

        Mouse.poll();
        if (Mouse.isButtonDown(1)) {
            // do checks
            if(!generatedDelay){
                startClickTime = clickAfter.getInput() > 0 ? System.currentTimeMillis() + clickAfter.getInput() : 0;
                generatedDelay = true;
            }

            if(System.currentTimeMillis() < startClickTime) {
                if((startClickTime - System.currentTimeMillis()) % 10 == 0)
                return;
            }

            if (!startedClicking) {
                startedClicking = true;
                pressed = true;
                genTimings();
            }

            if (System.currentTimeMillis() >= releaseTime) {
                if (pressed) {
                    pressed = false;
                    setPressed(false);
                } else if (System.currentTimeMillis() >= pressTime) {
                    pressed = true;
                    setPressed(true);
                    genTimings();
                }
            }
            // on release gen timings
        } else if (startedClicking && !Mouse.isButtonDown(1)) {
            startedClicking = false;
            pressed = false;
            generatedDelay = false;
            setPressed(false);
        }  else if(generatedDelay){
            generatedDelay = false;
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
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, b);
        KeyBinding.onTick(key);
    }

    public enum Listeners {
        RENDER,
        TICK;
    }
}