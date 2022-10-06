package keystrokesmod.client.module.modules.combat;

import java.awt.Color;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.render.PlayerESP;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * WHO MADE THIS AND WHY PLEASE WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHYW
 */
public class LegitAura2 extends Module {

    private EntityPlayer target;
    public DoubleSliderSetting reach;
    private SliderSetting rotationDistance;
    private float yaw, pitch;

    public LegitAura2() {
        super("Aura", ModuleCategory.combat);
        this.registerSetting(reach = new DoubleSliderSetting("Reach (Blocks)", 3.1, 3.3, 3, 6, 0.05));
        this.registerSetting(rotationDistance = new SliderSetting("Rotation", 3.5, 3, 6, 0.05));
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if(e.isPre()) {
            target = (EntityPlayer) Utils.Player.getClosestPlayer((float) rotationDistance.getInput());
            if((target != null) && !AntiBot.bot(target)) {
                float[] i = Utils.Player.getTargetRotations(target, 0);
                yaw = i[0];
                pitch = i[1];
                mc.thePlayer.setRotationYawHead(yaw);
                e.setYaw(yaw);
                e.setPitch(pitch);
            }
        }
    }

    @Subscribe
    public void renderWorldLast(ForgeEvent fe) {
        if((fe.getEvent() instanceof RenderWorldLastEvent) && (target != null) && !PlayerESP.t2.isToggled()) {
            RenderWorldLastEvent e = (RenderWorldLastEvent) fe.getEvent();
            int red = (int) (((20 - target.getHealth()) * 13) > 255 ? 255 : (20 - target.getHealth()) * 13);
            int green =  255 - red;
            int rgb = new Color(red, green, 0).getRGB();
            Utils.HUD.drawBoxAroundEntity(target, 2, 0, 0, rgb, false);
        }
    }

    @Subscribe
    public void move(MoveInputEvent e) {
        if(target != null)
            e.setYaw(yaw);
    }
}
