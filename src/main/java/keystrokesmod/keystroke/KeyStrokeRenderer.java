package keystrokesmod.keystroke;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import java.awt.*;
import java.io.IOException;

public class KeyStrokeRenderer {
    private static final int[] a = { 16777215, 16711680, 65280, 255, 16776960, 11141290 };
    private final Minecraft mc = Minecraft.getMinecraft();
    private final KeyStrokeKeyRenderer[] b = new KeyStrokeKeyRenderer[4];
    private final KeyStrokeMouse[] c = new KeyStrokeMouse[2];

    public KeyStrokeRenderer() {
        this.b[0] = new KeyStrokeKeyRenderer(this.mc.gameSettings.keyBindForward, 26, 2);
        this.b[1] = new KeyStrokeKeyRenderer(this.mc.gameSettings.keyBindBack, 26, 26);
        this.b[2] = new KeyStrokeKeyRenderer(this.mc.gameSettings.keyBindLeft, 2, 26);
        this.b[3] = new KeyStrokeKeyRenderer(this.mc.gameSettings.keyBindRight, 50, 26);
        this.c[0] = new KeyStrokeMouse(0, 2, 50);
        this.c[1] = new KeyStrokeMouse(1, 38, 50);
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (this.mc.currentScreen != null) {
            if (this.mc.currentScreen instanceof KeyStrokeConfigGui) {
                try {
                    this.mc.currentScreen.handleInput();
                } catch (IOException var3) {
                }
            }

        } else if (this.mc.inGameHasFocus && !this.mc.gameSettings.showDebugInfo) {
            this.renderKeystrokes();
        }
    }

    public void renderKeystrokes() {
        KeyStroke f = KeyStrokeMod.getKeyStroke();
        if (KeyStroke.enabled) {
            int x = KeyStroke.x;
            int y = KeyStroke.y;
            int g = this.getColor(KeyStroke.currentColorNumber);
            boolean h = KeyStroke.showMouseButtons;
            ScaledResolution res = new ScaledResolution(this.mc);
            int width = 74;
            int height = h ? 74 : 50;
            if (x < 0) {
                KeyStroke.x = 0;
                x = KeyStroke.x;
            } else if (x > res.getScaledWidth() - width) {
                KeyStroke.x = res.getScaledWidth() - width;
                x = KeyStroke.x;
            }

            if (y < 0) {
                KeyStroke.y = 0;
                y = KeyStroke.y;
            } else if (y > res.getScaledHeight() - height) {
                KeyStroke.y = res.getScaledHeight() - height;
                y = KeyStroke.y;
            }

            this.drawMovementKeys(x, y, g);
            if (h) {
                this.drawMouseButtons(x, y, g);
            }

        }
    }

    private int getColor(int index) {
        return index == 6
                ? Color.getHSBColor((float) (System.currentTimeMillis() % 3750L) / 3750.0F, 1.0F, 1.0F).getRGB()
                : a[index];
    }

    private void drawMovementKeys(int x, int y, int textColor) {
        KeyStrokeKeyRenderer[] var4 = this.b;
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            KeyStrokeKeyRenderer key = var4[var6];
            key.renderKey(x, y, textColor);
        }

    }

    private void drawMouseButtons(int x, int y, int textColor) {
        KeyStrokeMouse[] var4 = this.c;
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            KeyStrokeMouse button = var4[var6];
            button.n(x, y, textColor);
        }

    }
}
