package keystrokesmod.client.module.modules.combat;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

public class AutoSoup extends Module {

    private final DoubleSliderSetting delay, coolDown, invCoolDown, invWait;
    private final SliderSetting health;
    private final CoolDown cd = new CoolDown(1), invCd = new CoolDown(1);
    private final TickSetting invConsume, autoRefill;
    private State state = State.WAITINGTOSWITCH;
    private int originalSlot;
    private boolean inInv;
    private List<Integer> sortedSlots = new ArrayList<>();

    public AutoSoup() {
        super("AutoSoup", ModuleCategory.combat);
        this.registerSetting(delay = new DoubleSliderSetting("delay(ms)", 50, 100, 0, 200, 1));
        this.registerSetting(coolDown = new DoubleSliderSetting("cooldown(ms)", 1000, 1200, 0, 5000, 1));
        this.registerSetting(health = new SliderSetting("health", 7, 0, 20, 0.1));
        this.registerSetting(invConsume = new TickSetting("consume in inv", false));
        this.registerSetting(autoRefill = new TickSetting("auto refil", true));
        this.registerSetting(invWait = new DoubleSliderSetting("invWait(ms)", 50, 100, 0, 200, 1));
        this.registerSetting(invCoolDown = new DoubleSliderSetting("refill delay(ms)", 50, 100, 0, 200, 1));
    }

    @Override
    public void postApplyConfig() {
        guiButtonToggled(autoRefill);
    }

    @Override
    public void guiButtonToggled(Setting b) {
        if(b == autoRefill) {
            invWait.hideComponent(autoRefill.isToggled());
            invCoolDown.hideComponent(autoRefill.isToggled());
        }
    }

    @Subscribe
    public void update(UpdateEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;
        if(
        		(invConsume.isToggled() || (mc.currentScreen == null))
        		&& (mc.thePlayer.getHealth() < health.getInput()) && cd.hasFinished()
        		) {
            switch(state) {
                case WAITINGTOSWITCH:
                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/4);
                    break;
                case NONE:
                    int slot = getSoupSlot();
                    if(slot == -1 ) return;
                    originalSlot = mc.thePlayer.inventory.currentItem;
                    mc.thePlayer.inventory.currentItem = slot;

                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/4);
                    break;
                case SWITCHED:
                    KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());

                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/4);
                    break;
                case SWITCHEDANDCLICKED:
                    KeyBinding.onTick(mc.gameSettings.keyBindDrop.getKeyCode());

                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/4);
                    break;
                case SWITCHEDANDDROPPED:
                    mc.thePlayer.inventory.currentItem = originalSlot;

                    //cd.setCooldown(1);
                    cd.setCooldown((int) Utils.Client.ranModuleVal(coolDown, Utils.Java.rand()));
                    break;
            }
            state = state.next();
            cd.start();
        }
        if (autoRefill.isToggled() && Utils.Player.isPlayerInInventory()) {
            if (!inInv) {
            	invCd.setCooldown((long) Utils.Client.ranModuleVal(invWait, Utils.Java.rand()));
            	invCd.start();
                generatePath((ContainerPlayer) mc.thePlayer.openContainer);
                inInv = true;
            }
            if (!sortedSlots.isEmpty())
				if (invCd.hasFinished()) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, sortedSlots.get(0), 0, 1, mc.thePlayer);
                    invCd.setCooldown((long) Utils.Client.ranModuleVal(invCoolDown, Utils.Java.rand()));
                    invCd.start();
                    sortedSlots.remove(0);
                }
        } else
			inInv = false;
    }

    public void generatePath(ContainerPlayer inv) {
        ArrayList<Integer> slots = new ArrayList<>();
        int slotsNeeded = 0;
        for (int i = 0; i <= 8; i++)
        	slotsNeeded = mc.thePlayer.inventory.getStackInSlot(i) == null ? slotsNeeded + 1 : slotsNeeded ;
        for (int i = 0; i < inv.getInventory().size(); i++) {
        	if(!slots.isEmpty() && (slots.size() >= slotsNeeded)) break;
			if ((inv.getInventory().get(i) != null) && (inv.getInventory().get(i).getItem() instanceof ItemSoup) && !((i >= 36) && (i <= 44)))
				slots.add(i);
        }

        this.sortedSlots = slots;
    }

    public int getSoupSlot() {
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if ((itemInSlot != null) && (itemInSlot.getItem() instanceof ItemSoup))
                return slot;
        }
        return -1;
    }

    public enum State {
    	WAITINGTOSWITCH,
        NONE,
        SWITCHED,
        SWITCHEDANDCLICKED,
        SWITCHEDANDDROPPED;

        private static State[] vals = values();
        public State next() {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }

}
