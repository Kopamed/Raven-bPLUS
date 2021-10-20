package me.kopamed.lunarkeystrokes.module.modules.hotkey;

import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.*;

import java.util.ArrayList;

public class Pearl extends Module {
    private final Tick preferSlot;
    private final Slider hotbarSlotPreference;
    public static ArrayList<KeyBinding> changedKeybinds = new ArrayList<KeyBinding>();
    public Pearl() {
        super("Pearl", category.hotkey, 0);

        this.registerSetting(preferSlot = new Tick("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new Slider("Prefer wich slot", 6, 1, 9, 1));
    }

    public static boolean checkSlot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);

        return itemInSlot != null && itemInSlot.getDisplayName().equalsIgnoreCase("ender pearl");
    }

    @Override
    public void onEnable(){
        if (!Utils.Player.isPlayerInGame()){
            return;
        }

        if (preferSlot.isToggled()) {
            int preferedSlot = (int) hotbarSlotPreference.getInput() - 1;

            if(checkSlot(preferedSlot)) {
                mc.thePlayer.inventory.currentItem = preferedSlot;
                this.disable();
                return;
            }
        }

        for (int slot = 0; slot <= 8; slot++) {
            if (checkSlot(slot)) {
                mc.thePlayer.inventory.currentItem = slot;
                this.disable();
                return;
            }
        }
        this.disable();
    }
}
