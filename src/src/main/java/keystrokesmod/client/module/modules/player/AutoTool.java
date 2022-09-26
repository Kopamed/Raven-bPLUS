package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.LeftClicker;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoTool extends Module {
    private final TickSetting hotkeyBack;
    private Block previousBlock;
    private boolean isWaiting;
    public static DoubleSliderSetting mineDelay;
    public static int previousSlot;
    public static boolean justFinishedMining, mining;
    public static CoolDown delay;
    // public static List<Block> pickaxe = Arrays.asList(ItemBlock.class,
    // BlockIce.class);

    public AutoTool() {
        super("Auto Tool", ModuleCategory.player);

        this.registerSetting(hotkeyBack = new TickSetting("Hotkey back", true));
        this.registerSetting(mineDelay = new DoubleSliderSetting("Max delay", 10, 50, 0, 2000, 1));
        delay = new CoolDown(0);
    }

    @Subscribe
    public void onRender2D(Render2DEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;

        // quit if the player is not tryna mine
        if (!Mouse.isButtonDown(0)) {
            if (mining)
                finishMining();
            if (isWaiting)
                isWaiting = false;
            return;
        }

        // make sure that we are allowed to breack blocks if ac is enabled
        LeftClicker autoClicker = (LeftClicker) Raven.moduleManager.getModuleByClazz(LeftClicker.class);
        if (autoClicker.isEnabled()) {
            if (!LeftClicker.breakBlocks.isToggled()) {
                return;
            }
        }

        BlockPos lookingAtBlock = mc.objectMouseOver.getBlockPos();
        if (lookingAtBlock != null) {

            Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
            if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock != null) {

                if (mineDelay.getInputMax() > 0) {
                    if (previousBlock != null) {
                        if (previousBlock != stateBlock) {
                            previousBlock = stateBlock;
                            isWaiting = true;
                            delay.setCooldown((long) ThreadLocalRandom.current().nextDouble(mineDelay.getInputMin(),
                                    mineDelay.getInputMax() + 0.01));
                            delay.start();
                        } else {
                            if (isWaiting && delay.hasFinished()) {
                                isWaiting = false;
                                previousSlot = Utils.Player.getCurrentPlayerSlot();
                                mining = true;
                                hotkeyToFastest();
                            }
                        }
                    } else {
                        previousBlock = stateBlock;
                        isWaiting = false;
                    }
                    return;
                }

                if (!mining) {
                    previousSlot = Utils.Player.getCurrentPlayerSlot();
                    mining = true;
                }

                hotkeyToFastest();
            }
        }
    }

    public void finishMining() {
        if (hotkeyBack.isToggled()) {
            Utils.Player.hotkeyToSlot(previousSlot);
        }
        justFinishedMining = false;
        mining = false;
    }

    private void hotkeyToFastest() {
        int index = -1;
        double speed = 1;

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot != null) {
                if (itemInSlot.getItem() instanceof ItemTool || itemInSlot.getItem() instanceof ItemShears) {
                    BlockPos p = mc.objectMouseOver.getBlockPos();
                    Block bl = mc.theWorld.getBlockState(p).getBlock();

                    if (itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                        speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                        index = slot;
                    }
                }
            }
        }

        if (index == -1 || speed <= 1.1) {
        } else {
            Utils.Player.hotkeyToSlot(index);
        }
    }
}
