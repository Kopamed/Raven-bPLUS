package me.kopamed.lunarkeystrokes.module.modules.other;

import me.kopamed.lunarkeystrokes.discordRPC.DiscordRPCManager;
import me.kopamed.lunarkeystrokes.discordRPC.RPCMode;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.arikia.dev.drpc.DiscordRPC;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    public static final DiscordRPCManager rpc = new DiscordRPCManager();
    public static Slider rpcMode;
    public static Description rpcModeDesc, unsupportedOS;


    public DiscordRPCModule() {
        super("DiscordRPC", category.other);
        if (Ravenbplus.osArch.contains("arm") || Ravenbplus.osArch.contains("aarch64") || Ravenbplus.osName.toLowerCase().contains("mac")) {
            this.registerSetting(unsupportedOS = new Description("Unsupported OS!"));
        }
        this.registerSetting(rpcMode = new Slider("Mode", 4.0D, 1.0D, 4.0D, 1.0D));
        this.registerSetting(rpcModeDesc = new Description("Raven b+"));
    }

    public void onEnable() {
        if(Ravenbplus.osArch.contains("arm") || Ravenbplus.osArch.contains("aarch64") || Ravenbplus.osName.toLowerCase().contains("mac")) {
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
        MinecraftForge.EVENT_BUS.unregister(rpc);
        if (Ravenbplus.osArch.contains("arm") || Ravenbplus.osArch.contains("aarch64") || Ravenbplus.osName.toLowerCase().contains("mac")) return;
        if (rpc.rpc_thread != null)
            rpc.rpc_thread.interrupt();
        DiscordRPC.discordShutdown();
    }

    private int lastRPC;
    public void guiUpdate() {
        if (Ravenbplus.osArch.contains("arm") || Ravenbplus.osArch.contains("aarch64") || Ravenbplus.osName.toLowerCase().contains("mac")) return;
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
