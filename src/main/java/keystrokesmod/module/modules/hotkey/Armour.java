package keystrokesmod.module.modules.hotkey;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;

public class Armour extends Module {
    public static ModuleSettingTick ignoreIfAlreadyEquipped;
    public Armour() {
        super("Armour", category.hotkey, 0);

        this.registerSetting(ignoreIfAlreadyEquipped = new ModuleSettingTick("Ignore if already equipped", true));
    }

    @Override
    public void onEnable() {
        if (!ay.isPlayerInGame()) {
            return;
        }


        int index =-1;
        double strength = -1;

        for(int armorType = 0; armorType < 4; armorType++) {
            //System.out.println("Looking for " + armorType);
            index = -1;
            strength = -1;
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                    ItemArmor armorPiece = (ItemArmor) itemStack.getItem();

                    //System.out.println(ay.playerWearingArmor());
                    if(!ay.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && ignoreIfAlreadyEquipped.isToggled()){
                        //System.out.println("match found of " + armorPiece.armorType + " in slotr " + slot);
                        //System.out.println(strength + " "+ armorPiece.getArmorMaterial().getDamageReductionAmount(armorType));
                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }

                    } else if (ay.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {
                        //System.out.println("found betta");
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
                            //System.out.println("Shit");
                            continue;
                        }

                        if(armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength && armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > playerArmor.getArmorMaterial().getDamageReductionAmount(armorType)){
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    } else if(!ay.playerWearingArmor().contains(armorPiece.armorType) && armorPiece.armorType == armorType && !ignoreIfAlreadyEquipped.isToggled()) {
                        //System.out.println("playa aint have amo and is off");

                        if (armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) > strength) {
                            strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                            index = slot;
                        }
                    }










                /*if(armorPiece != null){
                    //System.out.println("not null");
                    if(armorPiece.armorType == armorType){
                        //System.out.println("armour type " + armorPiece.getItemStackDisplayName(itemStack));

                        if(mc.thePlayer.getCurrentArmor(armorPiece.armorType) != null && ignoreIfAlreadyEquipped.isToggled()){
                            //System.out.println("player alr wearing");
                        } else {
                            try {
                                //System.out.println("main");
                                if(ay.playerWearingArmor().contains(armorType)){
                                    ItemArmor playerArmor = (ItemArmor) mc.thePlayer.getCurrentArmor(armorType).getItem();
                                    if(playerArmor.getArmorMaterial().getDamageReductionAmount(armorType) < armorPiece.getArmorMaterial().getDamageReductionAmount(armorType) || strength < armorPiece.getArmorMaterial().getDamageReductionAmount(armorType)) {
                                        //System.out.println("added index v1");
                                        strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                                        index = slot;
                                    }
                                }
                                else {
                                    if(strength < armorPiece.getArmorMaterial().getDamageReductionAmount(armorType)) {
                                        //System.out.println("added index v2");
                                        strength = armorPiece.getArmorMaterial().getDamageReductionAmount(armorType);
                                        index = slot;
                                    }
                                }
                            } catch (Exception e){
                                //System.out.println("eXCEPTION FUCK");
                                e.printStackTrace();
                            }
                        }
                    }
                }*/
                }
            }
            if(index > -1 || strength > -1) {
                //System.out.println("Hotkeying to " + index);
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
