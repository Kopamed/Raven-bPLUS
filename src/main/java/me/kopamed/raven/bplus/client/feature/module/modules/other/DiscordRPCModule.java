package me.kopamed.raven.bplus.client.feature.module.modules.other;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.helper.discordRPC.DiscordRPCManager;
import me.kopamed.raven.bplus.helper.discordRPC.RPCMode;
import me.kopamed.raven.bplus.helper.utils.Tracker;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    public static final DiscordRPCManager rpc = new DiscordRPCManager();
    public static NumberSetting rpcMode;
    public static DescriptionSetting rpcModeDesc, unsupportedOS;


    public DiscordRPCModule() {
        super("DiscordRPC", ModuleCategory.Misc);

        Tracker tracker = Raven.client.getTracker();
        String osArch = tracker.getOsArch();
        String osName = tracker.getOsName();

        if (osArch.contains("arm") || osArch.contains("aarch64") || osName.toLowerCase().contains("mac")) {
            this.registerSetting(unsupportedOS = new DescriptionSetting("Unsupported OS!"));
        }
        this.registerSetting(rpcMode = new NumberSetting("Mode", 4.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(rpcModeDesc = new DescriptionSetting("Raven b+"));
    }

    public void onEnable() {
        Tracker tracker = Raven.client.getTracker();
        String osArch = tracker.getOsArch();
        String osName = tracker.getOsName();

        if(osArch.contains("arm") || osArch.contains("aarch64") || osName.toLowerCase().contains("mac")) {
            Utils.Player.sendMessageToSelf("&cYour computer OS/architecture is not supported yet!");
            this.disable();
            return;
        }
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
        Tracker tracker = Raven.client.getTracker();
        String osArch = tracker.getOsArch();
        String osName = tracker.getOsName();

        MinecraftForge.EVENT_BUS.unregister(rpc);
        if (osArch.contains("arm") || osArch.contains("aarch64") || osName.toLowerCase().contains("mac")) return;
        if (rpc.rpc_thread != null)
            rpc.rpc_thread.interrupt();
        DiscordRPC.discordShutdown();
    }

    private int lastRPC;
    public void guiUpdate() {
        Tracker tracker = Raven.client.getTracker();
        String osArch = tracker.getOsArch();
        String osName = tracker.getOsName();

        if (osArch.contains("arm") || osArch.contains("aarch64") || osName.toLowerCase().contains("mac")) return;
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
