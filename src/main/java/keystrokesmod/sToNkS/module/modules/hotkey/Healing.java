package keystrokesmod.sToNkS.module.modules.hotkey;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingSlider;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraft.item.*;

public class Healing extends Module {
    private final ModuleSettingTick preferSlot;
    private final ModuleSettingSlider hotbarSlotPreference;
    private final ModuleSettingSlider itemMode;
    private final ModuleDesc modeDesc;
    public Healing() {
        super("Healing", category.hotkey, 0);

        this.registerSetting(preferSlot = new ModuleSettingTick("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new ModuleSettingSlider("Prefer wich slot", 8, 1, 9, 1));
        this.registerSetting(itemMode = new ModuleSettingSlider("Value:", 1, 1,4, 1));
        this.registerSetting(modeDesc = new ModuleDesc("Mode: SOUP"));
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
