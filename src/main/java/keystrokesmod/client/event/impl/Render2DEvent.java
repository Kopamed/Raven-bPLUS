package keystrokesmod.client.event.impl;

import keystrokesmod.client.event.types.Event;

public class Render2DEvent extends Event {

    private final float partialTicks;

    public Render2DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

}
