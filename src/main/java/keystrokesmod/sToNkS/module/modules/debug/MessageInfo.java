package keystrokesmod.sToNkS.module.modules.debug;

import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.utils.Utils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;

public class MessageInfo extends Module {
    public MessageInfo() {
        super("Message Info", category.debug, 0);
    }

    @FMLEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event){
        Utils.Player.sendMessageToSelf("&eUnformatted: " + event.message.getUnformattedText().replace("§", "%"));
    }
}
