package me.kopamed.lunarkeystrokes.keystroke.ui.component;

import me.kopamed.lunarkeystrokes.utils.CoolDown;
import me.kopamed.lunarkeystrokes.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class KeyStrokeL {
    public int width, height;
    private KeyBinding keyBinding;
    private final Minecraft mc = Minecraft.getMinecraft();

    private Timer cd;
    private boolean downRegistered = false;
    private Tmode tmode = Tmode.NONE;

    private float cachedTime = 0;

    public KeyStrokeL(int width, int height, KeyBinding keyBinding){
        this.width = width;
        this.height = height;
        this.keyBinding = keyBinding;
    }

    public void draw(int x, int y, float transitionTime, Color txtColor, Color txtPressedColor, Color bgColor, Color bgPressedColor, boolean textShadow, double scale){
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);


        boolean down = this.keyBinding.isKeyDown();
        if(this.cachedTime != transitionTime){
            this.cd = new Timer(transitionTime);
            this.cachedTime = transitionTime;
        }


        if(down && !downRegistered){ // starting the transtiotn coloura (not to be confused with transgendered people)
            this.downRegistered = true;
            cd.start();
            tmode = Tmode.TOPRESSED;
        } else if (!down && downRegistered){// starting the transtiotn coloura (not to be confused with transgendered people)
            downRegistered = false;
            cd.start();
            tmode = Tmode.TORELEASED;
        } else if(cd.finished()){
            tmode = Tmode.NONE;
        }

        int red = down ? bgPressedColor.getRed() : bgColor.getRed();
        int green = down ? bgPressedColor.getGreen() : bgColor.getGreen();
        int blue = down ? bgPressedColor.getBlue() : bgColor.getBlue();
        final int transparecy = bgColor.getAlpha();

        // now i have to compile these colours into a Color class
        switch (tmode){
            case TOPRESSED:
                red = cd.getValueInt(bgColor.getRed(), bgPressedColor.getRed(), 1);
                green = cd.getValueInt(bgColor.getGreen(), bgPressedColor.getGreen(), 1);
                blue = cd.getValueInt(bgColor.getBlue(), bgPressedColor.getBlue(), 1);
                break;

            case TORELEASED:
                red = cd.getValueInt(bgPressedColor.getRed(), bgColor.getRed(), 1);
                green = cd.getValueInt(bgPressedColor.getGreen(), bgColor.getGreen(), 1);
                blue = cd.getValueInt(bgPressedColor.getBlue(), bgColor.getBlue(),1);
                break;
        }

        //System.out.println(red + " " + green + " " + blue);


        Gui.drawRect(x, y, x+this.width, y+this.height, new Color(red, green, blue, transparecy).getRGB());

        red = down ? txtPressedColor.getRed() : txtColor.getRed();
        green = down ? txtPressedColor.getGreen() : txtColor.getGreen();
        blue = down ? txtPressedColor.getBlue() : txtColor.getBlue();

        // now i have to compile these colours into a Color class
        switch (tmode){
            case TOPRESSED:
                red = cd.getValueInt(txtColor.getRed(), txtPressedColor.getRed(), 1);
                green = cd.getValueInt(txtColor.getGreen(), txtPressedColor.getGreen(), 1);
                blue = cd.getValueInt(txtColor.getBlue(), txtPressedColor.getBlue(), 1);
                break;

            case TORELEASED:
                red = cd.getValueInt(txtPressedColor.getRed(), txtColor.getRed(), 1);
                green = cd.getValueInt(txtPressedColor.getGreen(), txtColor.getGreen(), 1);
                blue = cd.getValueInt(txtPressedColor.getBlue(), txtColor.getBlue(),1);
                break;
        }

        drawCenteredString(Keyboard.getKeyName(this.keyBinding.getKeyCode()), x + this.width/2, y + this.height/2, new Color(red, green, blue, transparecy).getRGB(), textShadow);
        GL11.glPopMatrix();
    }

    private void drawCenteredString(String text, int x, int y, int color, boolean textShadow){
        if(textShadow)
            mc.fontRendererObj.drawString(text, x - mc.fontRendererObj.getStringWidth(text)/2, y - mc.fontRendererObj.FONT_HEIGHT/2, color);
        else
            mc.fontRendererObj.drawStringWithShadow(text, x - mc.fontRendererObj.getStringWidth(text)/2, y - mc.fontRendererObj.FONT_HEIGHT/2, color);
    }
}
