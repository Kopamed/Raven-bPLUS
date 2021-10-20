package me.kopamed.lunarkeystrokes.module.modules.hotkey;

import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class Armour extends Module {
    public static Tick ignoreIfAlreadyEquipped;
    public Armour() {
        super("Armour", category.hotkey, 0);

        this.registerSetting(ignoreIfAlreadyEquipped = new Tick("Ignore if already equipped", true));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame()) {
            return;
        }


        int index =-1;
        double strength = -1;

        for(int armorType = 0; armorType < 4; armorType++) {
            ////////System.out.println("Looking for " + armorType);
            index = -1;
            strength = -1;
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                    ItemArmor armorPiece = (ItemArmor) itemStack.getItem();

                    ////////System.out.println(ay.playerWearingArmor());
                    if(!Utils.Player.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && ignoreIfAlreadyEquipped.isToggled()){
                        ////////System.out.println("match found of " + armorPiece.armorType + " in slotr " + slot);
                        ////////System.out.println(strength + " "+ armorPiece.getArmorMaterial().getDamageReductionAmount(armorType));
                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }

                    } else if (Utils.Player.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {
                        ////////System.out.println("found betta");
                        ItemArmor playerArmor;
                        if(armorType == 0){
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(3).getItem();
                        } else if(armorType == 1){
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(2).getItem();
                        } else if(armorType == 2){
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(1).getItem();
                        } else if(armorType == 3){
                            playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(0).getItem();
                        } else {
                            ////////System.out.println("Shit");
                            continue;
                        }

                        if(armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength && armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > playerArmor.getArmorMaterial().getDamageReductionAmount(armorType)){
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    } else if(!Utils.Player.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {
                        ////////System.out.println("playa aint have amo and is off");

                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    }


                }
            }
            if(index > -1 || strength > -1) {
                ////////System.out.println("Hotkeying to " + index);
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
