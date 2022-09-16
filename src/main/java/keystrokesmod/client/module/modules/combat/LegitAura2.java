package keystrokesmod.client.module.modules.combat;

import java.awt.Color;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.render.PlayerESP;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class LegitAura2 extends Module {

    private EntityPlayer target;
    private DoubleSliderSetting reach;
    private SliderSetting rotationDistance;

    public LegitAura2() {
        super("Aura", ModuleCategory.combat);
        this.registerSetting(reach = new DoubleSliderSetting("Reach (Blocks)", 3.1, 3.3, 3, 6, 0.05));
        this.registerSetting(rotationDistance = new SliderSetting("Rotation", 3.5, 3, 6, 0.05));
    }

    @Subscribe 
    public void onUpdate(UpdateEvent e) {
        Entity en = Utils.Player.getClosestPlayer((float) rotationDistance.getInput());
        target = (EntityPlayer) en;
        if(en != null && !AntiBot.bot(en)) {
            Utils.Player.silentAim(en);
        } else {
            mc.thePlayer.rotationYawHead = mc.thePlayer.cameraYaw;
        }
    }

    @Subscribe
    public void renderWorldLast(ForgeEvent fe) {
        if(fe.getEvent() instanceof RenderWorldLastEvent && target != null && !PlayerESP.t2.isToggled()) {
            RenderWorldLastEvent e = (RenderWorldLastEvent) fe.getEvent();
            int red = (int) (((20 - target.getHealth()) * 13) > 255 ? 255 : (20 - target.getHealth()) * 13);
            int green =  255 - red;
            int rgb = new Color(red, green, 0).getRGB();
            Utils.HUD.drawBoxAroundEntity(target, 2, 0, 0, rgb, false);
        }
    }

    @Subscribe
    public void packet(PacketEvent e) {
        Packet p = e.getPacket();
        if(target != null) {
            if(p instanceof C05PacketPlayerLook) {
                C05PacketPlayerLook pe = (C05PacketPlayerLook) e.getPacket();
                e.setCancelled(true);
            }
        }
    }

    @Subscribe
    public void move(MoveInputEvent e) {
        if(target != null)
            e.setYaw(mc.thePlayer.prevRotationYawHead);
    }

    public DoubleSliderSetting getReach() {
        return reach;
    }
}
