package keystrokesmod.module.modules.player;

import keystrokesmod.utils.Utils;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.combat.AutoClicker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoTool extends Module {
    private final ModuleSettingTick hotkeyBack;
    public static boolean isBusy;
    private double startWaitTime;
    private boolean isWaiting;
    public static int previousSlot;
    public static boolean justFinishedMining, mining;
    //public static List<Block> pickaxe = Arrays.asList(ItemBlock.class, BlockIce.class);

    public AutoTool() {
        super("Auto Tool", category.player, 0);

        this.registerSetting(hotkeyBack = new ModuleSettingTick("Hotkey back", true));
        ModuleSettingTick doDelay;
        this.registerSetting(doDelay = new ModuleSettingTick("Random delay", true));
        ModuleSettingSlider minDelay;
        this.registerSetting(minDelay = new ModuleSettingSlider("Min delay", 100, 0, 3000, 5));
        ModuleSettingSlider maxDelay;
        this.registerSetting(maxDelay = new ModuleSettingSlider("Max delay", 390, 0, 3000, 5));
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;

        ////////System.out.println(mc.currentScreen);

        if(Mouse.isButtonDown(0)) {
            if(AutoClicker.autoClickerEnabled) {
                if(!AutoClicker.breakBlocks.isToggled()) {
                    return;
                }
            }

            BlockPos lookingAtBlock = mc.objectMouseOver.getBlockPos();
            if (lookingAtBlock != null) {
                Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
                if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {
                    if(!mining) {
                        previousSlot = Utils.Player.getCurrentPlayerSlot();
                        mining = true;
                    }
                    int index = -1;
                    double speed = 1;


                    for (int slot = 0; slot <= 8; slot++) {
                        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                        if(itemInSlot != null && itemInSlot.getItem() instanceof ItemTool) {
                            BlockPos p = mc.objectMouseOver.getBlockPos();
                            Block bl = mc.theWorld.getBlockState(p).getBlock();

                            if(itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                                speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                                index = slot;
                            }
                        }
                        else if(itemInSlot != null && itemInSlot.getItem() instanceof ItemShears) {
                            BlockPos p = mc.objectMouseOver.getBlockPos();
                            Block bl = mc.theWorld.getBlockState(p).getBlock();

                            if(itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
                                speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
                                index = slot;
                            }
                        }
                    }

                    if(index == -1 || speed <= 1.1 || speed == 0) {
                    } else {
                        Utils.Player.hotkeyToSlot(index);
                    }






                }
                else{
                }
            }
            else {
            }


        }
        else {
            if(mining)
            finishMining();
        }
/*
        BlockPos p = mc.objectMouseOver.getBlockPos();
        if (p != null) {
            Block bl = mc.theWorld.getBlockState(p).getBlock();
            if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {}
        }*/
        //hotkeyToPickAxe();


    }

    public static void hotkeyToPickAxe() {
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot != null && itemInSlot.getItem() instanceof ItemPickaxe) {
                BlockPos p = mc.objectMouseOver.getBlockPos();
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                //////System.out.println(itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()));
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
}
