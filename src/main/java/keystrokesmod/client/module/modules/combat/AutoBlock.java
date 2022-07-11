package keystrokesmod.client.module.modules.combat;

import java.util.concurrent.ThreadLocalRandom;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoBlock extends Module {
	public static SliderSetting blockRange;
    public static DoubleSliderSetting unblockTime, attackDelay;
    public boolean attacked;
    public BlockState state = BlockState.p4;
    private CoolDown timer = new CoolDown(0);

    public AutoBlock(){
        super("AutoBlock", ModuleCategory.combat);
        this.registerSetting(attackDelay = new DoubleSliderSetting("Attack Delay", 60, 70, 1, 300, 1));
        this.registerSetting(unblockTime = new DoubleSliderSetting("Unblock Time", 100, 110, 1, 300, 1));
        this.registerSetting(blockRange = new SliderSetting("Block when closer than", 2, 0, 10, 0.1));
    }
    
    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent e) {
    	if(Utils.Player.isPlayerInGame() && Utils.Player.isPlayerHoldingSword()) {
    		if(Utils.Player.getClosestPlayer(blockRange.getInput()) != null) {
    			if(mc.gameSettings.keyBindAttack.isKeyDown() && state == BlockState.P3 && !attacked) {
        			KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        			timer.setCooldown((long) ThreadLocalRandom.current().nextDouble(attackDelay.getInputMin(), attackDelay.getInputMax()+0.01));
        			timer.start();
        			state = BlockState.P1;
        		} else if(state == BlockState.P1 && timer.hasFinished()) { 
        			KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
        			timer.setCooldown((long) ThreadLocalRandom.current().nextDouble(unblockTime.getInputMin(), unblockTime.getInputMax()+0.01));
        			timer.start();
        			state = BlockState.P2;	
        			attacked = true;
        		} else if(timer.hasFinished() && state == BlockState.P2) {
        			KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        			state = BlockState.P3;
        		} else if((state != BlockState.P1 && state != BlockState.P2) && !mc.gameSettings.keyBindUseItem.isKeyDown()) {
        			KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        			state = BlockState.P3;
        		} else if(!mc.gameSettings.keyBindAttack.isKeyDown() && state == BlockState.P3) {
        			attacked = false;
        		}
    			
    		} 
    	} else if (state == BlockState.P3 && !Utils.Player.isPlayerHoldingSword()) {
			state = BlockState.p4;
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
		}
    }
    
    public enum BlockState {
    	P1,
    	P2,
    	P3,
    	p4
    }


    
}
