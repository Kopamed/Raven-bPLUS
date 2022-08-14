package keystrokesmod.client.module.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoArmour extends Module {

	private DoubleSliderSetting firstDelay, delay;
	private List<Slot> sortedSlots = new ArrayList<Slot>();
	private boolean inInv;
	private CoolDown delayTimer = new CoolDown(0);

	public AutoArmour() {
		super("AutoArmour", ModuleCategory.player);
		this.registerSetting(firstDelay = new DoubleSliderSetting("Open delay", 250, 450, 0, 1000, 1));
		this.registerSetting(delay = new DoubleSliderSetting("Delay", 150, 250, 0, 1000, 1));
	}

	@SubscribeEvent
	public void openChest(TickEvent.RenderTickEvent e) {
		if(!Utils.Player.isPlayerInGame()) 
			return;
		if(mc.currentScreen != null && mc.thePlayer != null) {
			if(mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerPlayer) {
				if(!inInv) {
					delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(firstDelay.getInputMin(), firstDelay.getInputMax()+0.01));
					delayTimer.start();
					generatePath((ContainerPlayer) mc.thePlayer.openContainer);
					inInv = true;
				}
				if(inInv && !sortedSlots.isEmpty()) {
					if(delayTimer.hasFinished()) {
						mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, sortedSlots.get(0).s, 0, 1, mc.thePlayer);
						delayTimer.setCooldown((long) ThreadLocalRandom.current().nextDouble(delay.getInputMin(), delay.getInputMax()+0.01));
						delayTimer.start();
						sortedSlots.remove(0);
					} 
				} 
			} 
		} else {
			inInv = false;
		}
	}

	public void generatePath(ContainerPlayer inv) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		Slot bestArmour[] = {new Slot(-1), new Slot(-1), new Slot(-1), new Slot(-1)};
		for(int i = 0;i < inv.getInventory().size(); i++) {
			if(inv.getInventory().get(i) != null && inv.getInventory().get(i).getItem() instanceof ItemArmor && !(i > 4 && i < 9)) {
				Slot ia = new Slot(i);
				if(bestArmour[ia.at].v < ia.v) bestArmour[ia.at] = ia;
			}
		}
		for(int i = 0; i < 4; i++) {
			try {
				ItemArmor ia = (ItemArmor) inv.getInventory().get(i + 5).getItem();
				Slot s = new Slot(i + 5);
				if(s.v < bestArmour[i].v) {
					slots.add(s);
					slots.add(bestArmour[i]);
				}
			} catch(NullPointerException e) {
				slots.add(bestArmour[i]); 
			} catch(ClassCastException e ) {
				slots.add(new Slot(i + 5));
				slots.add(bestArmour[i]);
			}
		}
		this.sortedSlots = slots;
	}

	public class Slot {
		final int x;
		final int y;
		final int s;
		int at;
		float v = 0;
		boolean visited;

		public Slot(int s) {
			this.s = s;
			this.x = (s + 1) % 10;
			this.y = s / 9;
			setValues();
		}
		
		public void setValues() {
			if(s < 0) 
				return;
			
			Item is = mc.thePlayer.openContainer.getSlot(s).getStack().getItem();
			if(!(is instanceof ItemArmor)) 
				return;
			
			ItemArmor as = (ItemArmor) is;
			v = as.damageReduceAmount;
			at = as.armorType;
		}

		public double getDistance(Slot s) {
			return Math.abs(this.x-s.x) + Math.abs(this.y - s.y);
		}

		public void visit() {
			visited = true;
		}
		
	}
}
