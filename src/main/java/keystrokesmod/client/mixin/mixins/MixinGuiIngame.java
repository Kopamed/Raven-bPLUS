package keystrokesmod.client.mixin.mixins;

import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.main.Raven;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 995, value = GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal = 1))
    public void renderHudHere(float p_renderGameOverlay_1_, CallbackInfo ci) {
        Raven.eventBus.post(new Render2DEvent(p_renderGameOverlay_1_));
    }

}
