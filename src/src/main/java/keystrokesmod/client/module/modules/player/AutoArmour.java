package keystrokesmod.client.module.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmour extends Module {

    private final DoubleSliderSetting firstDelay;
    private final DoubleSliderSetting delay;
    private List<Slot> sortedSlots = new ArrayList<>();
    private boolean inInv;
    private final CoolDown delayTimer = new CoolDown(0);

    public AutoArmour() {
        super("AutoArmour", ModuleCategory.player);
        this.registerSetting(firstDelay = new DoubleSliderSetting("Open delay", 250, 450, 0, 1000, 1));
        this.registerSetting(delay = new DoubleSliderSetting("Delay", 150, 250, 0, 1000, 1));
    }

    @Subscribe
    public void onRender2D(Render2DEvent e) {
        if (Utils.Player.isPlayerInInventory()) {
            if (!inInv) {
                delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(firstDelay.getInputMin(),
                        firstDelay.getInputMax() + 0.01));
                delayTimer.start();
                generatePath((ContainerPlayer) mc.thePlayer.openContainer);
                inInv = true;
            }
            if (!sortedSlots.isEmpty()) {
                if (delayTimer.hasFinished()) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, sortedSlots.get(0).s, 0, 1,
                            mc.thePlayer);
                    delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(delay.getInputMin(),
                            delay.getInputMax() + 0.01));
                    delayTimer.start();
                    sortedSlots.remove(0);
                }
            }
        } else {
            inInv = false;
        }
    }

    public void generatePath(ContainerPlayer inv) {
        ArrayList<Slot> slots = new ArrayList<>();
        Slot[] bestArmour = { new Slot(-1), new Slot(-1), new Slot(-1), new Slot(-1) };
        for (int i = 0; i < inv.getInventory().size(); i++) {
            if (inv.getInventory().get(i) != null && inv.getInventory().get(i).getItem() instanceof ItemArmor
                    && !(i > 4 && i < 9)) {
                Slot ia = new Slot(i);
                if (bestArmour[ia.at].v < ia.v)
                    bestArmour[ia.at] = ia;
            }
        }
        for (int i = 0; i < 4; i++) {
            try {
                Slot s = new Slot(i + 5);
                if (s.v < bestArmour[i].v) {
                    slots.add(s);
                    slots.add(bestArmour[i]);
                }
            } catch (NullPointerException e) {
                slots.add(bestArmour[i]);
            } catch (ClassCastException e) {
                slots.add(new Slot(i + 5));
                slots.add(bestArmour[i]);
            }
        }
        this.sortedSlots = slots;
    }

    public static class Slot {
        final int x;
        final int y;
        final int s;
        int at;
        float v;

        public Slot(int s) {
            this.s = s;
            this.x = (s + 1) % 10;
            this.y = s / 9;
            setValues();
        }

        public void setValues() {
            if (s < 0)
                return;

            ItemStack itemStack = mc.thePlayer.openContainer.getSlot(s).getStack();
            Item is = itemStack.getItem();
            if (!(is instanceof ItemArmor))
                return;

            ItemArmor as = (ItemArmor) is;
            float pl;
            try {
               pl = EnchantmentHelper.getEnchantments(itemStack).get(0); 
            } catch(Exception e) {
                pl = 0;
            }
            v = as.damageReduceAmount + (float) (pl * 0.6);
            at = as.armorType;
        }

    }
}
