package keystrokesmod.client.module.modules.player;

import org.lwjgl.input.Keyboard;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Parkour extends Module {
	
	private CoolDown cd = new CoolDown(1);
	private RGBSetting rgb;

	public Parkour() {
		super("Parkour", ModuleCategory.player);
	}
	
	@SubscribeEvent
	public void r(TickEvent.RenderTickEvent e) {
		if (!Utils.Player.isPlayerInGame()) 
			return;
		
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && cd.firstFinish())
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
		
        if (mc.thePlayer.onGround && Utils.Player.playerOverAir() && (mc.thePlayer.motionX !=0 || mc.thePlayer.motionZ != 0)) {
        	KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
        	cd.setCooldown(10);
        	cd.start();
        }
	}
	
}
