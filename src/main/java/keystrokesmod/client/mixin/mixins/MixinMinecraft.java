package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.main.Raven;
import net.minecraft.client.Minecraft;

@Mixin(priority = 1005, value = Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        Raven.eventBus.post(new GameLoopEvent());
    }

}
