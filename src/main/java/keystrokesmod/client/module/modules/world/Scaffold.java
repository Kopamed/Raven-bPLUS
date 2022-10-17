package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.gui.ScaledResolution;

public class Scaffold extends Module {

    private TickSetting eagle;
    private SliderSetting rps;

    private float yaw, pitch, locked;
    private int blockCount;


    public Scaffold() {
        super("Scaffold", ModuleCategory.world);
        this.registerSettings(
                        eagle = new TickSetting("Shift", false),
                        rps = new SliderSetting("Rotation speed", 80, 0, 300, 1));
    }

    @Subscribe
    public void gameLoopEvent(GameLoopEvent e) {
        updateBlockCount();

    }

    @Subscribe
    public void moveEvent(MoveInputEvent e) {

    }

    @Subscribe
    public void updateEvent(UpdateEvent e) {
        e.setPitch(pitch);
        e.setYaw(yaw);
    }

    @Subscribe
    public void lookEvent(LookEvent e) {
        e.setPitch(pitch);
        e.setYaw(yaw);
    }

    @Subscribe
    public void onRender2D(Render2DEvent e) {
        ScaledResolution sr = new ScaledResolution(mc);
        FontUtil.normal.drawCenteredSmoothString(blockCount + " blocks", (int) (sr.getScaledWidth()/2f+8), (int) (sr.getScaledHeight()/2f-4), blockCount <= 16? 0xff0000 : -1);
    }

    private void updateBlockCount() {
        blockCount = 3;
        /*
        for (int i = 0; i < InventoryUtils.END; i++) {
            final ItemStack stack = InventoryUtils.getStackInSlot(i);

            if (stack != null && stack.getItem() instanceof ItemBlock &&
                    InventoryUtils.isGoodBlockStack(stack))
                blockCount += stack.stackSize;
        }*/

    }


}
