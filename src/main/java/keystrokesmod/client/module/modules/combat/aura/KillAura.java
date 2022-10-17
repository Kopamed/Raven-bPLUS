package keystrokesmod.client.module.modules.combat.aura;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.GameLoopEvent;
import keystrokesmod.client.event.impl.LookEvent;
import keystrokesmod.client.event.impl.MoveInputEvent;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.client.Targets;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
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
public class KillAura extends Module {

    private EntityPlayer target;

    public static SliderSetting rotationDistance, fov, reach, rps;
    private DoubleSliderSetting cps;
    private TickSetting disableOnTp, disableWhenFlying, mouseDown, onlySurvival, fixMovement;
    private ComboSetting blockMode;

    private List<EntityPlayer> pTargets;
    private ComboSetting sortMode;
    private CoolDown coolDown = new CoolDown(1);
    private boolean leftDown, leftn, locked;
    private long leftDownTime, leftUpTime, leftk, leftl;
    private float yaw, pitch, prevYaw, prevPitch;
    private double leftm;
    private float lrtt;

    public KillAura() {
        super("KillAura", ModuleCategory.combat);
        this.registerSetting(new DescriptionSetting("Set targets in Client->Targets"));
        this.registerSetting(reach = new SliderSetting("Reach (Blocks)", 3.3, 3, 6, 0.05));
        this.registerSetting(rps = new SliderSetting("Max rotation speed", 36, 0, 200, 1));
        this.registerSetting(cps = new DoubleSliderSetting("Left CPS", 9, 13, 1, 60, 0.5));
        this.registerSetting(onlySurvival = new TickSetting("Only Survival", true));
        this.registerSetting(disableOnTp = new TickSetting("Disable after tp", true));
        this.registerSetting(disableWhenFlying = new TickSetting("Disable when flying", true));
        this.registerSetting(mouseDown = new TickSetting("Mouse Down", true));
        this.registerSetting(fixMovement = new TickSetting("Movement Fix", true));
        this.registerSetting(blockMode = new ComboSetting("Block mode", BlockMode.NONE));
    }

    @Subscribe
    public void gameLoopEvent(GameLoopEvent e) {
        Mouse.poll();
        EntityPlayer pTarget = Targets.getTarget();
        if(
                        (pTarget == null)
                        || (mc.currentScreen != null)
                        || !(!onlySurvival.isToggled() || (mc.playerController.getCurrentGameType() == GameType.SURVIVAL))
                        || !coolDown.hasFinished()
                        || !(!mouseDown.isToggled() || Mouse.isButtonDown(0))
                        || !(!disableWhenFlying.isToggled() || !mc.thePlayer.capabilities.isFlying)) {
            target = null;
            rotate(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true);
            return;
        }
        target = pTarget;
        ravenClick();
        float[] i = Utils.Player.getTargetRotations(target, 0);
        locked = false;
        rotate(i[0], i[1], false);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if(!Utils.Player.isPlayerInGame() || locked) {
            return;
        }
        e.setYaw(yaw);
        e.setPitch(pitch);
    }

    @Subscribe
    public void onTick(keystrokesmod.client.event.impl.TickEvent e) {
        BlockMode m = (BlockMode) blockMode.getMode();
        if((m == BlockMode.FUCKY) && (mc.thePlayer.prevSwingProgress < mc.thePlayer.swingProgress))
            KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    }

    @Subscribe
    public void renderWorldLast(ForgeEvent fe) {
        if((fe.getEvent() instanceof RenderWorldLastEvent) && (target != null)) {
            int red = (int) (((20 - target.getHealth()) * 13) > 255 ? 255 : (20 - target.getHealth()) * 13);
            int green =  255 - red;
            final int rgb = new Color(red, green, 0).getRGB();
            Utils.HUD.drawBoxAroundEntity(target, 2, 0, 0, rgb, false);
            for(EntityPlayer p : pTargets)
                Utils.HUD.drawBoxAroundEntity(p, 2, 0, 0, 0x800000FF, false);
        }
    }

    public void rotate(float yaw, float pitch, boolean e) {
       this.yaw = yaw;
       this.pitch = pitch;
    }

    @Subscribe
    public void packetEvent(PacketEvent e) {
        if((e.getPacket() instanceof S08PacketPlayerPosLook) && disableOnTp.isToggled() && (coolDown.getTimeLeft() < 2000)) {
            coolDown.setCooldown(2000);
            coolDown.start();
        }
    }

    @Subscribe
    public void move(MoveInputEvent e) {
        if(!fixMovement.isToggled() || locked) return;
        e.setYaw(yaw);
    }

    @Subscribe
    public void lookEvent(LookEvent e) {
        if(locked) return;
        e.setPrevYaw(prevYaw);
        e.setPrevPitch(prevPitch);
        e.setYaw(yaw);
        e.setPitch(pitch);
    }

    private void ravenClick() {
        this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
    }

    public void leftClickExecute(int key) {
        if ((this.leftUpTime > 0L) && (this.leftDownTime > 0L)) {
            if ((System.currentTimeMillis() > this.leftUpTime) && leftDown) {
                if(mc.thePlayer.isUsingItem())
                    mc.thePlayer.stopUsingItem();
                KeyBinding.onTick(key);
                this.genLeftTimings();
                Utils.Client.setMouseButtonState(0, true);
                leftDown = false;
            } else if (System.currentTimeMillis() > this.leftDownTime) {
                if(Mouse.isButtonDown(1))
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
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

    public double getReach() {
        return reach.getInput();
    }

    public enum BlockMode {
        NONE,
        FUCKY;
    }

    private enum RotatingState {
        SYNC,
        TARGET,
        TTS;
    }

}