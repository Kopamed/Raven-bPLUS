package keystrokesmod.client.module.modules.hotkey;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class Armour extends Module {
    public static TickSetting ignoreIfAlreadyEquipped;

    public Armour() {
        super("Armour", ModuleCategory.hotkey);

        this.registerSetting(ignoreIfAlreadyEquipped = new TickSetting("Ignore if already equipped", true));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame()) {
            return;
        }

        int index = -1;
        double strength = -1;

        for (int armorType = 0; armorType < 4; armorType++) {
            index = -1;
            strength = -1;
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                    ItemArmor armorPiece = (ItemArmor) itemStack.getItem();
                    if (!Utils.Player.playerWearingArmor().contains(armorPiece.armorType)
                            && armorPiece.armorType == armorType && ignoreIfAlreadyEquipped.isToggled()) {
                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }

                    } else if (Utils.Player.playerWearingArmor().contains(armorPiece.armorType)
                            && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {
                        ItemArmor playerArmor;
                        if (armorType == 0) {
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(3).getItem();
                        } else if (armorType == 1) {
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(2).getItem();
                        } else if (armorType == 2) {
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(1).getItem();
                        } else if (armorType == 3) {
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(0).getItem();
                        } else {
                            continue;
                        }

                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength
                                && armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > playerArmor
                                        .getArmorMaterial().getDamageReductionAmount(armorType)) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    } else if (!Utils.Player.playerWearingArmor().contains(armorPiece.armorType)
                            && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {

                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    }

                }
            }
            if (index > -1 || strength > -1) {
                mc.thePlayer.inventory.currentItem = index;
                this.disable();
                this.onDisable();
                return;
            }
        }
        this.onDisable();
        this.disable();
    }
}
