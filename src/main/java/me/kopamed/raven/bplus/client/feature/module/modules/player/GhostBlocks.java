package me.kopamed.raven.bplus.client.feature.module.modules.player;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.ComboSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
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

public class GhostBlocks extends Module {
    public static DescriptionSetting help;
    public static ComboSetting withItem;
    public static ComboSetting usedItemRight, usedItemLeft;
    public static NumberSetting airRange;
    public static BooleanSetting placeIfOccupied;
    public static BooleanSetting right, left;

    private BlockPos placedPos;
    private World inWorld;

    public GhostBlocks(){
        super("GhostBlocks", ModuleCategory.Player);
        this.registerSetting(help = new DescriptionSetting("RMB w/ fist to place blocks"));
        this.registerSetting(withItem = new ComboSetting("When holding", new String[]{"Nothing", "Sword", "Tool", "Block"}, 0));
        this.registerSetting(airRange = new NumberSetting("Range when looking at air", 3, 0, 50, 1));
        this.registerSetting(placeIfOccupied = new BooleanSetting("Place if area is occupied" , false));

        this.registerSetting(left = new BooleanSetting("Place on left click", false));
        this.registerSetting(usedItemLeft = new ComboSetting("With block", new String[]{"Diamond", "Wool", "Logs", "Barrier", "Air"}, 4));

        this.registerSetting(right = new BooleanSetting("Place on right click", true));
        this.registerSetting(usedItemRight = new ComboSetting("With block", new String[]{"Diamond", "Wool", "Logs", "Barrier", "Air"}, 0));
    }

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public void m(MouseEvent e) {
        if(e.buttonstate && holdingYes()){
            if (e.button == 0 && left.isToggled()) {
                leftClick();
            } else if (e.button == 1 && right.isToggled()) {
                rightClick();
            }

        }
    }

    private void leftClick() {
        BlockPos blockPos = mc.objectMouseOver.getBlockPos();
        placedPos = blockPos;
        inWorld = mc.theWorld;
        Block e;
        try {
            e = mc.theWorld.getBlockState(blockPos).getBlock();

        } catch (NullPointerException bru) {
            return;
        }
        e = mc.theWorld.getBlockState(blockPos).getBlock();
        boolean air = (e instanceof BlockAir);

        int y = blockPos.getY(), x = blockPos.getX(), z = blockPos.getZ();

        if (air) {

            //mc.thePlayer.swingItem();
            EnumFacing were = mc.objectMouseOver.sideHit;
            if (were == EnumFacing.UP) {
                y = blockPos.getY() + 1;
                if (air) {
                    y += 3 - airRange.getInput();
                }
            } else if (were == EnumFacing.DOWN) {
                y = blockPos.getY() - 1;
                if (air) {
                    y -= 4 + airRange.getInput();
                }
            } else if (were == EnumFacing.EAST) {
                x = blockPos.getX() + 1;
                if (air) {
                    x += 3 - airRange.getInput();
                }
            } else if (were == EnumFacing.WEST) {
                x = blockPos.getX() - 1;
                if (air) {
                    x -= 3 + airRange.getInput();
                }
            } else if (were == EnumFacing.SOUTH) {
                z = blockPos.getZ() + 1;
                if (air) {
                    z += 3 - airRange.getInput();
                }
            } else if (were == EnumFacing.NORTH) {
                z = blockPos.getZ() - 1;
                if (air) {
                    z -= 3 + airRange.getInput();
                }
            }
        }

        IBlockState theYes = Blocks.diamond_block.getDefaultState();

        switch (usedItemLeft.getMode()) {
            case "Wool":
                theYes = Blocks.wool.getDefaultState();
                break;
            case "Logs":
                theYes = Blocks.log.getDefaultState();
                break;
            case "Barrier":
                theYes = Blocks.barrier.getDefaultState();
                break;
            case "Air":
                theYes = Blocks.air.getDefaultState();
                break;
        }

        BlockPos placeBlock = new BlockPos(x, y, z);
        //Utils.Player.sendMessageToSelf("Player in block: " + isPlayerInBlock(placeBlock));
        if (isPlayerInBlock(placeBlock) && !placeIfOccupied.isToggled())
            return;


        mc.theWorld.setBlockState(placeBlock, theYes);
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
                y = blockPos.getY() + 1;
                if(air){
                    y+= 3 - airRange.getInput();
                }
            } else if(were == EnumFacing.DOWN){
                y = blockPos.getY() - 1;
                if(air){
                    y-= 4 + airRange.getInput();
                }
            } else if(were == EnumFacing.EAST){
                x = blockPos.getX() + 1;
                if(air){
                    x+= 3 - airRange.getInput();
                }
            }else if(were == EnumFacing.WEST){
                x = blockPos.getX() - 1;
                if(air){
                    x-= 3 + airRange.getInput();
                }
            }else if(were == EnumFacing.SOUTH){
                z = blockPos.getZ() + 1;
                if(air){
                    z+= 3 - airRange.getInput();
                }
            }else if(were == EnumFacing.NORTH){
                z = blockPos.getZ() - 1;
                if(air){
                    z-= 3 + airRange.getInput();
                }
            }

            IBlockState theYes = Blocks.diamond_block.getDefaultState();

            switch (usedItemRight.getMode()){
                case "Wool":
                    theYes = Blocks.wool.getDefaultState();
                    break;
                case "Logs":
                    theYes = Blocks.log.getDefaultState();
                    break;
                case "Barrier":
                    theYes = Blocks.barrier.getDefaultState();
                    break;
                case "Air":
                    theYes = Blocks.air.getDefaultState();
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
        double bruh = mc.thePlayer.width* 0.5;
        //System.out.println("X: LowerBound: " + Math.floor(mc.thePlayer.posX-bruh) + " x: " + blockPos.getX() + " UpperBound: " + mc.thePlayer.posX+bruh + " PosX: " + mc.thePlayer.posX);
        //System.out.println("Y: LowerBound: " + Math.floor(mc.thePlayer.posY) + " Y: " + blockPos.getY() + " UpperBound: " + mc.thePlayer.posY+mc.thePlayer.height + " PosY: " + mc.thePlayer.posY);
        //System.out.println("Z: LowerBound: " + Math.floor(mc.thePlayer.posZ-bruh) + " z: " + blockPos.getZ() + " UpperBound: " + mc.thePlayer.posZ+bruh + " PosZ: " + mc.thePlayer.posZ);

        return blockPos.getX() >= Math.floor(mc.thePlayer.posX-bruh) && blockPos.getX() <= mc.thePlayer.posX+bruh && blockPos.getZ() >= Math.floor(mc.thePlayer.posZ-bruh) && blockPos.getZ() <= mc.thePlayer.posZ+bruh && blockPos.getY() >= Math.floor(mc.thePlayer.posY) && blockPos.getY() <= mc.thePlayer.posY + mc.thePlayer.height;
    }
}
