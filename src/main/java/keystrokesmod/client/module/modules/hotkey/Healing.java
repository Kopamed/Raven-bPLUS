package keystrokesmod.client.module.modules.hotkey;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Healing extends Module {
    private final TickSetting preferSlot;
    private final SliderSetting hotbarSlotPreference;
    private final ComboSetting itemMode;
    private final HealingItems mode = HealingItems.HEAL_POT;

    public Healing() {
        super("Healing", ModuleCategory.hotkey);

        this.registerSetting(preferSlot = new TickSetting("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new SliderSetting("Prefer wich slot", 8, 1, 9, 1));
        this.registerSetting(itemMode = new ComboSetting("Mode:", mode));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            if (itemMode.getMode() == HealingItems.SOUP && isSoup(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.GAPPLE && isGapple(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.FOOD && isFood(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.ALL
                    && (isGapple(preferedSlot) || isFood(preferedSlot) || isSoup(preferedSlot))) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.HEAL_POT && (isHPot(preferedSlot))) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }

        }

        for (int slot = 0; slot <= 8; slot++) {
            if (itemMode.getMode() == HealingItems.SOUP && isSoup(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.GAPPLE && isGapple(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.FOOD && isFood(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.ALL && (isGapple(slot) || isFood(slot) || isSoup(slot))) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if (itemMode.getMode() == HealingItems.HEAL_POT && isHPot(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                System.out.println("a");
                return;
            }
        }
        this.disable();
    }

    public static boolean checkSlot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);

        return itemInSlot != null && itemInSlot.getDisplayName().equalsIgnoreCase("ladder");
    }

    public enum HealingItems {
        SOUP, GAPPLE,
        // NOTCH_APPLE,
        // HEAD,
        FOOD, HEAL_POT, ALL
    }

    public boolean isSoup(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return false;
        return itemInSlot.getItem() instanceof ItemSoup;
    }

    public boolean isGapple(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof ItemAppleGold;
    }

    public boolean isHPot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return false;

        if (itemInSlot.getItem() instanceof ItemPotion) {
            ItemPotion ip = (ItemPotion) itemInSlot.getItem();
            Utils.Player.sendMessageToSelf("" + slot);
            for (PotionEffect pe : ip.getEffects(itemInSlot)) {
                if (pe.getPotionID() == Potion.heal.id) {
                    return true;
                }
            }

        }

        return false;
    }

    public boolean isHead(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof Item;
    }

    public boolean isFood(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if (itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof ItemFood;
    }
}
