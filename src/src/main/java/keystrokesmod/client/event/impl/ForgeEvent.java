package keystrokesmod.client.event.impl;

import keystrokesmod.client.event.types.Event;

public class ForgeEvent extends Event {

    private final net.minecraftforge.fml.common.eventhandler.Event event;

    public ForgeEvent(net.minecraftforge.fml.common.eventhandler.Event event) {
        this.event = event;
    }

    public net.minecraftforge.fml.common.eventhandler.Event getEvent() {
        return event;
    }

}
