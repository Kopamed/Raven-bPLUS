package me.kopamed.raven.bplus.client.feature.module.modules.other;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.helper.discordRPC.DiscordCompatibility;
import me.kopamed.raven.bplus.helper.discordRPC.DiscordRPCManager;
import me.kopamed.raven.bplus.helper.discordRPC.RPCMode;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    public static final DiscordRPCManager rpc = new DiscordRPCManager();
    public static NumberSetting rpcMode;
    public static DescriptionSetting rpcModeDesc, unsupportedOS;


    public DiscordRPCModule() {
        super("DiscordRPC", ModuleCategory.Misc);

        if (!DiscordCompatibility.isCompatible()) {
            this.registerSetting(unsupportedOS = new DescriptionSetting("Unsupported OS!"));
        }
        this.registerSetting(rpcMode = new NumberSetting("Mode", 4.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(rpcModeDesc = new DescriptionSetting("Raven b+"));
    }

    public void onEnable() {
        if (!DiscordCompatibility.isCompatible()) return;

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
        if (!DiscordCompatibility.isCompatible()) return;

        MinecraftForge.EVENT_BUS.unregister(rpc);
        if (rpc.rpc_thread != null)
            rpc.rpc_thread.interrupt();
        DiscordRPC.discordShutdown();
    }

    private int lastRPC;
    public void guiUpdate() {
        if (!DiscordCompatibility.isCompatible()) return;

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
