package keystrokesmod.module.modules.render;

import keystrokesmod.utils.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class FPSSpoofer extends Module {
    public static ModuleDesc desc;
    public static ModuleSettingSlider min, max;
    public static Field r = null;
    public static int ticksPassed;

    public FPSSpoofer() {
        super("FPSSpoof", category.render, 0);
        this.registerSetting(desc = new ModuleDesc("Spoofs your fps"));
        this.registerSetting(min =  new ModuleSettingSlider("Min FPS:", 10000, 0, 100000, 100));
        this.registerSetting(max =  new ModuleSettingSlider("Max FPS:", 14000, 0, 100000, 100));

        try {
            r = mc.getClass().getDeclaredField("field_71420_M");
        } catch (Exception var4) {
            try {
                r = mc.getClass().getDeclaredField("fpsCounter");
            } catch (Exception var3) {
            }
        }

        if (r != null) {
            r.setAccessible(true);
        }
    }

    public void guiUpdate() {
        ay.correctSliders(min, max);
    }

    public void onEnable(){
        if(r == null){
            this.disable();
        }
        ticksPassed= 0;
    }

    @SubscribeEvent
    public void Tick(TickEvent.ClientTickEvent e){
        if(e.phase == TickEvent.Phase.START){
            //if(ticksPassed % 20 == 0) {
                guiUpdate();
                if(r != null) {
                    try {
                        r.set(mc, ThreadLocalRandom.current().nextInt((int)min.getInput(), (int)max.getInput()+1));
                    } catch (IllegalAccessException var4) {}
                }
                ticksPassed = 0;
           // }
            ticksPassed++;
        }
    }
}
