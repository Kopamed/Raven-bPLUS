package keystrokesmod.client.clickgui.kv;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class KvCompactGui extends GuiScreen {

    private int containerX,containerY,containerWidth,containerHeight;

    public KvCompactGui() {

    }

    public void initMain() {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //drawing container
        containerWidth = (int) (this.width/1.5);
        containerHeight = (int) (this.height/1.5);
        containerX = this.width/2 - containerWidth/2;
        containerY = this.height/2 - containerHeight/2;
        drawBorderedRoundedRect(
                containerX,
                containerY,
                containerX + containerWidth,
                containerY + containerHeight,
                7,
                3,
                0x80413C39,
                0x80413C39);
        //drawing the boarders
        //Gui.drawRect(mouseY, mouseY, mouseY, mouseX, mouseY);
    }

    public static void drawRoundedRect(int x, int y, int x1, int y1, int radius, int color) {

        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        color(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);

        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
    }



    public static void color(int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GL11.glColor4d(r, g, b, alpha);
    }

    public static void drawBorderedRoundedRect(int x, int y, int x1, int y1, int borderSize, int borderC, int insideC) {
        drawRoundedRect(x, y, x1, y1, borderSize, borderC);
        drawRoundedRect((x + 1), (y + 1), (x1 - 1), (y1 - 1), borderSize, insideC);
    }

    public static void drawBorderedRoundedRect(int x, int y, int x1, int y1, int radius, int borderSize, int borderC, int insideC) {
        drawRoundedRect((x + borderSize), (y + borderSize), (x1 - borderSize), (y1 - borderSize), radius, insideC);
        drawRoundedRect(x, y, x1, y1, radius + borderSize, borderC);
    }

    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }


}
