package keystrokesmod.client.module.modules.client;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.ChatHelper;
import keystrokesmod.client.utils.DebugInfoRenderer;
import keystrokesmod.client.utils.MouseManager;
import keystrokesmod.keystroke.KeyStrokeRenderer;
import net.minecraftforge.common.MinecraftForge;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("Self Destruct", ModuleCategory.client);
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

      // dude your event system doesnt even work bruh
      MinecraftForge.EVENT_BUS.unregister(new Raven());
      MinecraftForge.EVENT_BUS.unregister(new DebugInfoRenderer());
      MinecraftForge.EVENT_BUS.unregister(new MouseManager());
      MinecraftForge.EVENT_BUS.unregister(new KeyStrokeRenderer());
      MinecraftForge.EVENT_BUS.unregister(new ChatHelper());
   }
}
