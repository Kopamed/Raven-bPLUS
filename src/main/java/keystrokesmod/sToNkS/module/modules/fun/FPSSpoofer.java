package keystrokesmod.sToNkS.module.modules.fun;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingDoubleSlider;
import net.minecraft.client.Minecraft;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class FPSSpoofer extends Module {
    public static ModuleDesc desc;
    public static ModuleSettingDoubleSlider fps;

    public int ticksPassed;

    private final Field fpsField;

    public FPSSpoofer() {
        super("FPSSpoof", category.fun, 0);
        this.registerSetting(desc = new ModuleDesc("Spoofs your fps"));
        this.registerSetting(fps = new ModuleSettingDoubleSlider("FPS", 99860, 100000, 0, 100000, 100));

        fpsField = ReflectionHelper.findField(Minecraft.class, "field_71420_M", "fpsCounter");
        fpsField.setAccessible(true);
    }

    @Override
    public boolean canBeEnabled() {
        return fpsField != null;
    }

    public void onEnable(){
        ticksPassed = 0;
    }

    @FMLEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(event.phase == TickEvent.Phase.START){
            //if(ticksPassed % 20 == 0) {
                guiUpdate();

                try {
                    int fpsN = ThreadLocalRandom.current().nextInt((int)fps.getInputMin(), (int)fps.getInputMax() + 1);
                    fpsField.set(mc, fpsN);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    this.disable();
                }
                ticksPassed = 0;
           // }
            ticksPassed++;
        }
    }
}
