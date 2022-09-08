package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module {
    public static SliderSetting a;
    public static TickSetting b;
    public static TickSetting c;
    public static TickSetting d;
    public static TickSetting rm;
    public static TickSetting e;

    public Nametags() {
        super("Nametags", ModuleCategory.render);
        this.registerSetting(a = new SliderSetting("Offset", 0.0D, -40.0D, 40.0D, 1.0D));
        this.registerSetting(b = new TickSetting("Rect", true));
        this.registerSetting(c = new TickSetting("Show health", true));
        this.registerSetting(d = new TickSetting("Show invis", true));
        this.registerSetting(rm = new TickSetting("Remove tags", false));
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof Pre) {
            Pre e = (Pre) fe.getEvent();

            if (rm.isToggled()) {
                e.setCanceled(true);
            } else {
                if (e.entity instanceof EntityPlayer && e.entity != mc.thePlayer && e.entity.deathTime == 0) {
                    EntityPlayer en = (EntityPlayer) e.entity;
                    if (!d.isToggled() && en.isInvisible()) {
                        return;
                    }

                    if (AntiBot.bot(en) || en.getDisplayNameString().isEmpty()) {
                        return;
                    }

                    e.setCanceled(true);
                    String str = en.getDisplayName().getFormattedText();
                    if (c.isToggled()) {
                        double r = en.getHealth() / en.getMaxHealth();
                        String h = (r < 0.3D ? "§c" : (r < 0.5D ? "§6" : (r < 0.7D ? "§e" : "§a")))
                                + Utils.Java.round(en.getHealth(), 1);
                        str = str + " " + h;
                    }

                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float) e.x + 0.0F, (float) e.y + en.height + 0.5F, (float) e.z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
                    float f1 = 0.02666667F;
                    GlStateManager.scale(-f1, -f1, f1);
                    if (en.isSneaking()) {
                        GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                    }

                    GlStateManager.disableLighting();
                    GlStateManager.depthMask(false);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                    Tessellator tessellator = Tessellator.getInstance();
                    WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                    int i = (int) (-a.getInput());
                    int j = mc.fontRendererObj.getStringWidth(str) / 2;
                    GlStateManager.disableTexture2D();
                    if (b.isToggled()) {
                        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                        worldrenderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        tessellator.draw();
                    }

                    GlStateManager.enableTexture2D();
                    mc.fontRendererObj.drawString(str, -mc.fontRendererObj.getStringWidth(str) / 2, i, -1);
                    GlStateManager.enableDepth();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();
                }

            }
        }
    }
}
