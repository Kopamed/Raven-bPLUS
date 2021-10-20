package me.kopamed.lunarkeystrokes.module.modules.player;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Method;

public class SuperAutoClicker extends Module {
    public Description credits, warning, cps;
    public Tick left, right;
    public Slider everyFps;
    public Method cpsCap;

    private int currentFPS = 0;

    public SuperAutoClicker(){
        super("SuperAutoClicker", category.player);

        this.registerSetting(credits = new Description("Huge thanks to Caterpillow"));
        this.registerSetting(warning = new Description("Ban speedruns be like"));
        this.registerSetting(left = new Tick("Left Click", false));
        this.registerSetting(cps = new Description("Warning: CPS = FPS"));
        this.registerSetting(right = new Tick("Rigth Click", true));
        this.registerSetting(everyFps = new Slider("Frame Delay: ", 1, 1, 100, 1));
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e){
        if(!Utils.Player.isPlayerInGame()) return;
        currentFPS ++;
        if(currentFPS%everyFps.getInput() == 0){
            currentFPS = 0;
        } else {
            return;
        }
        if (mc.gameSettings.keyBindUseItem.isKeyDown() && right.isToggled()) {
            try {
                cpsCap = this.mc.getClass().getDeclaredMethod("func_147121_ag");
                cpsCap.setAccessible(true);
                cpsCap.invoke(this.mc);
            } catch (Exception var3) {
                try {
                    cpsCap = this.mc.getClass().getDeclaredMethod("rightClickMouse");
                    cpsCap.setAccessible(true);
                    cpsCap.invoke(this.mc);
                } catch (Exception var5) {
                    //var3.printStackTrace();
                }
                //var3.printStackTrace();
            }
        }

        if (mc.gameSettings.keyBindAttack.isKeyDown() && left.isToggled()) {
            try {
                cpsCap = this.mc.getClass().getDeclaredMethod("func_147116_af");
                cpsCap.setAccessible(true);
                cpsCap.invoke(this.mc);
            } catch (Exception var3) {
                try {
                    cpsCap = this.mc.getClass().getDeclaredMethod("clickMouse");
                    cpsCap.setAccessible(true);
                    cpsCap.invoke(this.mc);
                } catch (Exception var4) {
                    //var3.printStackTrace();
                }
                //var3.printStackTrace();
            }
        }
    }
}
