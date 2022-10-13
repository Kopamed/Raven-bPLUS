package keystrokesmod.client.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {

    public static Color blend(Color color, Color color1, double d0) {
        float f = (float) d0;
        float f1 = 1.0F - f;
        float[] afloat = new float[3];
        float[] afloat1 = new float[3];

        color.getColorComponents(afloat);
        color1.getColorComponents(afloat1);

        return new Color((afloat[0] * f) + (afloat1[0] * f1), (afloat[1] * f) + (afloat1[1] * f1),
                        (afloat[2] * f) + (afloat1[2] * f1));
    }


    public static void glScissor(int x, int y, int width, int height) {
        int scale = new ScaledResolution(Raven.mc).getScaleFactor();
        GL11.glScissor(
                        x * scale,
                        (Raven.mc.displayHeight - ((((y/height) + height)) * scale)),
                        width * scale,
                        (height + y) * scale);

    }

    public static void drawBorderedRect(float f, float f1, float f2, float f3, float f4, int i, int j) {
        float f5 = (float) ((i >> 24) & 255) / 255.0F;
        float f6 = (float) ((i >> 16) & 255) / 255.0F;
        float f7 = (float) ((i >> 8) & 255) / 255.0F;
        float f8 = (float) (i & 255) / 255.0F;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glLineWidth(f4);
        GL11.glBegin(1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f1);
        GL11.glVertex2d(f2, f1);
        GL11.glVertex2d(f, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void setColor(final int color) {
        final float a = ((color >> 24) & 0xFF) / 255.0f;
        final float r = ((color >> 16) & 0xFF) / 255.0f;
        final float g = ((color >> 8) & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, a);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, final float radius, final int color) {
        drawRoundedRect(x, y, x1, y1, radius, color,  new boolean[] {true,true,true,true} );
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, final float radius, final int color, boolean[] round) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        setColor(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        round(x, y, x1, y1, radius, round);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glEnable(3042);
        GL11.glPopAttrib();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void roundHelper(float x, float y, float radius, int pn, int pn2, int originalRotation, int finalRotation) {
        for (int i = originalRotation; i <= finalRotation; i += 3)
            GL11.glVertex2d(x + (radius * -pn) + (Math.sin((i * 3.141592653589793) / 180.0) * radius * pn), y + (radius * pn2) + (Math.cos((i * 3.141592653589793) / 180.0) * radius * pn));
    }

    public static void drawRoundedOutline(float x, float y, float x1, float y1, final float radius, final float borderSize, final int color) {
        drawRoundedOutline(x, y, x1, y1, radius, borderSize , color, new boolean[] {true,true,true,true});
    }

    public static void round(float x, float y, float x1, float y1, float radius, final boolean[] round) {
        if(round[0])
            roundHelper(x, y, radius, -1, 1,0, 90);
        else
            GL11.glVertex2d(x, y);

        if(round[1])
            roundHelper(x, y1, radius, -1, -1, 90, 180);
        else
            GL11.glVertex2d(x, y1);

        if(round[2])
            roundHelper(x1, y1, radius, 1, -1, 0, 90);
        else
            GL11.glVertex2d(x1, y1);

        if(round[3])
            roundHelper(x1, y, radius, 1, 1, 90, 180);
        else
            GL11.glVertex2d(x1, y);
    }


    public static void drawRoundedOutline(float x, float y, float x1, float y1, final float radius, final float borderSize, final int color, boolean[] drawCorner) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        setColor(color);
        GL11.glEnable(2848);
        GL11.glLineWidth(borderSize);
        GL11.glBegin(2);
        round(x, y, x1, y1, radius, drawCorner);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth(1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawBorderedRoundedRect(float x, float y, float d, float y1, float radius, float borderSize, int borderC, int insideC, boolean[] round) {
        drawRoundedRect(x, y, d, y1, radius, insideC, round);
        drawRoundedOutline(x, y, d, y1, radius, borderSize, borderC, round);
    }

    public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float radius, float borderSize, int borderC, int insideC) {
        drawRoundedRect(x, y, x1, y1, radius, insideC);
        drawRoundedOutline(x, y, x1, y1, radius, borderSize, borderC);
    }

    public static ResourceLocation getResourcePath(String s) {
        InputStream ravenLogoInputStream = HUD.class.getResourceAsStream(s);
        BufferedImage bf;
        try {
            assert ravenLogoInputStream != null;
            bf = ImageIO.read(ravenLogoInputStream);
            return Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven",new DynamicTexture(bf));
        } catch (IOException | IllegalArgumentException | NullPointerException noway) {
            return new ResourceLocation("null");
        }
    }
}
