package keystrokesmod.client.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.render.CursorTrail;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen extends Gui implements GuiYesNoCallback {

    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
    	//Utils.Player.sendMessageToSelf("a");
    	CursorTrail cursorTrail = (CursorTrail) Raven.moduleManager.getModuleByClazz(CursorTrail.class);
    	if(cursorTrail.isEnabled())
    		cursorTrail.draw(mouseX, mouseY);
    }

}
