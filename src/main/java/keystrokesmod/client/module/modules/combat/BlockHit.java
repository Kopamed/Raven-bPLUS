package keystrokesmod.client.module.modules.combat;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BlockHit extends Module {
    public static SliderSetting range, chance;
    public static DescriptionSetting eventTypeDesc;
    public static TickSetting onlyPlayers, onlyForward;
    public static DoubleSliderSetting waitMs,actionMs, hitPer, postDelay;
    public static boolean executingAction, hitCoolDown, alreadyHit, safeGuard;
    public static int hits, rhit;
    public static boolean call, trystartcombo;
    private CoolDown actionTimer = new CoolDown(0);
    private CoolDown waitTimer = new CoolDown(0);
    public Random r = new Random();

    public BlockHit() {
        super("BlockHit", ModuleCategory.combat);

        this.registerSetting(onlyPlayers = new TickSetting("Only combo players", true));
        this.registerSetting(onlyForward = new TickSetting("Only blockhit when walking forward", true));
        this.registerSetting(waitMs = new DoubleSliderSetting("Action Time (MS)", 110, 150, 1, 500, 1));
        this.registerSetting(actionMs = new DoubleSliderSetting("Block after ... ms", 20, 30, 1, 500, 1));
        this.registerSetting(hitPer = new DoubleSliderSetting("Once every ... hits", 1, 1, 1, 10, 1));
        
        this.registerSetting(chance =  new SliderSetting("Chance %", 100, 0, 100, 1));
        this.registerSetting(range = new SliderSetting("Range: ", 3, 1, 6, 0.05));

        
    }




    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) { 
    	if(!Utils.Player.isPlayerInGame())
    		return;
    	
    	if(trystartcombo && waitTimer.hasFinished()) {
    		trystartcombo = false;
    		startCombo();
    	}
    	if(actionTimer.hasFinished() && executingAction) {
    		finishCombo();
    	}
    	
    }

    @SubscribeEvent
    public void onHit(AttackEntityEvent e) {
    	//why dafaq is this called twice
    	
    	if(isSecondCall() || executingAction)
    		return;
    	hits++;
    	if(hits > rhit) {
    		hits = 1;
    		int easports = (int) (hitPer.getInputMax() - hitPer.getInputMin() + 1);
    		rhit = ThreadLocalRandom.current().nextInt((easports));
    		rhit += (int) hitPer.getInputMin();
    	}
    	System.out.println(hits + " " + rhit);
    	if(!(e.target instanceof EntityPlayer) && onlyPlayers.isToggled() 
    			|| !(Math.random() <= chance.getInput() / 100)
    			|| !Utils.Player.isPlayerHoldingSword()
    			|| mc.thePlayer.getDistanceToEntity(e.target) > range.getInput()
    			|| !(rhit == hits))
    		return;
    	System.out.println("a");
    	trystartCombo();
    }
    private void finishCombo() {
    	executingAction = false;
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, false);
        Utils.Client.setMouseButtonState(1, false);
    }

    private void startCombo() {
        if(!(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) && onlyForward.isToggled())
            return;
        
        executingAction = true;
        int key = mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState(key, true);
        KeyBinding.onTick(key);
        Utils.Client.setMouseButtonState(1, true);
        actionTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(waitMs.getInputMin(),  waitMs.getInputMax()+0.01));
    	actionTimer.start();
    }
    
    public void trystartCombo() {
    	trystartcombo = true;
    	waitTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble(actionMs.getInputMin(),  actionMs.getInputMax()+0.01));
    	waitTimer.start();
    }
    
    private static boolean isSecondCall() {
    	if(call) {
    		call = false;
    		return true;
    	} else {
    		call = true;
    		return false;
    	}
    }
}
