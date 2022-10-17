package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import keystrokesmod.client.module.modules.client.SelfDestruct;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    @Shadow
    public String splashText;

    @Inject(method = "initGui", at = @At("RETURN"))
    public void initGui(CallbackInfo ci) {
        splashText = SelfDestruct.selfDestructed ? splashText : "Kopamed On Top!!";
       // SoundUtils.playSound("sus");
    }

}
