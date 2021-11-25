package me.kopamed.raven.bplus.client.feature.module.modules.fun;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class Twerk extends Module {
    public static NumberSetting a;
    private int tickCount;
    private boolean sneaking;

    public Twerk(){
        super("Twerk", ModuleCategory.Misc, 0);
        this.registerSetting(a =  new NumberSetting("Duration:", 1, 1, 100, 1));
    }

    public void onEnable(){
        tickCount = 0;
    }

    public void update(){
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;


        tickCount++;
        if(tickCount >= a.getInput()+1){
            tickCount = 0;
        } else {
            return;
        }


        if(sneaking){
            setShift(false);
            sneaking = false;
        } else {
            setShift(true);
            sneaking = true;
        }
    }

    private void setShift(boolean sh) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }
}
