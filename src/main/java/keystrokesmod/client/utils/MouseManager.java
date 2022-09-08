package keystrokesmod.client.utils;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.world.AntiBot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MouseManager {
    private static final List<Long> leftClicks = new ArrayList<>();
    private static final List<Long> rightClicks = new ArrayList<>();
    public static long leftClickTimer;
    public static long rightClickTimer;

    @SubscribeEvent
    public void onMouseUpdate(MouseEvent mouse) {
        if (mouse.buttonstate) {
            if (mouse.button == 0) {
                addLeftClick();
                if (Raven.debugger && Minecraft.getMinecraft().objectMouseOver != null) {
                    Entity en = Minecraft.getMinecraft().objectMouseOver.entityHit;
                    if (en == null) {
                        return;
                    }

                    Utils.Player.sendMessageToSelf("&7&m-------------------------");
                    Utils.Player.sendMessageToSelf("n: " + en.getName());
                    Utils.Player.sendMessageToSelf("rn: " + en.getName().replace("§", "%"));
                    Utils.Player.sendMessageToSelf("d: " + en.getDisplayName().getUnformattedText());
                    Utils.Player.sendMessageToSelf("rd: " + en.getDisplayName().getUnformattedText().replace("§", "%"));
                    Utils.Player.sendMessageToSelf("b?: " + AntiBot.bot(en));
                }
            } else if (mouse.button == 1) {
                addRightClick();
            }

        }
    }

    public static void addLeftClick() {
        leftClicks.add(leftClickTimer = System.currentTimeMillis());
    }

    public static void addRightClick() {
        rightClicks.add(rightClickTimer = System.currentTimeMillis());
    }

    // prev f
    public static int getLeftClickCounter() {
        if (!Utils.Player.isPlayerInGame())
            return leftClicks.size();
        for (Long lon : leftClicks) {
            if (lon < System.currentTimeMillis() - 1000L) {
                leftClicks.remove(lon);
                break;
            }
        }
        return leftClicks.size();
    }

    // prev i
    public static int getRightClickCounter() {
        if (!Utils.Player.isPlayerInGame())
            return leftClicks.size();
        for (Long lon : rightClicks) {
            if (lon < System.currentTimeMillis() - 1000L) {
                rightClicks.remove(lon);
                break;
            }
        }
        return rightClicks.size();
    }
}
