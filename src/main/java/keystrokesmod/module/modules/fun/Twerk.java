package keystrokesmod.module.modules.fun;

import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.utils.ay;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Twerk extends Module {
    public static ModuleSettingSlider a;
    private int tickCount;
    private boolean sneaking;

    public Twerk(){
        super("Twerk", category.fun, 0);
        this.registerSetting(a =  new ModuleSettingSlider("Duration:", 1, 1, 100, 1));
    }

    public void onEnable(){
        tickCount = 0;
    }

    public void update(){
        if (!ay.isPlayerInGame() || mc.currentScreen != null)
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
