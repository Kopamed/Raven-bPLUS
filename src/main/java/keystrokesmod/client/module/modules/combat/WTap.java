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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WTap extends Module {
    public static SliderSetting range, chance, tapMultiplier;
    public static DescriptionSetting eventTypeDesc;
    public static TickSetting onlyPlayers, onlySword, autoCfg, dynamic;
    public static DoubleSliderSetting waitMs,actionMs, hitPer, postDelay;
    public static int hits, rhit;
    public static boolean call, p;
    public static long s;
    private WtapState state = WtapState.NONE;
    private CoolDown timer = new CoolDown(0);
    private Entity target;
   
    public Random r = new Random();
    
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
    	if (p && Utils.Player.isPlayerInGame()) {
	         if (Utils.Java.str(e.message.getUnformattedText()).contains("Unknown")) {
	            e.setCanceled(true);
	            p = false;
	            int ping = (int) ((System.currentTimeMillis() - s) - 20);
	            actionMs.setValueMin(ping - 5);
	            actionMs.setValueMax(ping + 5);
	         }
	      }
    }
    
    public void onEnable() {
    	if(autoCfg.isToggled()) {
    		  Utils.mc.thePlayer.sendChatMessage("/...");
    		  p = true;
    		  s = System.currentTimeMillis();
    	}
    }
    
    
    
    public WTap(){
        super("WTap", ModuleCategory.combat);
        
        this.registerSetting(autoCfg = new TickSetting("Auto config wtap delay", true));
        this.registerSetting(onlyPlayers = new TickSetting("Only combo players", true));
        this.registerSetting(dynamic = new TickSetting("Dynamic Tapping (BETA)", false));
        this.registerSetting(onlySword = new TickSetting("Only sword", false));
        this.registerSetting(waitMs = new DoubleSliderSetting("Release w for ... ms", 30, 40, 1, 300, 1));
        this.registerSetting(actionMs = new DoubleSliderSetting("WTap after ... ms", 20, 30, 1, 300, 1));
        this.registerSetting(hitPer = new DoubleSliderSetting("Once every ... hits", 1, 1, 1, 10, 1)); 
        this.registerSetting(chance =  new SliderSetting("Chance %", 100, 0, 100, 1));
        this.registerSetting(range = new SliderSetting("Range: ", 3, 1, 6, 0.05));
        this.registerSetting(tapMultiplier = new SliderSetting("closer = more tap", 1F, 0F, 5F, 0.1F));
    }



    public void finishCombo() {
    	if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
    		KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    	}
    	state = WtapState.NONE;
    	hits = 0;
		int easports = (int) (hitPer.getInputMax() - hitPer.getInputMin() + 1);
		rhit = ThreadLocalRandom.current().nextInt((easports));
		rhit += (int) hitPer.getInputMin();
    }

    public void startCombo() {
        state = WtapState.TAPPING;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
        double cd = (double) ThreadLocalRandom.current().nextDouble(waitMs.getInputMin(), waitMs.getInputMax()+0.01);
        if (dynamic.isToggled()) {
        	cd = 3 - mc.thePlayer.getDistanceToEntity(target) < 3 ? (cd + (3 - mc.thePlayer.getDistanceToEntity(target) * tapMultiplier.getInput() * 10)) : cd; 
        }
        
        timer.setCooldown((long) cd);
    	timer.start();
    }
    
    
    public void trystartCombo() {
    	state = WtapState.WAITINGTOTAP;
    	timer.setCooldown((long)ThreadLocalRandom.current().nextDouble(actionMs.getInputMin(),  actionMs.getInputMax()+0.01));
    	timer.start();
    }
    
    @SubscribeEvent
    public void wTap(AttackEntityEvent e) {
    	if(isSecondCall()) 
    		return;
    	//Utils.Player.sendMessageToSelf(state.toString());
    	if(state != WtapState.NONE)
    		return;
    	target = e.target;
    	if(!(Math.random() <= chance.getInput() / 100)) {
    		hits++;
    	}
    	if(mc.thePlayer.getDistanceToEntity(target) > range.getInput()
    			|| (onlyPlayers.isToggled() && !(target instanceof EntityPlayer))
    			|| (onlySword.isToggled() && !Utils.Player.isPlayerHoldingSword())
    			|| !(rhit >= hits))
    		return;
    	trystartCombo();
    	//Utils.Player.sendMessageToSelf(state.toString());
    }
    
    @SubscribeEvent
    public void wTapUpdate(TickEvent.RenderTickEvent e) {
    	//Utils.Player.sendMessageToSelf(state.toString());
    	if(state == WtapState.NONE)
    		return;
    	if(state == WtapState.WAITINGTOTAP && timer.hasFinished()) {
    		startCombo();
    	} else if (state == WtapState.TAPPING && timer.hasFinished()) {
    		finishCombo();
    	}
    }
    
    private boolean isSecondCall() {
    	if(call) {
    		call = false;
    		return true;
    	} else {
    		call = true;
    		return false;
    	}
    }
    
    public enum WtapState {
    	NONE,
    	WAITINGTOTAP,
    	TAPPING,
    }
}
