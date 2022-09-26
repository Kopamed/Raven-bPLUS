//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.keystroke;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeyStrokeKeyRenderer {
    private final Minecraft a = Minecraft.getMinecraft();
    private final KeyBinding keyBinding;
    private final int c;
    private final int d;
    private boolean e = true;
    private long f;

    public KeyStrokeKeyRenderer(KeyBinding i, int j, int k) {
        this.keyBinding = i;
        this.c = j;
        this.d = k;
    }

    public void renderKey(int l, int m, int color) {
        boolean o = this.keyBinding.isKeyDown();
        String p = Keyboard.getKeyName(this.keyBinding.getKeyCode());
        if (o != this.e) {
            this.e = o;
            this.f = System.currentTimeMillis();
        }

        double h = 1.0D;
        int g = 255;
        if (o) {
            g = Math.min(255, (int) (2L * (System.currentTimeMillis() - this.f)));
            h = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.f) / 20.0D);
        } else {
            g = Math.max(0, 255 - (int) (2L * (System.currentTimeMillis() - this.f)));
            h = Math.min(1.0D, (double) (System.currentTimeMillis() - this.f) / 20.0D);
        }

        int q = color >> 16 & 255;
        int r = color >> 8 & 255;
        int s = color & 255;
        int c = (new Color(q, r, s)).getRGB();
        net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 22, m + this.d + 22,
                2013265920 + (g << 16) + (g << 8) + g);
        if (KeyStroke.outline) {
            net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 22, m + this.d + 1, c);
            net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d + 21, l + this.c + 22, m + this.d + 22, c);
            net.minecraft.client.gui.Gui.drawRect(l + this.c, m + this.d, l + this.c + 1, m + this.d + 22, c);
            net.minecraft.client.gui.Gui.drawRect(l + this.c + 21, m + this.d, l + this.c + 22, m + this.d + 22, c);
        }

        this.a.fontRendererObj.drawString(p, l + this.c + 8, m + this.d + 8,
                -16777216 + ((int) ((double) q * h) << 16) + ((int) ((double) r * h) << 8) + (int) ((double) s * h));
    }
}
