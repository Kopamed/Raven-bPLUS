package keystrokesmod.module.modules.hotkey;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.*;

import java.util.Collections;

public class Weapon extends Module {
    public Weapon() {
        super("Weapon", category.hotkey, 0);
    }

    @Override
    public void onEnable() {
        if (!ay.isPlayerInGame())
            return;

        int index = -1;
        double damage = -1;

        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if(itemInSlot == null)
                continue;
            for (AttributeModifier mooommHelp :itemInSlot.getAttributeModifiers().values()){
                if(mooommHelp.getAmount() > damage) {
                    damage = mooommHelp.getAmount();
                    index = slot;
                }
            }


        }
        if(index > -1 && damage > -1) {
            if (mc.thePlayer.inventory.currentItem != index) {
                ay.hotkeyToSlot(index);
            }
        }
        this.disable();
    }
}
