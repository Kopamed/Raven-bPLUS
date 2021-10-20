package me.kopamed.lunarkeystrokes.keystroke.ui.component.components;

import me.kopamed.lunarkeystrokes.keystroke.setting.settings.SliderSetting;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {
    private int barHeight = 4;
    private Color sliderColour = new Color(73, 99, 246);
    private Color backgroundBarColour = new Color(64, 69, 82);

    private int barWidth, sliderWidth;
    private SliderSetting sliderSetting;

    private int margin = 6;
    private int maxValLen;

    private boolean helping = false;

    public Slider(int x, int y, int width, int height, SliderSetting sliderSetting, double textSize){
        super(x, y, width, height, textSize);
        this.sliderSetting = sliderSetting;
        this.maxValLen = (int)(getFontRender().getStringWidth(sliderSetting.getMax() + "") * textSize);
    }

    public void draw(){
        int baseX = this.getX() + maxStringLen + margin*2 + maxValLen;
        int baseY = this.getY() + (this.getHeight() - barHeight) / 2;
        int barCurrentWidth = this.getWidth() + this.getX() - baseX;
        int sliderCurrentWidth = (int)(barCurrentWidth
                * ((this.sliderSetting.getValue() - this.sliderSetting.getMin())
                / (this.sliderSetting.getMax() - this.sliderSetting.getMin())));

        Gui.drawRect(baseX, baseY, this.getWidth() + this.getX(), baseY + barHeight, backgroundBarColour.getRGB());
        Gui.drawRect(baseX, baseY, baseX + sliderCurrentWidth, baseY + barHeight, sliderColour.getRGB());

        GL11.glPushMatrix();
        GL11.glScaled(this.getTextSize(), this.getTextSize(), this.getTextSize());
        double fontHeight = getFontRender().FONT_HEIGHT * this.getTextSize();
        Minecraft.getMinecraft().fontRendererObj.drawString(this.sliderSetting.getName(), (int)(this.getX() * (1/this.getTextSize())), (int)((this.getY() + (this.getHeight() - fontHeight)/2) * (1/this.getTextSize())), 0xffffffff);
        Minecraft.getMinecraft().fontRendererObj.drawString(this.sliderSetting.getValue() + "", (int)((this.getX() + maxStringLen + margin) * (1/this.getTextSize())), (int)((this.getY() + (this.getHeight() - fontHeight)/2) * (1/this.getTextSize())), 0xffffffff);
        GL11.glPopMatrix();
    }

    public void backgroundProcess(int x, int y){
        super.backgroundProcess(x, y);

        double mousePressedAt = x;

        int baseX = this.getX() + maxStringLen + margin*2 + maxValLen;
        int endX = this.getWidth() + this.getX();

        //zSystem.out.println(isMouseOverSlider(x, y));
        if((isMouseOverSlider(x, y) || helping) && this.mouseDown){
            if(!helping)
                this.helping = true;

            //System.out.println("Over");

            if(mousePressedAt <= baseX){
                this.sliderSetting.setValue(this.sliderSetting.getMin());
            } else if(mousePressedAt >= endX){
                this.sliderSetting.setValue(this.sliderSetting.getMax());
            } else {
                double posOnSlider = r((mousePressedAt - baseX)
                        / (endX - baseX)
                        * (this.sliderSetting.getMax() - this.sliderSetting.getMin()) + this.sliderSetting.getMin(), 2);
                System.out.println(posOnSlider + " " +  this.sliderSetting.getMin() + " " + posOnSlider);
                this.sliderSetting.setValue(posOnSlider);
            }

        }else if(!mouseDown && helping){
            helping = false;
        }
    }

    private boolean isMouseOverSlider(int x, int y) {
        int baseX = this.getX() + maxStringLen + margin*2 + maxValLen;
        int baseY = this.getY();
        int endX = this.getWidth() + this.getX();
        int endY = baseY + this.getHeight();
        //System.out.println(baseX + " " + x + " " + endX + " " + baseY + " " + y + " " + endY);
        return (x >= baseX && x <= endX && y>= baseY && y <= endY);
    }

    private static double r(double v, int p) {
        if (p < 0) {
            return 0.0D;
        } else {
            BigDecimal bd = new BigDecimal(v);
            bd = bd.setScale(p, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }
}
