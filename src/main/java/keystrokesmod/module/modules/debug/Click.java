package keystrokesmod.module.modules.debug;

import keystrokesmod.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;

public class Click extends Module {
    public ModuleSettingSlider hookMode;
    public ModuleDesc hookDesc;
    public static double lastEventTime;
    public static event lastEvent;
    public static hook currentHook;
    public static boolean minecraftDown;
    public Click() {
        super("Click Info", category.debug, 0);
        this.registerSetting(hookMode = new ModuleSettingSlider("Value:", 1, 1, 2, 1));
        this.registerSetting(hookDesc = new ModuleDesc("Mode: MOUSE"));
    }

    @Override
    public void onEnable() {
        lastEventTime = System.currentTimeMillis();
        lastEvent = event.buttonDown;
    }

    public void guiUpdate() {
        hookDesc.setDesc(ay.md + hook.values()[(int) (hookMode.getInput() - 1)]);
    }
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e) {
        currentHook = hook.values()[(int) (hookMode.getInput() - 1)];

        if(currentHook == hook.MOUSE) {
            if(Mouse.isButtonDown(0) && lastEvent == event.buttonUp){
                System.out.println("Nohold lasted " + (System.currentTimeMillis() - lastEventTime));
                lastEvent = event.buttonDown;
                lastEventTime = System.currentTimeMillis();
            }

            if(!Mouse.isButtonDown(0) && lastEvent == event.buttonDown) {
                System.out.println("Hold lasted " + (System.currentTimeMillis() - lastEventTime));
                lastEvent = event.buttonUp;
                lastEventTime = System.currentTimeMillis();
            }
        }

        if (currentHook == hook.MINECRAFT){
            if(minecraftDown && lastEvent == event.buttonUp){
                System.out.println("Nohold lasted " + (System.currentTimeMillis() - lastEventTime));
                lastEvent = event.buttonDown;
                lastEventTime = System.currentTimeMillis();
            }

            if(!minecraftDown && lastEvent == event.buttonDown) {
                System.out.println("Hold lasted " + (System.currentTimeMillis() - lastEventTime));
                lastEvent = event.buttonUp;
                lastEventTime = System.currentTimeMillis();
            }
        }
    }

    public static enum event {
        buttonDown,
        buttonUp;
    }

    public static enum hook {
        MOUSE,
        MINECRAFT;
    }

    public static void minecraftPressed(boolean a){
        minecraftDown = a;
    }
}
