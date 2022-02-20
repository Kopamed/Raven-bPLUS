package keystrokesmod.sToNkS.module.modules.debug;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class MouseClick extends Module {
    public MouseClick() {
        super("MouseClick", category.debug, 0);
    }

    @FMLEvent
    public void onTick(TickEvent.PlayerTickEvent e) {
        for(int i = 0; i < 16; i++) {
            if(Mouse.isButtonDown(i))
                System.out.println(i);
        }
    }
}
