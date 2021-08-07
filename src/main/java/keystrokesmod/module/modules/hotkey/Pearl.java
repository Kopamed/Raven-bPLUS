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
        if (!ay.isPlayerInGame())
            return;

        for (int o = 0; o < mc.gameSettings.keyBindsHotbar.length; o++){
            if(mc.gameSettings.keyBindsHotbar[o].getKeyCode()== this.getKeycode()) {
                System.out.println(o + " -  - - - - - " + mc.gameSettings.keyBindsHotbar[o].getKeyCode());
                mc.gameSettings.keyBindsHotbar[o].setKeyCode(0);
                changedKeybinds.add(mc.gameSettings.keyBindsHotbar[o]);
            }
            else {
                System.out.println(o + " - " + mc.gameSettings.keyBindsHotbar[o].getKeyCode());
            }
        }



    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.ClientTickEvent ev) {
        int curSlot = mc.thePlayer.inventory.currentItem;
        curSlot = mc.thePlayer.inventory.player.inventory.currentItem;
        for (int o = 0; o < mc.gameSettings.keyBindsHotbar.length; o++){
            if(mc.gameSettings.keyBindsHotbar[o].getKeyCode()== this.getKeycode()) {
                //curSlot = o;

            }
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
            if(checkSlot(slot)) {
                if(curSlot != slot){
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
