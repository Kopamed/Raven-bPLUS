package me.kopamed.raven.bplus.client.feature.module.modules.combat;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.SelectorRunnable;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class LeftClicker extends Module {
    public static final BooleanSetting blatantMode = new BooleanSetting("Blatant", false);
    private static final RangeSetting blatantCps = new RangeSetting("CPS range", 20D, 30D, 1D, 100D, 1D);
    private static final RangeSetting cps = new RangeSetting("CPS range", 9D, 15D, 1D, 20D, 0.5D);

    public static final ComboSetting eventType = new ComboSetting("On", Listeners.TICK);

    public static final BooleanSetting breakBlocks = new BooleanSetting("Break blocks", true);
    public static final RangeSetting breakBlocksDelay = new RangeSetting("Breaking delay (MS)", 50D, 90D, 0D, 1000D, 5D);

    private boolean startedClicking = false;
    private boolean pressed = false;
    private double releaseTime;
    private double pressTime;
    private boolean breaking;

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

        breakBlocksDelay.addSelector(new SelectorRunnable() {
            @Override
            public boolean showOnlyIf() {
                return breakBlocks.isToggled();
            }
        });

        this.registerSetting(blatantMode);
        this.registerSetting(blatantCps);
        this.registerSetting(cps);
        this.registerSetting(eventType);
        this.registerSetting(breakBlocks);
        //this.registerSetting(breakBlocksDelay);
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
        if ((!Utils.Player.isPlayerInGame()) || mc.currentScreen != null || eventType.getMode() != Listeners.TICK) {
            if(startedClicking){
                startedClicking = false;
                pressed = false;
                setPressed(false);
            }
            return;
        }

        click();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if ((!Utils.Player.isPlayerInGame()) || mc.currentScreen != null || eventType.getMode() != Listeners.RENDER)
            return;

        click();
    }

    private void click(){
        Mouse.poll();
        if (Mouse.isButtonDown(0)) {
            // do checks
            if(breakBlocks.isToggled()){
                if(!breaking && onBreakableBlock()){
                    breaking = true;
                    setPressed(true);
                }
                else if(breaking && !onBreakableBlock()){
                    breaking = false;
                }
                if(breaking)
                    return;
            }


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

    public enum Listeners {
        RENDER,
        TICK;
    }

    private boolean onBreakableBlock(){
        if(mc.objectMouseOver != null){
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null) {
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                return bl != Blocks.air && !(bl instanceof BlockLiquid);
            }
        }
        return false;
    }
}
