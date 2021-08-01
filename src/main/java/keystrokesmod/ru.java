//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class ru {
   public static final int rc = -1089466352;
   private static final double p2 = 6.283185307179586D;
   private static Minecraft mc = Minecraft.getMinecraft();
   public static boolean ring_c = false;

   public static void re(BlockPos bp, int color, boolean shade) {
      if (bp != null) {
         double x = (double)bp.getX() - mc.getRenderManager().viewerPosX;
         double y = (double)bp.getY() - mc.getRenderManager().viewerPosY;
         double z = (double)bp.getZ() - mc.getRenderManager().viewerPosZ;
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         float a = (float)(color >> 24 & 255) / 255.0F;
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color & 255) / 255.0F;
         GL11.glColor4d((double)r, (double)g, (double)b, (double)a);
         RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
         if (shade) {
            dbb(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D), r, g, b);
         }

         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
      }
   }

   public static void ee(Entity e, int type, double expand, double shift, int color, boolean damage) {
      if (e instanceof EntityLivingBase) {
         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float d = (float)expand / 40.0F;
         if (e instanceof EntityPlayer && damage && ((EntityPlayer)e).hurtTime != 0) {
            color = Color.RED.getRGB();
         }

         GlStateManager.pushMatrix();
         if (type == 3) {
            GL11.glTranslated(x, y - 0.2D, z);
            GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
            int outline = Color.black.getRGB();
            net.minecraft.client.gui.Gui.drawRect(-20, -1, -26, 75, outline);
            net.minecraft.client.gui.Gui.drawRect(20, -1, 26, 75, outline);
            net.minecraft.client.gui.Gui.drawRect(-20, -1, 21, 5, outline);
            net.minecraft.client.gui.Gui.drawRect(-20, 70, 21, 75, outline);
            if (color != 0) {
               net.minecraft.client.gui.Gui.drawRect(-21, 0, -25, 74, color);
               net.minecraft.client.gui.Gui.drawRect(21, 0, 25, 74, color);
               net.minecraft.client.gui.Gui.drawRect(-21, 0, 24, 4, color);
               net.minecraft.client.gui.Gui.drawRect(-21, 71, 25, 74, color);
            } else {
               int st = ay.rainbowDraw(2L, 0L);
               int en = ay.rainbowDraw(2L, 1000L);
               dGR(-21, 0, -25, 74, st, en);
               dGR(21, 0, 25, 74, st, en);
               net.minecraft.client.gui.Gui.drawRect(-21, 0, 21, 4, en);
               net.minecraft.client.gui.Gui.drawRect(-21, 71, 21, 74, st);
            }

            GlStateManager.enableDepth();
         } else {
            int i;
            if (type == 4) {
               EntityLivingBase en = (EntityLivingBase)e;
               double r = (double)(en.getHealth() / en.getMaxHealth());
               int b = (int)(74.0D * r);
               int hc = r < 0.3D ? Color.red.getRGB() : (r < 0.5D ? Color.orange.getRGB() : (r < 0.7D ? Color.yellow.getRGB() : Color.green.getRGB()));
               GL11.glTranslated(x, y - 0.2D, z);
               GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
               GlStateManager.disableDepth();
               GL11.glScalef(0.03F + d, 0.03F + d, 0.03F + d);
               i = (int)(21.0D + shift * 2.0D);
               net.minecraft.client.gui.Gui.drawRect(i, -1, i + 5, 75, Color.black.getRGB());
               net.minecraft.client.gui.Gui.drawRect(i + 1, b, i + 4, 74, Color.darkGray.getRGB());
               net.minecraft.client.gui.Gui.drawRect(i + 1, 0, i + 4, b, hc);
               GlStateManager.enableDepth();
            } else if (type == 6) {
               d3p(x, y, z, 0.699999988079071D, 45, 1.5F, color, color == 0);
            } else {
               if (color == 0) {
                  color = ay.rainbowDraw(2L, 0L);
               }

               float a = (float)(color >> 24 & 255) / 255.0F;
               float r = (float)(color >> 16 & 255) / 255.0F;
               float g = (float)(color >> 8 & 255) / 255.0F;
               float b = (float)(color & 255) / 255.0F;
               if (type == 5) {
                  GL11.glTranslated(x, y - 0.2D, z);
                  GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
                  GlStateManager.disableDepth();
                  GL11.glScalef(0.03F + d, 0.03F, 0.03F + d);
                  int base = 1;
                  d2p(0.0D, 95.0D, 10, 3, Color.black.getRGB());

                  for(i = 0; i < 6; ++i) {
                     d2p(0.0D, (double)(95 + (10 - i)), 3, 4, Color.black.getRGB());
                  }

                  for(i = 0; i < 7; ++i) {
                     d2p(0.0D, (double)(95 + (10 - i)), 2, 4, color);
                  }

                  d2p(0.0D, 95.0D, 8, 3, color);
                  GlStateManager.enableDepth();
               } else {
                  AxisAlignedBB bbox = e.getEntityBoundingBox().expand(0.1D + expand, 0.1D + expand, 0.1D + expand);
                  AxisAlignedBB axis = new AxisAlignedBB(bbox.minX - e.posX + x, bbox.minY - e.posY + y, bbox.minZ - e.posZ + z, bbox.maxX - e.posX + x, bbox.maxY - e.posY + y, bbox.maxZ - e.posZ + z);
                  GL11.glBlendFunc(770, 771);
                  GL11.glEnable(3042);
                  GL11.glDisable(3553);
                  GL11.glDisable(2929);
                  GL11.glDepthMask(false);
                  GL11.glLineWidth(2.0F);
                  GL11.glColor4f(r, g, b, a);
                  if (type == 1) {
                     RenderGlobal.drawSelectionBoundingBox(axis);
                  } else if (type == 2) {
                     dbb(axis, r, g, b);
                  }

                  GL11.glEnable(3553);
                  GL11.glEnable(2929);
                  GL11.glDepthMask(true);
                  GL11.glDisable(3042);
               }
            }
         }

         GlStateManager.popMatrix();
      }
   }

   public static void dbb(AxisAlignedBB abb, float r, float g, float b) {
      float a = 0.25F;
      Tessellator ts = Tessellator.getInstance();
      WorldRenderer vb = ts.getWorldRenderer();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
   }

   public static void dtl(Entity e, int color, float lw) {
      if (e != null) {
         double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
         double y = (double)e.getEyeHeight() + e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
         double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)ay.getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
         float a = (float)(color >> 24 & 255) / 255.0F;
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color & 255) / 255.0F;
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glEnable(2848);
         GL11.glDisable(2929);
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3042);
         GL11.glLineWidth(lw);
         GL11.glColor4f(r, g, b, a);
         GL11.glBegin(2);
         GL11.glVertex3d(0.0D, (double)mc.thePlayer.getEyeHeight(), 0.0D);
         GL11.glVertex3d(x, y, z);
         GL11.glEnd();
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glPopMatrix();
      }
   }

   public static void dGR(int left, int top, int right, int bottom, int startColor, int endColor) {
      int j;
      if (left < right) {
         j = left;
         left = right;
         right = j;
      }

      if (top < bottom) {
         j = top;
         top = bottom;
         bottom = j;
      }

      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos((double)right, (double)top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos((double)left, (double)top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos((double)left, (double)bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      worldrenderer.pos((double)right, (double)bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void db(int w, int h, int r) {
      int c = r == -1 ? -1089466352 : r;
      net.minecraft.client.gui.Gui.drawRect(0, 0, w, h, c);
   }

   public static void dct(String text, char lineSplit, int x, int y, long s, long shift, boolean rect, FontRenderer fontRenderer) {
      int bX = x;
      int l = 0;
      long r = 0L;

      for(int i = 0; i < text.length(); ++i) {
         char c = text.charAt(i);
         if (c == lineSplit) {
            ++l;
            x = bX;
            y += fontRenderer.FONT_HEIGHT + 5;
            r = shift * (long)l;
         } else {
            fontRenderer.drawString(String.valueOf(c), (float)x, (float)y, ay.rainbowDraw(s, r), rect);
            x += fontRenderer.getCharWidth(c);
            if (c != ' ') {
               r -= 90L;
            }
         }
      }

   }

   public static void d2p(double x, double y, int radius, int sides, int color) {
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(r, g, b, a);
      worldrenderer.begin(6, DefaultVertexFormats.POSITION);

      for(int i = 0; i < sides; ++i) {
         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         worldrenderer.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0D).endVertex();
      }

      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void d3p(double x, double y, double z, double radius, int sides, float lineWidth, int color, boolean chroma) {
      float a = (float)(color >> 24 & 255) / 255.0F;
      float r = (float)(color >> 16 & 255) / 255.0F;
      float g = (float)(color >> 8 & 255) / 255.0F;
      float b = (float)(color & 255) / 255.0F;
      mc.entityRenderer.disableLightmap();
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(false);
      GL11.glLineWidth(lineWidth);
      if (!chroma) {
         GL11.glColor4f(r, g, b, a);
      }

      GL11.glBegin(1);
      long d = 0L;
      long ed = 15000L / (long)sides;
      long hed = ed / 2L;

      for(int i = 0; i < sides * 2; ++i) {
         if (chroma) {
            if (i % 2 != 0) {
               if (i == 47) {
                  d = hed;
               }

               d += ed;
            }

            int c = ay.rainbowDraw(2L, d);
            float r2 = (float)(c >> 16 & 255) / 255.0F;
            float g2 = (float)(c >> 8 & 255) / 255.0F;
            float b2 = (float)(c & 255) / 255.0F;
            GL11.glColor3f(r2, g2, b2);
         }

         double angle = 6.283185307179586D * (double)i / (double)sides + Math.toRadians(180.0D);
         GL11.glVertex3d(x + Math.cos(angle) * radius, y, z + Math.sin(angle) * radius);
      }

      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      mc.entityRenderer.enableLightmap();
   }
}
