package keystrokesmod.module.modules.hotkey;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class Blocks extends Module {
    private ModuleSettingTick preferSlot;
    private ModuleSettingSlider hotbarSlotPreference;
    public Blocks() {
        super("Blocks", category.hotkey, 0);

        this.registerSetting(preferSlot = new ModuleSettingTick("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new ModuleSettingSlider("Prefer wich slot", 9, 1, 9, 1));
    }

    @Override
    public void onEnable() {
        if (!ay.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(preferedSlot);
            if(itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }
        }

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock && (((ItemBlock) itemInSlot.getItem()).getBlock().isFullBlock() || ((ItemBlock) itemInSlot.getItem()).getBlock().isFullCube())) {
                if(mc.thePlayer.inventory.currentItem != slot){
                    mc.thePlayer.inventory.currentItem = slot;
                } else {
                    return;
                }
                this.disable();
                return;
            }
        }
        this.disable();
    }
}
