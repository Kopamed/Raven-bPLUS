package keystrokesmod.module.modules.other;

import keystrokesmod.DiscordRPCManager;
import keystrokesmod.utils.ay;
import keystrokesmod.module.Module;
import net.arikia.dev.drpc.OSUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

public class DiscordRPCModule extends Module {
    public static final DiscordRPCManager rpc = new DiscordRPCManager();

    public DiscordRPCModule() {
        super("DiscordRPC", category.other);
    }

    public void onEnable() {
        if (!new OSUtil().isMac()) {
            rpc.init_rpc();
            rpc.updateRavenRPC();
            MinecraftForge.EVENT_BUS.register(rpc);
        } else {
            mc.thePlayer.addChatMessage(new ChatComponentText(ay.r("<JMRaich> I'm sorry but Discord RPC don't work on Mac OS for now.")));
            this.disable();
        }
    }

    public void onDisable() {
        if (!new OSUtil().isMac()) {
            rpc.shutdown_rpc();
            MinecraftForge.EVENT_BUS.unregister(rpc);
        }
    }
}
