package keystrokesmod.module.modules.hotkey;

import com.sun.jna.platform.mac.Carbon;
import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.*;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.nio.file.FileSystem;
import java.util.ArrayList;

public class Pearl extends Module {
    private ModuleSettingTick preferSlot;
    private ModuleSettingSlider hotbarSlotPreference;
    public static ArrayList<KeyBinding> changedKeybinds = new ArrayList<KeyBinding>();
    public Pearl() {
        super("Pearl", category.hotkey, 0);

        this.registerSetting(preferSlot = new ModuleSettingTick("Prefer a slot", false));
        this.registerSetting(hotbarSlotPreference = new ModuleSettingSlider("Prefer wich slot", 6, 1, 9, 1));
    }

    public static boolean checkSlot(int slot) {
        ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);

        if(itemInSlot != null && itemInSlot.getDisplayName().equalsIgnoreCase("ender pearl")) {
            return true;
        }
        return false;
    }

    @Override
    public void onEnable(){
        if (!ay.isPlayerInGame()){
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
