package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.network.play.client.C03PacketPlayer;

@Mixin(C03PacketPlayer.class)
public interface MixinC03PacketPlayer {
    
    @Accessor("yaw")
    void setYaw(float newYaw);

    @Accessor("pitch")
    void setPitch(float newPitch);
}
