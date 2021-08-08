package keystrokesmod.module.modules.hotkey;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.item.*;

public class Healing extends Module {
    private ModuleSettingTick preferSlot;
    private ModuleSettingSlider hotbarSlotPreference , itemMode;
    private ModuleDesc modeDesc;
    public Healing() {
        super("Healing", category.hotkey, 0);

        this.registerSetting(preferSlot = new ModuleSettingTick("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new ModuleSettingSlider("Prefer wich slot", 8, 1, 9, 1));
        this.registerSetting(itemMode = new ModuleSettingSlider("Value:", 1, 1,4, 1));
        this.registerSetting(modeDesc = new ModuleDesc("Mode: SOUP"));
    }

    public void guiUpdate() {
        modeDesc.setDesc(ay.md + HealingItems.values()[(int) itemMode.getInput() - 1]);
    }

    @Override
    public void onEnable() {
        if (!ay.isPlayerInGame())
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

        if(itemInSlot != null && itemInSlot.getDisplayName().equalsIgnoreCase("ladder")) {
            return true;
        }
        return false;
    }

    public static enum HealingItems {
        SOUP,
        GAPPLE,
        //NOTCH_APPLE,
        //HEAD,
        FOOD,
        ALL;
    }

    public boolean isSoup(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;
        if(itemInSlot.getItem() instanceof ItemSoup) {
            return true;
        }
        return false;
    }

    public boolean isGapple(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        if(itemInSlot.getItem() instanceof ItemAppleGold) {
            return true;
        }
        return false;
    }

    public boolean isHead(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        if(itemInSlot.getItem() instanceof Item) {
            return true;
        }
        return false;
    }

    public boolean isFood(int slot){
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
        if(itemInSlot == null)
            return false;

        if(itemInSlot.getItem() instanceof ItemFood) {
            return true;
        }
        return false;
    }
}
