package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
    public Chams() {
        super("Chams", ModuleCategory.render);
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof Pre) {
            if (((Pre) fe.getEvent()).entity != mc.thePlayer) {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0F, -1100000.0F);
            }
        } else if (fe.getEvent() instanceof Post) {
            if (((Post) fe.getEvent()).entity != mc.thePlayer) {
                GL11.glDisable(32823);
                GL11.glPolygonOffset(1.0F, 1100000.0F);
            }
        }
    }

}
