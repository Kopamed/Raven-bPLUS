package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

public class AutoSoup extends Module {

    private final DoubleSliderSetting delay;
    private final DoubleSliderSetting coolDown;
    private final SliderSetting health;
    private final CoolDown cd = new CoolDown(1);
    private State state = State.WAITINGTOSWITCH;
    private int originalSlot;

    public AutoSoup() {
        super("AutoSoup", ModuleCategory.combat);
        this.registerSetting(delay = new DoubleSliderSetting("delay", 50, 100, 0, 200, 1));
        this.registerSetting(coolDown = new DoubleSliderSetting("cooldown(ms)", 1000, 1200, 0, 5000, 1));
        this.registerSetting(health = new SliderSetting("health", 7, 0, 20, 0.1));

    }

    @Subscribe
    public void update(UpdateEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;
        if((mc.thePlayer.getHealth() < health.getInput()) && cd.hasFinished()) {
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
