package me.kopamed.raven.bplus.client.feature.module.modules.player;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Method;

public class SuperAutoClicker extends Module {
    public DescriptionSetting credits, warning, cps;
    public BooleanSetting left, right;
    public NumberSetting everyFps;
    public Method cpsCap;

    private int currentFPS = 0;

    public SuperAutoClicker(){
        super("SuperAutoClicker", ModuleCategory.Player);

        this.registerSetting(credits = new DescriptionSetting("Huge thanks to Caterpillow"));
        this.registerSetting(warning = new DescriptionSetting("Ban speedruns be like"));
        this.registerSetting(left = new BooleanSetting("Left Click", false));
        this.registerSetting(cps = new DescriptionSetting("Warning: CPS = FPS"));
        this.registerSetting(right = new BooleanSetting("Rigth Click", true));
        this.registerSetting(everyFps = new NumberSetting("Frame Delay: ", 1, 1, 100, 1));
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
                cpsCap = mc.getClass().getDeclaredMethod("func_147121_ag");
                cpsCap.setAccessible(true);
                cpsCap.invoke(mc);
            } catch (Exception var3) {
                try {
                    cpsCap = mc.getClass().getDeclaredMethod("rightClickMouse");
                    cpsCap.setAccessible(true);
                    cpsCap.invoke(mc);
                } catch (Exception var5) {
                    //var3.printStackTrace();
                }
                //var3.printStackTrace();
            }
        }

        if (mc.gameSettings.keyBindAttack.isKeyDown() && left.isToggled()) {
            try {
                cpsCap = mc.getClass().getDeclaredMethod("func_147116_af");
                cpsCap.setAccessible(true);
                cpsCap.invoke(mc);
            } catch (Exception var3) {
                try {
                    cpsCap = mc.getClass().getDeclaredMethod("clickMouse");
                    cpsCap.setAccessible(true);
                    cpsCap.invoke(mc);
                } catch (Exception var4) {
                    //var3.printStackTrace();
                }
                //var3.printStackTrace();
            }
        }
    }
}
