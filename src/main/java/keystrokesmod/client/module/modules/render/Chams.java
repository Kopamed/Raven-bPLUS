package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.module.Module;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
   public Chams() {
      super("Chams", ModuleCategory.render);
   }

   @SubscribeEvent
   public void r1(Pre e) {
      if (e.entity != mc.thePlayer) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1100000.0F);
      }
   }

   @SubscribeEvent
   public void r2(Post e) {
      if (e.entity != mc.thePlayer) {
         GL11.glDisable(32823);
         GL11.glPolygonOffset(1.0F, 1100000.0F);
      }
   }
}
