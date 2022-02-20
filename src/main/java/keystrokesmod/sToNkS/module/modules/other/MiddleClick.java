package keystrokesmod.sToNkS.module.modules.other;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.module.modules.combat.AimAssist;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.event.InputEvent;

public class MiddleClick extends Module {
    public static ModuleSettingSlider action;
    public static ModuleSettingTick showHelp;
    public static ModuleDesc actionDesc;
    int prevSlot;
    public static boolean a;
    private Robot bot;
    private boolean hasClicked;
    private int pearlEvent;

    public MiddleClick() {
        super("Middleclick", category.other, 0);
        this.registerSetting(showHelp = new ModuleSettingTick("Show friend help in chat", true));
        this.registerSetting(action = new ModuleSettingSlider("Value:", 1,1, 3, 1));
        this.registerSetting(actionDesc = new ModuleDesc("Mode: PEARL_THROW"));
    }

    public void guiUpdate() {
        actionDesc.setDesc(Utils.md + actions.values()[(int)(action.getInput() -1)]);
    }

    public void onEnable() {
        try {
            this.bot = new Robot();
        } catch (AWTException var2) {
            this.disable();
        }
        hasClicked = false;
        pearlEvent = 4;
    }

    @FMLEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        if(!Utils.Player.isPlayerInGame()) return;

        if(pearlEvent < 4){
            if(pearlEvent==3) mc.thePlayer.inventory.currentItem = prevSlot;
            pearlEvent++;
        }

        if(Mouse.isButtonDown(2) && !hasClicked) {
            switch (actions.values()[(int)(action.getInput() -1)]){
                case PEARL_THROW:
                    for (int slot = 0; slot <= 8; slot++) {
                        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                        if(itemInSlot != null && itemInSlot.getItem() instanceof ItemEnderPearl) {
                            prevSlot = mc.thePlayer.inventory.currentItem;
                            mc.thePlayer.inventory.currentItem = slot;
                            this.bot.mousePress(InputEvent.BUTTON3_MASK);
                            this.bot.mouseRelease(InputEvent.BUTTON3_MASK);
                            pearlEvent = 0;
                            hasClicked = true;
                            return;
                        }
                    }
                    break;

                case ADD_FRIEND:
                    addFriend();
                    if(showHelp.isToggled()) showHelpMessage();
                    break;

                case REMOVE_FRIEND:
                    removeFriend();
                    if(showHelp.isToggled()) showHelpMessage();
                    break;

            }
            hasClicked = true;
        } else if(!Mouse.isButtonDown(2) && hasClicked) {
            hasClicked = false;
        }
    }

    private void showHelpMessage() {
        if(showHelp.isToggled()) {
            Utils.Player.sendMessageToSelf("Run 'help friends' in CommandLine to find out how to add, remove and view friends.");
        }
    }

    private void removeFriend() {
        Entity player = mc.objectMouseOver.entityHit;
        if(player == null) {
            Utils.Player.sendMessageToSelf("Please aim at a player/entity when removing them.");
        } else {
            if (AimAssist.removeFriend(player)) {
                Utils.Player.sendMessageToSelf("Successfully removed " + player.getName() + " from friends list!");
            } else {
                Utils.Player.sendMessageToSelf(player.getName() + " was not found in the friends list!");
            }
        }
    }

    private void addFriend() {
        Entity player = mc.objectMouseOver.entityHit;
        if(player == null) {
            Utils.Player.sendMessageToSelf("Please aim at a player/entity when adding them.");
        }
        else {
            AimAssist.addFriend(player);
            Utils.Player.sendMessageToSelf("Successfully added " + player.getName() + " to friends list.");
        }
    }

    public enum actions {
        PEARL_THROW,
        ADD_FRIEND,
        REMOVE_FRIEND
    }
}
