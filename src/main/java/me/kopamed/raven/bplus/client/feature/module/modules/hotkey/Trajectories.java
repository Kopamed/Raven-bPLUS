package me.kopamed.raven.bplus.client.feature.module.modules.hotkey;

import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import net.minecraft.item.*;

public class Trajectories extends Module {
    private final BooleanSetting preferSlot;
    private final NumberSetting hotbarSlotPreference;
    public Trajectories() {
        super("Trajectories", ModuleCategory.Hotkeys, 0);

        this.registerSetting(preferSlot = new BooleanSetting("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new NumberSetting("Prefer wich slot", 5, 1, 9, 1));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            if(checkSlot(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }
        }

        for (int slot = 0; slot <= 8; slot++) {
            if(checkSlot(slot)) {
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

    public static boolean checkSlot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);

        return itemInSlot != null && (itemInSlot.getItem() instanceof ItemSnowball || itemInSlot.getItem() instanceof ItemEgg || itemInSlot.getItem() instanceof ItemFishingRod);
    }
}
