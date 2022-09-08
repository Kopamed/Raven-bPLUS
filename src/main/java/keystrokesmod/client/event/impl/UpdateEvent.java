package keystrokesmod.client.event.impl;

import keystrokesmod.client.event.EventTiming;
import keystrokesmod.client.event.types.Event;
import keystrokesmod.client.event.types.IEventTiming;

public class UpdateEvent extends Event implements IEventTiming {

    private final EventTiming timing;
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public UpdateEvent(EventTiming timing, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.timing = timing;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public static UpdateEvent convertPost(UpdateEvent e) {
        return new UpdateEvent(EventTiming.POST, e.getX(), e.getY(), e.getZ(), e.getYaw(), e.getPitch(),
                e.isOnGround());
    }

    @Override
    public EventTiming getTiming() {
        return timing;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

}
