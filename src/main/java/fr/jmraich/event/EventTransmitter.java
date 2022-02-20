package fr.jmraich.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * FML events to sub-system
 */
public class EventTransmitter {
    /**
     * Transmit all events to the event sub-system
     * @param e any forge event
     */
    @SubscribeEvent
    public void onEvent(Event e) {
        EventManager.callEvent(e);
    }
}
