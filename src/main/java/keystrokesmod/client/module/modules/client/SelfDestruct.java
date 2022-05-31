package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("Self Destruct", ModuleCategory.client, Keyboard.KEY_BACK);
   }

   public void onEnable() {
      this.disable();

      mc.displayGuiScreen(null);

      for (Module module : Raven.moduleManager.getModules()) {
         if (module != this && module.isEnabled()) {
            module.disable();
         }
      }

      /*
         that just fully unload the event system
         so we don't need to care anymore about the state of the mod... if it has been self-destructed events won't be called
         including if they're still registered
       */
      MinecraftForge.EVENT_BUS.unregister(Raven.eventTransmitter);
   }
}
