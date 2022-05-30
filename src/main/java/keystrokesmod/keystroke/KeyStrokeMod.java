package keystrokesmod.keystroke;

import keystrokesmod.client.main.Raven;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
   modid = "keystrokesmod",
   name = "KeystrokesMod",
   version = "KMV5",
   acceptedMinecraftVersions = "[1.8.9]",
   clientSideOnly = true
)
public class KeyStrokeMod {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new KeystrokeCommand());

        Raven.init();
    }
}
