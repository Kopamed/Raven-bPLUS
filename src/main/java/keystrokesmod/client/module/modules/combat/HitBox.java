package keystrokesmod.client.module.modules.combat;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;

public class HitBox extends Module {
    public static SliderSetting a;
    public static TickSetting b;
    private static MovingObjectPosition mv;

    public HitBox() {
        super("HitBoxes", ModuleCategory.combat);
        this.registerSetting(new DescriptionSetting("Changed from multiplier to extra blocks!"));
        this.registerSetting(a = new SliderSetting("Extra Blocks", 0.2D, 0.05D, 2.0D, 0.05D));
        this.registerSetting(b = new TickSetting("Vertical", false));
    }

    /*
    @Subscribe
    public void onRenderWorldLast(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (b.isToggled() && Utils.Player.isPlayerInGame()) {
                for (Entity en : mc.theWorld.loadedEntityList) {
                    if (en != mc.thePlayer && en instanceof EntityLivingBase && ((EntityLivingBase) en).deathTime == 0
                            && !(en instanceof EntityArmorStand) && !en.isInvisible()) {
                        this.rh(en, Color.WHITE);
                    }
                }
            }
        }
    }

     */

    public static double exp(Entity en) {
        Module hitBox = Raven.moduleManager.getModuleByClazz(HitBox.class);
        return ((hitBox != null) && hitBox.isEnabled() && !AntiBot.bot(en)) ? a.getInput() : 0D;
    }

    private void rh(Entity e, Color c) {
        if (e instanceof EntityLivingBase) {
            double x = (e.lastTickPosX + ((e.posX - e.lastTickPosX) * (double) Utils.Client.getTimer().renderPartialTicks))
                    - mc.getRenderManager().viewerPosX;
            double y = (e.lastTickPosY + ((e.posY - e.lastTickPosY) * (double) Utils.Client.getTimer().renderPartialTicks))
                    - mc.getRenderManager().viewerPosY;
            double z = (e.lastTickPosZ + ((e.posZ - e.lastTickPosZ) * (double) Utils.Client.getTimer().renderPartialTicks))
                    - mc.getRenderManager().viewerPosZ;
            float ex = (float) ((double) e.getCollisionBorderSize() * a.getInput());
            AxisAlignedBB bbox = e.getEntityBoundingBox().expand(ex, ex, ex);
            AxisAlignedBB axis = new AxisAlignedBB((bbox.minX - e.posX) + x, (bbox.minY - e.posY) + y,
                    (bbox.minZ - e.posZ) + z, (bbox.maxX - e.posX) + x, (bbox.maxY - e.posY) + y, (bbox.maxZ - e.posZ) + z);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(2.0F);
            GL11.glColor3d(c.getRed(), c.getGreen(), c.getBlue());
            RenderGlobal.drawSelectionBoundingBox(axis);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
        }
    }
}