package keystrokesmod.module.modules.other;

import keystrokesmod.discordRPC.DiscordRPCManager;
import keystrokesmod.discordRPC.RPCMode;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.Module;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    public static final DiscordRPCManager rpc = new DiscordRPCManager();
    public static ModuleSettingSlider rpcMode;
    public static ModuleDesc rpcModeDesc;


    public DiscordRPCModule() {
        super("DiscordRPC", category.other);
        this.registerSetting(rpcMode = new ModuleSettingSlider("Mode", 5.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(rpcModeDesc = new ModuleDesc("Raven b+"));
    }

    public void onEnable() {
        switch ((int) rpcMode.getInput()) {
            case 1: // BADLION
                rpc.init_rpc(RPCMode.BADLION);
                break;
            case 2: // LUNAR
                rpc.init_rpc(RPCMode.LUNAR);
                break;

            case 3: // MINECRAFT
                rpc.init_rpc(RPCMode.MINECRAFT);
                break;

            case 4: // RAVEN B+
                rpc.init_rpc(RPCMode.RAVEN_BP);
                break;
        }
        rpc.updateRPC();
        MinecraftForge.EVENT_BUS.register(rpc);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(rpc);
        if (rpc.rpc_thread != null)
            rpc.rpc_thread.interrupt();
        DiscordRPC.discordClearPresence();
        DiscordRPC.discordShutdown();
    }

    private int lastRPC;
    public void guiUpdate() {
        if (lastRPC != (int) rpcMode.getInput()) { // prevent lags
            lastRPC = (int) rpcMode.getInput();

            switch ((int) rpcMode.getInput()) {
                case 1: // BADLION
                    rpcModeDesc.setDesc("Badlion");
                    rpc.updateRPCMode(RPCMode.BADLION);
                    break;

                case 2: // LUNAR
                    rpcModeDesc.setDesc("Lunar");
                    rpc.updateRPCMode(RPCMode.LUNAR);
                    break;

                case 3: // MINECRAFT
                    rpcModeDesc.setDesc("Minecraft");
                    rpc.updateRPCMode(RPCMode.MINECRAFT);
                    break;

                case 4: // RAVEN B+
                    rpcModeDesc.setDesc("Raven b+");
                    rpc.updateRPCMode(RPCMode.RAVEN_BP);
                    break;
            }
        }
    }
}
