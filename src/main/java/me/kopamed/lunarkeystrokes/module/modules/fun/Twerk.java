package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class Twerk extends Module {
    public static Slider a;
    private int tickCount;
    private boolean sneaking;

    public Twerk(){
        super("Twerk", category.fun, 0);
        this.registerSetting(a =  new Slider("Duration:", 1, 1, 100, 1));
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
