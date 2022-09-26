package keystrokesmod.keystroke;

import keystrokesmod.client.utils.MouseManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KeyStrokeMouse {
    private static final String[] a = { "LMB", "RMB" };
    private final Minecraft b = Minecraft.getMinecraft();
    private final int c;
    private final int d;
    private final int e;
    private final List<Long> f = new ArrayList();
    private boolean g = true;
    private long h;

    public KeyStrokeMouse(int k, int l, int m) {
        this.c = k;
        this.d = l;
        this.e = m;
    }

    public void n(int o, int p, int color) {
        boolean r = Mouse.isButtonDown(this.c);
        String s = a[this.c];
        if (r != this.g) {
            this.g = r;
            this.h = System.currentTimeMillis();
            if (r) {
                this.f.add(this.h);
            }
        }

        double j = 1.0D;
        int i = 255;
        if (r) {
            i = Math.min(255, (int) (2L * (System.currentTimeMillis() - this.h)));
            j = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.h) / 20.0D);
        } else {
            i = Math.max(0, 255 - (int) (2L * (System.currentTimeMillis() - this.h)));
            j = Math.min(1.0D, (double) (System.currentTimeMillis() - this.h) / 20.0D);
        }

        int t = color >> 16 & 255;
        int u = color >> 8 & 255;
        int v = color & 255;
        int c = (new Color(t, u, v)).getRGB();
        net.minecraft.client.gui.Gui.drawRect(o + this.d, p + this.e, o + this.d + 34, p + this.e + 22,
                2013265920 + (i << 16) + (i << 8) + i);
        if (KeyStroke.outline) {
            net.minecraft.client.gui.Gui.drawRect(o + this.d, p + this.e, o + this.d + 34, p + this.e + 1, c);
            net.minecraft.client.gui.Gui.drawRect(o + this.d, p + this.e + 21, o + this.d + 34, p + this.e + 22, c);
            net.minecraft.client.gui.Gui.drawRect(o + this.d, p + this.e, o + this.d + 1, p + this.e + 22, c);
            net.minecraft.client.gui.Gui.drawRect(o + this.d + 33, p + this.e, o + this.d + 34, p + this.e + 22, c);
        }

        this.b.fontRendererObj.drawString(s, o + this.d + 8, p + this.e + 4,
                -16777216 + ((int) ((double) t * j) << 16) + ((int) ((double) u * j) << 8) + (int) ((double) v * j));
        String w = MouseManager.getLeftClickCounter() + " CPS";
        String x = MouseManager.getRightClickCounter() + " CPS";
        int y = this.b.fontRendererObj.getStringWidth(w);
        int z = this.b.fontRendererObj.getStringWidth(x);
        boolean a2 = this.c == 0;
        int b2 = a2 ? y : z;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.b.fontRendererObj.drawString(a2 ? w : x, (o + this.d + 17) * 2 - b2 / 2, (p + this.e + 14) * 2,
                -16777216 + ((int) (255.0D * j) << 16) + ((int) (255.0D * j) << 8) + (int) (255.0D * j));
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }
}
