package keystrokesmod.client.module.modules.player;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import net.minecraft.network.Packet;

public class Blink extends Module {

    private ArrayList<Packet> packets = new ArrayList<>();
    
    public Blink() {
        super("Blink", ModuleCategory.player);
    }
    
    @Subscribe
    public void packetEvent(PacketEvent p) {
        packets.add(p.getPacket());
    }
    
    @Override
    public void onEnable() {
        packets.clear();
    }
    
    @Override
    public void onDisable() {
        for(Packet packet : packets) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        packets.clear();
    }
}
