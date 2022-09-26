package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class Projectiles extends Module {

    public static SliderSetting w;

    public Projectiles() {
        super("Projectiles", ModuleCategory.render);
        this.registerSetting(w = new SliderSetting("Thickness", 2.0D, 1.0D, 10.0D, 1.0D));
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (!Utils.Player.isPlayerInGame())
                return;
            EntityPlayerSP player = mc.thePlayer;

            if (mc.thePlayer.getCurrentEquippedItem() == null)
                return;
            Item stack = mc.thePlayer.getCurrentEquippedItem().getItem();
            if (stack == null)
                return;

            // check if item is throwable
            if (!(stack instanceof ItemBow))
                return;

            Timer timer = new Timer(3F);
            // calculate starting position
            double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * timer.renderPartialTicks
                    - Math.cos((float) Math.toRadians(player.rotationYaw)) * 0.16F;
            double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * timer.renderPartialTicks
                    + player.getEyeHeight() - 0.1;
            double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * timer.renderPartialTicks
                    - Math.sin((float) Math.toRadians(player.rotationYaw)) * 0.16F;

            // calculate starting motion
            float arrowMotionFactor = 1F;
            float yaw = (float) Math.toRadians(player.rotationYaw);
            float pitch = (float) Math.toRadians(player.rotationPitch);
            float arrowMotionX = (float) (-Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor);
            float arrowMotionY = (float) (-Math.sin(pitch) * arrowMotionFactor);
            float arrowMotionZ = (float) (Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor);
            double arrowMotion = Math
                    .sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
            arrowMotionX /= arrowMotion;
            arrowMotionY /= arrowMotion;
            arrowMotionZ /= arrowMotion;
            float bowPower = (72000 - player.getItemInUseCount()) / 20F;
            bowPower = (bowPower * bowPower + bowPower * 2F) / 3F;

            if (bowPower > 1F || bowPower <= 0.1F)
                bowPower = 1F;

            bowPower *= 3F;
            arrowMotionX *= bowPower;
            arrowMotionY *= bowPower;
            arrowMotionZ *= bowPower;

            // GL settings
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth((int) w.getInput());

            RenderManager renderManager = mc.getRenderManager();

            // draw trajectory line
            double gravity = 0.05D;
            Vec3 playerVector = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            GL11.glColor4f(0, 1, 0, 0.75F);
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for (int i = 0; i < 1000; i++) {
                GL11.glVertex3d(arrowPosX - renderManager.viewerPosX, arrowPosY - renderManager.viewerPosY,
                        arrowPosZ - renderManager.viewerPosZ);

                arrowPosX += arrowMotionX * 0.1;
                arrowPosY += arrowMotionY * 0.1;
                arrowPosZ += arrowMotionZ * 0.1;
                arrowMotionX *= 0.999D;
                arrowMotionY *= 0.999D;
                arrowMotionZ *= 0.999D;
                arrowMotionY -= gravity * 0.1;

                if (mc.theWorld.rayTraceBlocks(playerVector, new Vec3(arrowPosX, arrowPosY, arrowPosZ)) != null)
                    break;
            }
            GL11.glEnd();

            // draw end of trajectory line
            double renderX = arrowPosX - renderManager.viewerPosX;
            double renderY = arrowPosY - renderManager.viewerPosY;
            double renderZ = arrowPosZ - renderManager.viewerPosZ;

            GL11.glPushMatrix();
            GL11.glTranslated(renderX - 0.5, renderY - 0.5, renderZ - 0.5);

            GL11.glColor4f(0F, 1F, 0F, 0.25F);
            GL11.glColor4f(0, 1, 0, 0.75F);

            GL11.glPopMatrix();

            // GL resets
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glPopMatrix();

        }
    }
}
