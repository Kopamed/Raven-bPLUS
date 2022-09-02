package keystrokesmod.client.mixin.mixins;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.other.NameHider;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(priority = 995, value = GuiUtilRenderComponents.class)
public class MixinGuiUtilRenderComponents {

    @Redirect(method = "func_178908_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/IChatComponent;getUnformattedTextForChat()Ljava/lang/String;", ordinal = 0))
    private static String mixin(IChatComponent instance) {
        Module nameHider = Raven.moduleManager.getModuleByClazz(NameHider.class);
        if (nameHider != null && nameHider.isEnabled()) {
            return NameHider.format(instance.getUnformattedTextForChat());
        }

        return instance.getUnformattedTextForChat();
    }

}
