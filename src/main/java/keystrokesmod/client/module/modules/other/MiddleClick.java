package keystrokesmod.client.module.modules.other;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.AimAssist;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.event.InputEvent;

import static keystrokesmod.client.module.modules.other.MiddleClick.Action.*;

public class MiddleClick extends Module {
    public static ComboSetting actionSetting;
    public static TickSetting showHelp;
    int prevSlot;
    public static boolean a;
    private Robot bot;
    private boolean hasClicked;
    private int pearlEvent;

    public MiddleClick() {
        super("Middleclick", ModuleCategory.other);
        this.registerSetting(showHelp = new TickSetting("Show friend help in chat", true));
        this.registerSetting(actionSetting = new ComboSetting("On click", ThrowPearl));
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

    @Subscribe
    public void onTick(TickEvent e) {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (pearlEvent < 4) {
            if (pearlEvent == 3)
                mc.thePlayer.inventory.currentItem = prevSlot;
            pearlEvent++;
        }

        if (Mouse.isButtonDown(2) && !hasClicked) {
            if (ThrowPearl.equals(actionSetting.getMode())) {
                for (int slot = 0; slot <= 8; slot++) {
                    ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                    if (itemInSlot != null && itemInSlot.getItem() instanceof ItemEnderPearl) {
                        prevSlot = mc.thePlayer.inventory.currentItem;
                        mc.thePlayer.inventory.currentItem = slot;
                        this.bot.mousePress(InputEvent.BUTTON3_MASK);
                        this.bot.mouseRelease(InputEvent.BUTTON3_MASK);
                        pearlEvent = 0;
                        hasClicked = true;
                        return;
                    }
                }
            } else if (AddFriend.equals(actionSetting.getMode())) {
                addFriend();
                if (showHelp.isToggled())
                    showHelpMessage();
            } else if (RemoveFriend.equals(actionSetting.getMode())) {
                removeFriend();
                if (showHelp.isToggled())
                    showHelpMessage();
            }
            hasClicked = true;
        } else if (!Mouse.isButtonDown(2) && hasClicked) {
            hasClicked = false;
        }
    }

    private void showHelpMessage() {
        if (showHelp.isToggled()) {
            Utils.Player.sendMessageToSelf(
                    "Run 'help friends' in CommandLine to find out how to add, remove and view friends.");
        }
    }

    private void removeFriend() {
        Entity player = mc.objectMouseOver.entityHit;
        if (player == null) {
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
        if (player == null) {
            Utils.Player.sendMessageToSelf("Please aim at a player/entity when adding them.");
        } else {
            AimAssist.addFriend(player);
            Utils.Player.sendMessageToSelf("Successfully added " + player.getName() + " to friends list.");
        }
    }

    public enum Action {
        ThrowPearl, AddFriend, RemoveFriend
    }
}
