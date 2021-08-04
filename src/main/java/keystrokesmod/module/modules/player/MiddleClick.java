package keystrokesmod.module.modules.player;

import io.netty.util.internal.ThreadLocalRandom;
import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.modules.combat.AimAssist;
import keystrokesmod.module.modules.debug.Click;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MiddleClick extends Module {
    public static ModuleSettingSlider action, minCPS, maxCPS;
    public static ModuleSettingTick showHelp;
    public static ModuleDesc actionDesc, cpsClarification;
    public static boolean holding, clicking;
    public static double ppsDelay, holdTime, holdStart, releaseStart, releaseTime;
    int prevSlot, tickLastest, currentTick;
    public static double speedRight;
    public static double rightHoldLength;
    public static long lastClick;
    public static double leftHold;
    public static boolean a;
    public static boolean moveback, timeOut;

    public MiddleClick() {
        super("Middleclick", category.player, 0);
        this.registerSetting(showHelp = new ModuleSettingTick("Show friend help in chat", true));
        this.registerSetting(minCPS = new ModuleSettingSlider("Min CPS:", 8,1, 20, 0.5));
        this.registerSetting(maxCPS = new ModuleSettingSlider("Max CPS:", 12,1, 20, 0.5));
        this.registerSetting(cpsClarification = new ModuleDesc("CPS only applies to PEARL_THROW"));
        this.registerSetting(action = new ModuleSettingSlider("Value:", 1,1, 3, 1));
        this.registerSetting(actionDesc = new ModuleDesc("Mode: PEARL_THROW"));
        tickLastest = 15;
    }

    public void guiUpdate() {
        this.actionDesc.setDesc(ay.md + actions.values()[(int)(this.action.getInput() -1)]);
        ay.correctSliders(minCPS, maxCPS);
    }

    public void onEnable() {
        clicking = false;
        holding = false;
        timeOut = false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if(timeOut){
            if (currentTick <= tickLastest){
                currentTick++;
                return;
            }else {
                currentTick = 1;
                timeOut = false;
            }
        }

        if(moveback){
            moveback = false;
            mc.thePlayer.inventory.currentItem = prevSlot;
        }

        if(clicking){
            /*
            if (holding && System.currentTimeMillis() - holdStart > holdTime){
                holding = false;
                releaseStart = System.currentTimeMillis();
                if (holdStart + holdTime < releaseStart){
                    releaseStart = holdStart + holdTime;
                }
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                Click.minecraftPressed(false);
                clicking = false;
                mc.thePlayer.inventory.currentItem = prevSlot;
                genTimings(16, 20);
            } else if (!holding && System.currentTimeMillis() - releaseStart > releaseTime) {
                KeyBinding.setKeyBindState(1, true);
                Click.minecraftPressed(true);
                KeyBinding.onTick(1);
                clicking= true;
                mc.thePlayer.inventory.currentItem = prevSlot;
            }*/

            if (System.currentTimeMillis() - lastClick > speedRight * 1000 && a) {
                lastClick = System.currentTimeMillis();
                if (leftHold < lastClick){
                    leftHold = lastClick;
                }
                int key = mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                a = false;
                KeyBinding.onTick(key);
            } else if (System.currentTimeMillis() - leftHold > rightHoldLength * 1000 && !a) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                clicking = false;
                a = true;
                moveback = true;
            }



            return;
        }

        if(!Mouse.isButtonDown(2))
            return;


        actions middleClickAction = actions.values()[(int) this.action.getInput() - 1];

        if(middleClickAction == actions.PEARL_THROW){
            throwPearl();
        } else if(middleClickAction == actions.ADD_FRIEND) {
            addFriend();
            showHelpMessage();
            timeOut = true;
        } else if(middleClickAction == actions.REMOVE_FRIEND) {
            removeFriend();
            showHelpMessage();
            timeOut = true;
        }
    }

    private void showHelpMessage() {
        if(showHelp.isToggled()) {
            ay.sendMessageToSelf("Run 'help friends' in CommandLine to find out how to add, remove and view friends.");
        }
    }

    private void removeFriend() {
        Entity player = mc.objectMouseOver.entityHit;
        if(player == null) {
            ay.sendMessageToSelf("Please aim at a player/entity when removing them.");
        } else {
            if (AimAssist.removeFriend(player)) {
                ay.sendMessageToSelf("Successfully removed " + player.getName() + " from friends list!");
            } else {
                ay.sendMessageToSelf(player.getName() + " was not found in the friends list!");
            }
        }
    }

    private void addFriend() {
        Entity player = mc.objectMouseOver.entityHit;
        if(player == null) {
            ay.sendMessageToSelf("Please aim at a player/entity when adding them.");
        }
        else {
            AimAssist.addFriend(player);
            ay.sendMessageToSelf("Successfully added " + player.getName() + " to friends list.");
        }
    }

    private void throwPearl() {

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot != null && itemInSlot.getItem() instanceof ItemEnderPearl) {
                prevSlot = mc.thePlayer.inventory.currentItem;
                mc.thePlayer.inventory.currentItem = slot;
                lastClick = System.currentTimeMillis();
                genTimings(minCPS.getInput(), maxCPS.getInput() + 0.2);
                clicking = true;
            }
        }
    }

    public static enum actions {
        PEARL_THROW,
        ADD_FRIEND,
        REMOVE_FRIEND;
    }

    public static void rightClick(double withMinCps, double withMaxCps) {

    }

    public static void genTimings(double withMinCps, double withMaxCps) {
        speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble( withMinCps, withMaxCps);
        rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(withMinCps, withMaxCps);
    }
}
