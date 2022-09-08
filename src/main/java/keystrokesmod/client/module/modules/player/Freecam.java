package keystrokesmod.client.module.modules.player;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Freecam extends Module {
    public static SliderSetting a;
    public static TickSetting b;
    private final double toRad = 0.017453292519943295D;
    public static EntityOtherPlayerMP en;
    private int[] lcc = { Integer.MAX_VALUE, 0 };
    private final float[] sAng = { 0.0F, 0.0F };

    public Freecam() {
        super("Freecam", ModuleCategory.player);
        this.registerSetting(a = new SliderSetting("Speed", 2.5D, 0.5D, 10.0D, 0.5D));
        this.registerSetting(b = new TickSetting("Disable on damage", true));
    }

    @Override
    public void onEnable() {
        if (!Utils.Player.isPlayerInGame()) {
            return;
        }
        if (!mc.thePlayer.onGround) {
            this.disable();
        } else {
            en = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            en.copyLocationAndAnglesFrom(mc.thePlayer);
            this.sAng[0] = en.rotationYawHead = mc.thePlayer.rotationYawHead;
            this.sAng[1] = mc.thePlayer.rotationPitch;
            en.setVelocity(0.0D, 0.0D, 0.0D);
            en.setInvisible(true);
            mc.theWorld.addEntityToWorld(-8008, en);
            mc.setRenderViewEntity(en);
        }
    }

    public void onDisable() {
        if (en != null) {
            mc.setRenderViewEntity(mc.thePlayer);
            mc.thePlayer.rotationYaw = mc.thePlayer.rotationYawHead = this.sAng[0];
            mc.thePlayer.rotationPitch = this.sAng[1];
            mc.theWorld.removeEntity(en);
            en = null;
        }

        this.lcc = new int[] { Integer.MAX_VALUE, 0 };
        int rg = 1;
        int x = mc.thePlayer.chunkCoordX;
        int z = mc.thePlayer.chunkCoordZ;

        for (int x2 = -1; x2 <= 1; ++x2) {
            for (int z2 = -1; z2 <= 1; ++z2) {
                int a = x + x2;
                int b = z + z2;
                mc.theWorld.markBlockRangeForRenderUpdate(a * 16, 0, b * 16, a * 16 + 15, 256, b * 16 + 15);
            }
        }

    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (!Utils.Player.isPlayerInGame() || en == null)
            return;
        if (b.isToggled() && mc.thePlayer.hurtTime != 0) {
            this.disable();
        } else {
            mc.thePlayer.setSprinting(false);
            mc.thePlayer.moveForward = 0.0F;
            mc.thePlayer.moveStrafing = 0.0F;
            en.rotationYaw = en.rotationYawHead = mc.thePlayer.rotationYaw;
            en.rotationPitch = mc.thePlayer.rotationPitch;
            double s = 0.215D * a.getInput();
            EntityOtherPlayerMP var10000;
            double rad;
            double dx;
            double dz;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
                rad = (double) en.rotationYawHead * 0.017453292519943295D;
                dx = -1.0D * Math.sin(rad) * s;
                dz = Math.cos(rad) * s;
                var10000 = en;
                var10000.posX += dx;
                var10000 = en;
                var10000.posZ += dz;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
                rad = (double) en.rotationYawHead * 0.017453292519943295D;
                dx = -1.0D * Math.sin(rad) * s;
                dz = Math.cos(rad) * s;
                var10000 = en;
                var10000.posX -= dx;
                var10000 = en;
                var10000.posZ -= dz;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
                rad = (double) (en.rotationYawHead - 90.0F) * 0.017453292519943295D;
                dx = -1.0D * Math.sin(rad) * s;
                dz = Math.cos(rad) * s;
                var10000 = en;
                var10000.posX += dx;
                var10000 = en;
                var10000.posZ += dz;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
                rad = (double) (en.rotationYawHead + 90.0F) * 0.017453292519943295D;
                dx = -1.0D * Math.sin(rad) * s;
                dz = Math.cos(rad) * s;
                var10000 = en;
                var10000.posX += dx;
                var10000 = en;
                var10000.posZ += dz;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
                var10000 = en;
                var10000.posY += 0.93D * s;
            }

            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
                var10000 = en;
                var10000.posY -= 0.93D * s;
            }

            mc.thePlayer.setSneaking(false);
            if (this.lcc[0] != Integer.MAX_VALUE && (this.lcc[0] != en.chunkCoordX || this.lcc[1] != en.chunkCoordZ)) {
                int x = en.chunkCoordX;
                int z = en.chunkCoordZ;
                mc.theWorld.markBlockRangeForRenderUpdate(x * 16, 0, z * 16, x * 16 + 15, 256, z * 16 + 15);
            }

            this.lcc[0] = en.chunkCoordX;
            this.lcc[1] = en.chunkCoordZ;
        }
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (Utils.Player.isPlayerInGame()) {
                mc.thePlayer.renderArmPitch = mc.thePlayer.prevRenderArmPitch = 700.0F;
                Utils.HUD.drawBoxAroundEntity(mc.thePlayer, 1, 0.0D, 0.0D, Color.green.getRGB(), false);
                Utils.HUD.drawBoxAroundEntity(mc.thePlayer, 2, 0.0D, 0.0D, Color.green.getRGB(), false);
            }
        } else if (fe.getEvent() instanceof MouseEvent) {
            MouseEvent e = ((MouseEvent) fe.getEvent());

            if (Utils.Player.isPlayerInGame() && e.button != -1) {
                e.setCanceled(true);
            }
        }
    }
}
