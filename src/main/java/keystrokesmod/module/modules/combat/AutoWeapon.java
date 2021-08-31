package keystrokesmod.module.modules.combat;

import keystrokesmod.utils.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoWeapon extends Module {
    public static ModuleSettingTick onlyWhenHoldingDown;
    public static ModuleSettingTick goBackToPrevSlot;
    private boolean onWeapon;
    private int prevSlot;

    public AutoWeapon(){
        super("AutoWeapon", category.combat, 0);
        this.registerSetting(onlyWhenHoldingDown = new ModuleSettingTick("Only when holding lmb", true));
        this.registerSetting(goBackToPrevSlot = new ModuleSettingTick("Revert to old slot", true));
    }

    @SubscribeEvent
    public void datsDaSoundOfDaPolis(TickEvent.RenderTickEvent ev){
        if(!ay.isPlayerInGame() || mc.currentScreen != null) return;


        if(mc.objectMouseOver==null || mc.objectMouseOver.entityHit==null || (onlyWhenHoldingDown.isToggled() && !Mouse.isButtonDown(0))){
            if(onWeapon){
                onWeapon = false;
                if(goBackToPrevSlot.isToggled()){
                    mc.thePlayer.inventory.currentItem = prevSlot;
                }
            }
        } else{
            Entity target = mc.objectMouseOver.entityHit;
            if(onlyWhenHoldingDown.isToggled()){
                if(!Mouse.isButtonDown(0)) return;
            }
            if(!onWeapon){
                prevSlot = mc.thePlayer.inventory.currentItem;
                onWeapon = true;

                int maxDamageSlot = ay.getMaxDamageSlot();

                if(maxDamageSlot > 0 && ay.getSlotDamage(maxDamageSlot) > ay.getSlotDamage(mc.thePlayer.inventory.currentItem)){
                    mc.thePlayer.inventory.currentItem = maxDamageSlot;
                }
            }
        }
    }
}
