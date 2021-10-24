package me.kopamed.lunarkeystrokes.module.modules.player;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Mode;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

public class GhostBlocks extends Module {
    public static Description help;
    public static Mode withItem;
    public static Mode usedItem;
    public static Slider airRange;
    public static Tick placeIfOccupied;

    private BlockPos placedPos;
    private World inWorld;

    public GhostBlocks(){
        super("GhostBlocks", category.player);
        this.registerSetting(help = new Description("RMB w/ fist to place blocks"));
        this.registerSetting(withItem = new Mode("When holding", new String[]{"Nothing", "Sword", "Tool", "Block"}, 0));
        this.registerSetting(usedItem = new Mode("With block", new String[]{"Diamond", "Wool", "Logs", "Barrier", }, 0));
        this.registerSetting(airRange = new Slider("Range when looking at air", 3, 0, 50, 1));
        this.registerSetting(placeIfOccupied = new Tick("Place if area is occupied" , false));
    }

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public void m(MouseEvent e) {
        if (e.buttonstate &&  e.button == 1) {
            if(holdingYes())
                rightClick();
        }
    }

    private boolean holdingYes() {
        ItemStack itemStack = mc.thePlayer.inventory.getCurrentItem();
        try{
            switch (withItem.getMode()){
                case "Nothing":
                    return itemStack == null;
                case "Sword":
                    return itemStack.getItem() instanceof ItemSword;
                case "Tool":
                    return itemStack.getItem() instanceof ItemTool;
                case "Block":
                    return itemStack.getItem()instanceof ItemBlock;
            }
        } catch (NullPointerException e){}

        return false;
    }

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public void o(PlayerInteractEvent e){
        if(e.pos == null || placedPos == null || inWorld == null)
            return;
        if(e.pos == placedPos && e.world == inWorld){
            e.setCanceled(true);
        }
    }


    private void rightClick(){
        BlockPos blockPos = mc.objectMouseOver.getBlockPos();
        placedPos = blockPos;
        inWorld = mc.theWorld;
        Block e;
        try {
            e = mc.theWorld.getBlockState(blockPos).getBlock();

        } catch (NullPointerException bru){
            return;
        }
        e = mc.theWorld.getBlockState(blockPos).getBlock();
        boolean air = (e instanceof BlockAir);


        if(blockPos == null){

        } else{
            int y = blockPos.getY(), x = blockPos.getX(), z = blockPos.getZ();
            //mc.thePlayer.swingItem();
            EnumFacing were = mc.objectMouseOver.sideHit;
            if(were == EnumFacing.UP){
                y = (int)blockPos.getY() + 1;
                if(air){
                    y+= 3 - airRange.getInput();
                }
            } else if(were == EnumFacing.DOWN){
                y = (int)blockPos.getY() - 1;
                if(air){
                    y-= 4 + airRange.getInput();
                }
            } else if(were == EnumFacing.EAST){
                x = (int)blockPos.getX() + 1;
                if(air){
                    x+= 3 - airRange.getInput();
                }
            }else if(were == EnumFacing.WEST){
                x = (int)blockPos.getX() - 1;
                if(air){
                    x-= 3 + airRange.getInput();
                }
            }else if(were == EnumFacing.SOUTH){
                z = (int)blockPos.getZ() + 1;
                if(air){
                    z+= 3 - airRange.getInput();
                }
            }else if(were == EnumFacing.NORTH){
                z = (int)blockPos.getZ() - 1;
                if(air){
                    z-= 3 + airRange.getInput();
                }
            }

            IBlockState theYes = Blocks.diamond_block.getDefaultState();

            switch (usedItem.getMode()){
                case "Wool":
                    theYes = Blocks.wool.getDefaultState();
                    break;
                case "Logs":
                    theYes = Blocks.log.getDefaultState();
                    break;
                case "Barrier":
                    theYes = Blocks.barrier.getDefaultState();
                    break;
            }

            BlockPos placeBlock = new BlockPos(x, y, z);
            //Utils.Player.sendMessageToSelf("Player in block: " + isPlayerInBlock(placeBlock));
            if(isPlayerInBlock(placeBlock) && !placeIfOccupied.isToggled())
                return;


            mc.theWorld.setBlockState(placeBlock, theYes);
        }
    }

    private boolean isPlayerInBlock(BlockPos blockPos){
        double bruh = mc.thePlayer.width/2;
        //System.out.println("X: LowerBound: " + Math.floor(mc.thePlayer.posX-bruh) + " x: " + blockPos.getX() + " UpperBound: " + mc.thePlayer.posX+bruh + " PosX: " + mc.thePlayer.posX);
        //System.out.println("Y: LowerBound: " + Math.floor(mc.thePlayer.posY) + " Y: " + blockPos.getY() + " UpperBound: " + mc.thePlayer.posY+mc.thePlayer.height + " PosY: " + mc.thePlayer.posY);
        //System.out.println("Z: LowerBound: " + Math.floor(mc.thePlayer.posZ-bruh) + " z: " + blockPos.getZ() + " UpperBound: " + mc.thePlayer.posZ+bruh + " PosZ: " + mc.thePlayer.posZ);

        return blockPos.getX() >= Math.floor(mc.thePlayer.posX-bruh) && blockPos.getX() <= mc.thePlayer.posX+bruh && blockPos.getZ() >= Math.floor(mc.thePlayer.posZ-bruh) && blockPos.getZ() <= mc.thePlayer.posZ+bruh && blockPos.getY() >= Math.floor(mc.thePlayer.posY) && blockPos.getY() <= mc.thePlayer.posY + mc.thePlayer.height;
    }
}
