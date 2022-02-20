package keystrokesmod.keystroke;

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
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new keystrokeCommand());
    }
}
