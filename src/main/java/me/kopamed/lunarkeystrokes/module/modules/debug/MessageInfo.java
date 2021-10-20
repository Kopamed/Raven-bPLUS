package me.kopamed.lunarkeystrokes.module.modules.debug;

import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.modules.client.SelfDestruct;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MessageInfo extends Module {
    public MessageInfo() {
        super("Message Info", category.debug, 0);
    }

    @SubscribeEvent
    public void onChatMessageRecieved(ClientChatReceivedEvent event){
        if(!SelfDestruct.destructed){
            Utils.Player.sendMessageToSelf("&eUnformatted: " + event.message.getUnformattedText().replace("§", "%"));
        }
    }
}
