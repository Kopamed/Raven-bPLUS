package keystrokesmod.client.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.module.modules.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class RenderUtils {

	public static void stopDrawing() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glDisable(2848);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
	}

	public static void startDrawing() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		try {
			Method m = ReflectionHelper.findMethod(EntityRenderer.class, Minecraft.getMinecraft().entityRenderer,
					new String[] { "func_78479_a", "setupCameraTransform" }, float.class, int.class);

			m.setAccessible(true);
			m.invoke(Minecraft.getMinecraft().entityRenderer, Utils.Client.getTimer().renderPartialTicks, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

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

	public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1f);
		Utils.mc.getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0.0f, 0.0f, (int) width, (int) height, width, height);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glEnable(2929);
	}

	public static void drawRect(double d0, double d1, double d2, double d3, int i) {
		double d4;

		if (d0 < d2) {
			d4 = d0;
			d0 = d2;
			d2 = d4;
		}

		if (d1 < d3) {
			d4 = d1;
			d1 = d3;
			d3 = d4;
		}

		float f = (float) ((i >> 24) & 255) / 255.0F;
		float f1 = (float) ((i >> 16) & 255) / 255.0F;
		float f2 = (float) ((i >> 8) & 255) / 255.0F;
		float f3 = (float) (i & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f1, f2, f3, f);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION);
		worldrenderer.pos(d0, d3, 0.0D).endVertex();
		worldrenderer.pos(d2, d3, 0.0D).endVertex();
		worldrenderer.pos(d2, d1, 0.0D).endVertex();
		worldrenderer.pos(d0, d1, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawBorderedRect(float f, float f1, float f2, float f3, float f4, int i, int j) {
		drawRect(f, f1, f2, f3, j);
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

	public static void drawBorderedRoundedRect(float x, float y, float d, float y1, float radius, float borderSize, int borderC, int insideC) {
		drawRoundedRect(x, y, d, y1, radius, insideC);
		drawRoundedOutline(x, y, d, y1, radius, borderSize, borderC);
	}

	public static ResourceLocation getResourcePath(String s) {
        InputStream ravenLogoInputStream = HUD.class.getResourceAsStream(s);
        BufferedImage bf;
        try {
            assert ravenLogoInputStream != null;
            bf = ImageIO.read(ravenLogoInputStream);
            return Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven",new DynamicTexture(bf));
        } catch (IOException | IllegalArgumentException | NullPointerException noway) {
            noway.printStackTrace();
            return new ResourceLocation("null");
        }
	}
}
