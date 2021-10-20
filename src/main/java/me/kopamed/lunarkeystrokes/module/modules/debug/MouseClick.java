package me.kopamed.lunarkeystrokes.module.modules.debug;

import me.kopamed.lunarkeystrokes.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class MouseClick extends Module {
    public MouseClick() {
        super("MouseClick", category.debug, 0);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        for(int i = 0; i < 16; i++) {
            if(Mouse.isButtonDown(i))
                System.out.println(i);
        }
    }
}
