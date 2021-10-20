package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class FPSSpoofer extends Module {
    public static Description desc;
    public static RangeSlider fps;
    public static Field r = null;
    public static int ticksPassed;

    public FPSSpoofer() {
        super("FPSSpoof", category.fun, 0);
        this.registerSetting(desc = new Description("Spoofs your fps"));
        this.registerSetting(fps = new RangeSlider("FPS", 99860, 100000, 0, 100000, 100));

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
                        r.set(mc, ThreadLocalRandom.current().nextInt((int)fps.getInputMin(), (int)fps.getInputMax()+1));
                    } catch (IllegalAccessException var4) {}
                }
                ticksPassed = 0;
           // }
            ticksPassed++;
        }
    }
}
