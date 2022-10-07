package keystrokesmod.client.module.modules.combat;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.client.event.RenderWorldLastEvent;

/**
 * WHO MADE THIS AND WHY PLEASE WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHY WHYW
 */

// todo smoother rotations when exiting range
// uh there was one other thing as well
public class LegitAura2 extends Module {

    private EntityPlayer target;
    private SliderSetting rotationDistance, fov, reach;
    private DoubleSliderSetting cps;
    private TickSetting disableOnTp, disableWhenFlying, mouseDown, onlySurvival, raytrace;
    private CoolDown coolDown = new CoolDown(1);
    private boolean leftDown, leftn;
    private long leftDownTime, leftUpTime, leftk, leftl;
    private float yaw, pitch, prevYaw, prevPitch;
    private double leftm;

    public LegitAura2() {
        super("Aura", ModuleCategory.combat);
        this.registerSetting(reach = new SliderSetting("Reach (Blocks)", 3.3, 3, 6, 0.05));
        this.registerSetting(rotationDistance = new SliderSetting("Rotation Range", 3.5, 3, 6, 0.05));
        this.registerSetting(cps = new DoubleSliderSetting("Left CPS", 9, 13, 1, 60, 0.5));
        this.registerSetting(fov = new SliderSetting("Fov", 30, 0, 360, 1));
        this.registerSetting(onlySurvival = new TickSetting("Only Survival", true));
        this.registerSetting(disableOnTp = new TickSetting("Disable after tp", true));
        this.registerSetting(disableWhenFlying = new TickSetting("Disable when flying", true));
        this.registerSetting(mouseDown = new TickSetting("Mouse Down", true));
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if(!Utils.Player.isPlayerInGame()) {
        	yaw = mc.thePlayer.rotationYaw;
        	return;
        }
        Mouse.poll();
        EntityPlayer pTarget = Utils.Player.getClosestPlayer((float) rotationDistance.getInput());
        //Utils.Player.sendMessageToSelf(!(!mouseDown.isToggled() || Mouse.isButtonDown(0)) + "");
        if(
                (pTarget == null)
                || AntiBot.bot(pTarget)
                || (mc.currentScreen != null)
                || !(!onlySurvival.isToggled() || (mc.playerController.getCurrentGameType() == GameType.SURVIVAL))
                || !coolDown.hasFinished()
                || !(!mouseDown.isToggled() || Mouse.isButtonDown(0))
                || !(!disableWhenFlying.isToggled() || !mc.thePlayer.capabilities.isFlying)
                || !Utils.Player.fov(pTarget, (float) fov.getInput())) {
            target = null;
            prevYaw = yaw;
            prevPitch = pitch;
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
            //need to add smooth rotations here
            return;
        }
        target = pTarget;
        ravenClick();
        prevYaw = yaw;
        prevPitch = pitch;
        float[] i = Utils.Player.getTargetRotations(target, 0);
        yaw = i[0];
        pitch = i[1] + 4f;
        e.setYaw(yaw);
        e.setPitch(pitch);
    }

    @Subscribe
    public void renderWorldLast(ForgeEvent fe) {
        if((fe.getEvent() instanceof RenderWorldLastEvent) && (target != null)) {
            int red = (int) (((20 - target.getHealth()) * 13) > 255 ? 255 : (20 - target.getHealth()) * 13);
            int green =  255 - red;
            int rgb = new Color(red, green, 0).getRGB();
            Utils.HUD.drawBoxAroundEntity(target, 2, 0, 0, rgb, false);
        }
    }

    @Subscribe
    public void packetEvent(PacketEvent e) {
        if((e.getPacket() instanceof S08PacketPlayerPosLook) && mouseDown.isToggled() && (coolDown.getTimeLeft() < 2000)) {
            coolDown.setCooldown(2000);
            coolDown.start();
        }
    }

    @Subscribe
    public void move(MoveInputEvent e) {
    	if(target == null)
    		return;
    	e.setYaw(yaw);
    }

    @Subscribe
    public void lookEvent(LookEvent e) {
    	if(target == null)
    		return;
    	e.setPrevYaw(prevYaw);
    	e.setPrevPitch(prevPitch);
    	e.setYaw(yaw);
    	e.setPitch(pitch);
    }

    private void ravenClick() {
        if (!Mouse.isButtonDown(0)) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
            Utils.Client.setMouseButtonState(0, false);
        }
        if (Mouse.isButtonDown(0))
            this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
    }

    public void leftClickExecute(int key) {
        if ((this.leftUpTime > 0L) && (this.leftDownTime > 0L)) {
            if ((System.currentTimeMillis() > this.leftUpTime) && leftDown) {
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                this.genLeftTimings();
                Utils.Client.setMouseButtonState(0, true);
                leftDown = false;
            } else if (System.currentTimeMillis() > this.leftDownTime) {
                KeyBinding.setKeyBindState(key, false);
                leftDown = true;
                Utils.Client.setMouseButtonState(0, false);
            }
        } else
            this.genLeftTimings();

    }

    public void genLeftTimings() {
        double clickSpeed = Utils.Client.ranModuleVal(cps, Utils.Java.rand()) + (0.4D * Utils.Java.rand().nextDouble());
        long delay = (int) Math.round(1000.0D / clickSpeed);
        if (System.currentTimeMillis() > this.leftk) {
            if (!this.leftn && (Utils.Java.rand().nextInt(100) >= 85)) {
                this.leftn = true;
                this.leftm = 1.1D + (Utils.Java.rand().nextDouble() * 0.15D);
            } else
                this.leftn = false;

            this.leftk = System.currentTimeMillis() + 500L + Utils.Java.rand().nextInt(1500);
        }

        if (this.leftn)
            delay = (long) (delay * this.leftm);

        if (System.currentTimeMillis() > this.leftl) {
            if (Utils.Java.rand().nextInt(100) >= 80)
                delay += 50L + Utils.Java.rand().nextInt(100);

            this.leftl = System.currentTimeMillis() + 500L + Utils.Java.rand().nextInt(1500);
        }

        this.leftUpTime = System.currentTimeMillis() + delay;
        this.leftDownTime = (System.currentTimeMillis() + (delay / 2L)) - Utils.Java.rand().nextInt(10);
    }
}
