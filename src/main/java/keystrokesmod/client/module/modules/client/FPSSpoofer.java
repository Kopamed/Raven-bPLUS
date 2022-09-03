package keystrokesmod.client.module.modules.client;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class FPSSpoofer extends Module {
    public static DescriptionSetting desc;
    public static DoubleSliderSetting fps;

    public int ticksPassed;

    private final Field fpsField;

    public FPSSpoofer() {
        super("FPSSpoof", ModuleCategory.other);
        this.registerSetting(desc = new DescriptionSetting("Spoofs your fps"));
        this.registerSetting(fps = new DoubleSliderSetting("FPS", 99860, 100000, 0, 100000, 100));

        fpsField = ReflectionHelper.findField(Minecraft.class, "field_71420_M", "fpsCounter");
        fpsField.setAccessible(true);
    }

    @Override
    public boolean canBeEnabled() {
        return fpsField != null;
    }

    public void onEnable() {
        ticksPassed = 0;
    }

    @Subscribe
    public void onGameLoop(GameLoopEvent e) {
        try {
            int fpsN = ThreadLocalRandom.current().nextInt((int) fps.getInputMin(), (int) fps.getInputMax() + 1);
            fpsField.set(mc, fpsN);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            Utils.Java.throwException(new RuntimeException("Could not access FPS field, THIS SHOULD NOT HAPPEN"));
            this.disable();
        }
    }

}
