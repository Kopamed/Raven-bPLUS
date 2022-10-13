package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.other.NameHider;
import net.minecraft.client.gui.FontRenderer;

@Mixin(priority = 1005, value = FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "renderStringAtPos", at = @At("HEAD"))
    private void renderStringAtPos(String p_renderStringAtPos_1_, boolean p_renderStringAtPos_2_, CallbackInfo ci) {
        Module nameHider = Raven.moduleManager.getModuleByClazz(NameHider.class);
        if ((nameHider != null) && nameHider.isEnabled())
			p_renderStringAtPos_1_ = NameHider.format(p_renderStringAtPos_1_);
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"))
    public void getStringWidth(String p_getStringWidth_1_, CallbackInfoReturnable<Integer> cir) {
        Module nameHider = Raven.moduleManager.getModuleByClazz(NameHider.class);
        if ((nameHider != null) && nameHider.isEnabled())
			p_getStringWidth_1_ = NameHider.format(p_getStringWidth_1_);
    }

}
