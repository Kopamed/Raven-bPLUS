package keystrokesmod.client.module.modules.hotkey;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.*;

public class Healing extends Module {
    private final TickSetting preferSlot;
    private final SliderSetting hotbarSlotPreference;
    private final SliderSetting itemMode;
    private final DescriptionSetting modeDesc;
    public Healing() {
        super("Healing", ModuleCategory.hotkey);

        this.registerSetting(preferSlot = new TickSetting("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new SliderSetting("Prefer wich slot", 8, 1, 9, 1));
        this.registerSetting(itemMode = new SliderSetting("Value:", 1, 1,4, 1));
        this.registerSetting(modeDesc = new DescriptionSetting("Mode: SOUP"));
    }

    public void guiUpdate() {
        modeDesc.setDesc(Utils.md + HealingItems.values()[(int) itemMode.getInput() - 1]);
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;


            if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.SOUP && isSoup(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.GAPPLE && isGapple(preferedSlot)){
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.FOOD && isFood(preferedSlot)){
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.ALL && (isGapple(preferedSlot) || isFood(preferedSlot) || isSoup(preferedSlot))){
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }

        }

        for (int slot = 0; slot <= 8; slot++) {
            if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.SOUP && isSoup(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.GAPPLE && isGapple(slot)){
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.FOOD && isFood(slot)){
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            } else if(HealingItems.values()[(int) itemMode.getInput() - 1] == HealingItems.ALL && (isGapple(slot) || isFood(slot) || isSoup(slot))){
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
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
        SOUP,
        GAPPLE,
        //NOTCH_APPLE,
        //HEAD,
        FOOD,
        ALL
    }

    public boolean isSoup(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;
        return itemInSlot.getItem() instanceof ItemSoup;
    }

    public boolean isGapple(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof ItemAppleGold;
    }

    public boolean isHead(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof Item;
    }

    public boolean isFood(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        return itemInSlot.getItem() instanceof ItemFood;
    }
}
