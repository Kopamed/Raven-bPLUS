package me.kopamed.lunarkeystrokes.module.modules.player;

import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.utils.CoolDown;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.combat.AutoClicker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoTool extends Module {
    private final Tick hotkeyBack;
    private Block previousBlock;
    private boolean isWaiting;
    public static RangeSlider mineDelay;
    public static int previousSlot;
    public static boolean justFinishedMining, mining;
    public static CoolDown delay;
    //public static List<Block> pickaxe = Arrays.asList(ItemBlock.class, BlockIce.class);

    public AutoTool() {
        super("Auto Tool", category.player, 0);

        this.registerSetting(hotkeyBack = new Tick("Hotkey back", true));
        this.registerSetting(mineDelay = new RangeSlider("Max delay", 10, 50, 0, 2000, 1));
        delay = new CoolDown(0);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;

        // quit if the player is not tryna mine
        if(!Mouse.isButtonDown(0)){
            if(mining)
                finishMining();
            if(isWaiting && delay.hasTimeElapsed()) {
                isWaiting = false;
                previousSlot = Utils.Player.getCurrentPlayerSlot();
                mining = true;
                hotkeyToFastest();
            }
            return;
        }



        //make sure that we are allowed to breack blocks if ac is enabled
        if(AutoClicker.autoClickerEnabled) {
            if(!AutoClicker.breakBlocks.isToggled()) {
                return;
            }
        }

        BlockPos lookingAtBlock;
        try{
        lookingAtBlock = mc.objectMouseOver.getBlockPos();
        } catch (NullPointerException bigFBois) {
            return;
        }
        if (lookingAtBlock != null) {

            Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
            if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {

                if(mineDelay.getInputMax() > 0){
                    if(previousBlock != null){
                        if(previousBlock!=stateBlock){
                            previousBlock = stateBlock;
                            isWaiting = true;
                            delay.setCooldown((long)ThreadLocalRandom.current().nextDouble(mineDelay.getInputMin(), mineDelay.getInputMax() + 0.01));
                            delay.start();
                        } else {
                            if(isWaiting && delay.hasTimeElapsed()) {
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

                if(!mining) {
                    previousSlot = Utils.Player.getCurrentPlayerSlot();
                    mining = true;
                }

                hotkeyToFastest();
            }
        }
    }

    public void finishMining(){
        if(hotkeyBack.isToggled()) {
            Utils.Player.hotkeyToSlot(previousSlot);
        }
        justFinishedMining = false;
        mining = false;
    }

    private void hotkeyToFastest(){
        int index = -1;
        double speed = 1;


        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot != null) {
                if( itemInSlot.getItem() instanceof ItemTool || itemInSlot.getItem() instanceof ItemShears){
                    BlockPos p = mc.objectMouseOver.getBlockPos();
                    Block bl = mc.theWorld.getBlockState(p).getBlock();

                    if(itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                        speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                        index = slot;
                    }
                }
            }
        }

        if(index == -1 || speed <= 1.1 || speed == 0) {
        } else {
            Utils.Player.hotkeyToSlot(index);
        }
    }
}
