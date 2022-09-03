package keystrokesmod.client.mixin.mixins;

import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.LeftClicker;
import keystrokesmod.client.module.modules.combat.Reach;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 1005, value = Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        Module autoClicker = Raven.moduleManager.getModuleByClazz(LeftClicker.class);
        if (autoClicker == null || !autoClicker.isEnabled() || !Mouse.isButtonDown(0) || !Reach.call()) {
            Minecraft.getMinecraft().entityRenderer.getMouseOver(1.0F);
        }

        Raven.eventBus.post(new GameLoopEvent());
    }

}
