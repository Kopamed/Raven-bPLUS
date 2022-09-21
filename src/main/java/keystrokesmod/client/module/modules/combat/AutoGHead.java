package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;

public class AutoGHead extends Module {

    private final DoubleSliderSetting delay;
    private final DoubleSliderSetting coolDown;
    private final SliderSetting health;
    private final CoolDown cd = new CoolDown(1);
    private State state = State.NONE;
    private int originalSlot;
    
    public AutoGHead() {
        super("AutoGHead", ModuleCategory.combat);
        this.registerSetting(delay = new DoubleSliderSetting("delay", 50, 100, 0, 200, 1));
        this.registerSetting(coolDown = new DoubleSliderSetting("cooldown(ms)", 1000, 1200, 0, 5000, 1));
        this.registerSetting(health = new SliderSetting("health", 7, 0, 20, 0.1));
        
    }
    
    @Subscribe
    public void update(UpdateEvent e) {
        if(!Utils.Player.isPlayerInGame())
            return;
        if(mc.thePlayer.getHealth() < health.getInput() && cd.hasFinished()) {
            switch(state) {
                case NONE:
                    int slot = getGHeadSlot();
                    if(slot == -1 ) return;
                    originalSlot = mc.thePlayer.inventory.currentItem;
                    mc.thePlayer.inventory.currentItem = slot;
                    
                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/3);
                    state = State.SWITCHED;
                    break;
                case SWITCHED:
                    KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());

                    cd.setCooldown((int) Utils.Client.ranModuleVal(delay, Utils.Java.rand())/3);
                    state = State.SWITCHEDANDCLICKED;
                    break;
                case SWITCHEDANDCLICKED:
                    mc.thePlayer.inventory.currentItem = originalSlot;
                    
                    state = State.NONE;
                    cd.setCooldown((int) Utils.Client.ranModuleVal(coolDown, Utils.Java.rand()));
                    break;
            }
            cd.start();
        }
    }
    
    public int getGHeadSlot() {
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
            if (itemInSlot != null && itemInSlot.getItem() instanceof ItemSkull
                    && (itemInSlot.getDisplayName().toLowerCase().contains("golden") && itemInSlot.getDisplayName().toLowerCase().contains("head"))) {
                return slot;
            }
        }
        return -1;
    }
    
    public enum State {
        NONE,
        SWITCHED,
        SWITCHEDANDCLICKED;
    }
    
}
