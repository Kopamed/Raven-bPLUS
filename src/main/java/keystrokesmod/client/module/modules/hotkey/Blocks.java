package keystrokesmod.client.module.modules.hotkey;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class Blocks extends Module {
    private final TickSetting preferSlot;
    private final SliderSetting hotbarSlotPreference;

    public Blocks() {
        super("Blocks", ModuleCategory.hotkey);

        this.registerSetting(preferSlot = new TickSetting("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new SliderSetting("Prefer wich slot", 9, 1, 9, 1));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame())
            return;

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(preferedSlot);
            if (itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }
        }

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock
                    && (((ItemBlock) itemInSlot.getItem()).getBlock().isFullBlock()
                            || ((ItemBlock) itemInSlot.getItem()).getBlock().isFullCube())) {
                if (mc.thePlayer.inventory.currentItem != slot) {
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
