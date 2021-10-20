package me.kopamed.lunarkeystrokes.module.modules.combat;

import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoWeapon extends Module {
    public static Tick onlyWhenHoldingDown;
    public static Tick goBackToPrevSlot;
    private boolean onWeapon;
    private int prevSlot;

    public AutoWeapon(){
        super("AutoWeapon", category.combat, 0);
        this.registerSetting(onlyWhenHoldingDown = new Tick("Only when holding lmb", true));
        this.registerSetting(goBackToPrevSlot = new Tick("Revert to old slot", true));
    }

    @SubscribeEvent
    public void datsDaSoundOfDaPolis(TickEvent.RenderTickEvent ev){
        if(!Utils.Player.isPlayerInGame() || mc.currentScreen != null) return;


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

                int maxDamageSlot = Utils.Player.getMaxDamageSlot();

                if(maxDamageSlot > 0 && Utils.Player.getSlotDamage(maxDamageSlot) > Utils.Player.getSlotDamage(mc.thePlayer.inventory.currentItem)){
                    mc.thePlayer.inventory.currentItem = maxDamageSlot;
                }
            }
        }
    }
}
