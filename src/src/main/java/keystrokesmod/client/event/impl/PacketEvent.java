package keystrokesmod.client.event.impl;

import keystrokesmod.client.event.EventDirection;
import keystrokesmod.client.event.types.CancellableEvent;
import keystrokesmod.client.event.types.IEventDirection;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent implements IEventDirection {

    private final Packet<?> packet;
    private final EventDirection direction;

    public PacketEvent(Packet<?> packet, EventDirection direction) {
        this.packet = packet;
        this.direction = direction;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) this.packet;
    }

    @Override
    public EventDirection getDirection() {
        return direction;
    }

}