package keystrokesmod.module.modules.other;

import keystrokesmod.DiscordRPCManager;
import keystrokesmod.module.Module;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    private final DiscordRPCManager rpc = new DiscordRPCManager();

    public DiscordRPCModule() {
        super("DiscordRPC", category.other);
    }

    public void onEnable() {
        rpc.init_rpc();
        rpc.updateRavenRPC();
        MinecraftForge.EVENT_BUS.register(rpc);
    }

    public void onDisable() {
        rpc.shutdown_rpc();
        MinecraftForge.EVENT_BUS.unregister(rpc);
    }
}
