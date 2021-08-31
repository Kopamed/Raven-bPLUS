package keystrokesmod.module.modules.debug;

import keystrokesmod.utils.ay;
import keystrokesmod.module.Module;
import keystrokesmod.module.modules.client.SelfDestruct;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageInfo extends Module {
    public MessageInfo() {
        super("Message Info", category.debug, 0);
    }

    @SubscribeEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event){
        if(!SelfDestruct.destructed){
            ay.sendMessageToSelf("&eUnformatted: " + event.message.getUnformattedText().replace("§", "%"));
        }
    }
}
