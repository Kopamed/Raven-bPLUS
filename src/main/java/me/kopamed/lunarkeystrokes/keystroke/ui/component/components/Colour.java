package me.kopamed.lunarkeystrokes.keystroke.ui.component.components;

import me.kopamed.lunarkeystrokes.keystroke.setting.settings.ColourSetting;
import me.kopamed.lunarkeystrokes.keystroke.ui.component.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Colour extends Component {
    private ColourSetting colourSetting;
    private Color barColor = new Color(73, 99, 246);
    private Color backgroundBarColour = new Color(64, 69, 82);

    private double barWidth;
    private double sliderWidth1, sliderWidth2, sliderWidth3;
    public final int barMargin = 6;
    public final int barHeight = 4;
    public final int colourSize = barHeight;
    private Helping helping;

    public Colour (int x, int y, int width, int height, ColourSetting colourSetting, double textSize){
        super(x, y, width, height, textSize);
        this.colourSetting = colourSetting;

        this.barWidth = (width - maxStringLen - colourSize - barMargin * 5)/3;

        this.backgroundProcess(-99, 0);
        this.helping = Helping.NONE;
    }

    public void draw(){
        /*for(int i = 0; i < 3; i++){
            Gui.drawRect(this.getX() + maxStringLen + this.barWidth + this.barMargin * i, this.getY(), this.getX() + maxStringLen + this.barWidth + this.barMargin * (i == 0 ? 0 : i-1 ), this.getY() + this.barHeight, backgroundBarColour.getRGB());
        }*/
        int baseX = this.getX() + maxStringLen + barMargin;
        Gui.drawRect(baseX, this.getY(), baseX + colourSize, this.getY() + this.barHeight, this.colourSetting.getRGB().getRGB());
        baseX += colourSize;
        Gui.drawRect(baseX + barMargin, this.getY(), baseX + barMargin + (int)barWidth, this.getY() + this.barHeight, backgroundBarColour.getRGB());
        Gui.drawRect(baseX + barMargin*2 + (int)barWidth, this.getY(), baseX + barMargin*2 + (int)barWidth*2, this.getY() + this.barHeight, backgroundBarColour.getRGB());
        Gui.drawRect(baseX + barMargin*3 + (int)barWidth*2, this.getY(), baseX + barMargin*3 + (int)barWidth*3, this.getY() + this.barHeight, backgroundBarColour.getRGB());

        Gui.drawRect(baseX + barMargin, this.getY(), baseX + barMargin + (int)this.sliderWidth1, this.getY() + this.barHeight, new Color(this.colourSetting.getRed(), 0, 0).getRGB());
        Gui.drawRect(baseX + barMargin*2 + (int)barWidth, this.getY(), baseX + barMargin*2 + (int)barWidth + (int)this.sliderWidth2, this.getY() + this.barHeight, new Color(0, this.colourSetting.getGreen(), 0).getRGB());
        Gui.drawRect(baseX + barMargin*3 + (int)barWidth*2, this.getY(), baseX + barMargin*3 + (int)barWidth*2 + (int)this.sliderWidth3, this.getY() + this.barHeight, new Color(0, 0, this.colourSetting.getBlue()).getRGB());




        GL11.glPushMatrix();
        GL11.glScaled(this.getTextSize(), this.getTextSize(), this.getTextSize());
        Minecraft.getMinecraft().fontRendererObj.drawString(this.colourSetting.getName(), (int)(this.getX() * (1/this.getTextSize())), (int)(this.getY() * (1/this.getTextSize())), 0xffffffff);
        GL11.glPopMatrix();
    }

    public void backgroundProcess(int x, int y){
        super.backgroundProcess(x, y);



        double mousePressedAt = x;

        if((this.isMouseOver(x, y) || this.helping!= Helping.NONE) && mouseDown){
            if(mouseOverSlider1(x, y) || helping == Helping.RED){
                if(this.helping == Helping.NONE) this.helping = Helping.RED;

                if(this.helping == Helping.RED){
                    int baseX = this.getX() + maxStringLen + barMargin + colourSize;
                    int startX = baseX + barMargin, endX = baseX + barMargin + (int)barWidth;

                    if(mousePressedAt <= startX){
                        this.colourSetting.setRed(0);

                    } else if(mousePressedAt >= endX){

                        this.colourSetting.setRed(this.colourSetting.maxPos);
                    } else {
                        int posOnSlider = (int) Math.round((mousePressedAt - startX)
                                / (endX - startX)
                                * this.colourSetting.maxPos);
                        this.colourSetting.setRed(posOnSlider);

                    }
                }

            }

            if(mouseOverSlider2(x, y) || helping == Helping.GREEN){
                if(this.helping ==Helping.NONE) this.helping = Helping.GREEN;

                if(this.helping == Helping.GREEN){
                    int baseX = this.getX() + maxStringLen + barMargin + colourSize;
                    int startX = baseX + barMargin*2 + (int)barWidth, endX = baseX + barMargin*2 + (int)barWidth*2;
                    if(mousePressedAt <= startX){
                        this.colourSetting.setGreen(0);
                    } else if(mousePressedAt >= endX){
                        this.colourSetting.setGreen(this.colourSetting.maxPos);
                    } else {
                        int posOnSlider = (int) Math.round((mousePressedAt - startX)
                                / (endX - startX)
                                * this.colourSetting.maxPos);
                        this.colourSetting.setGreen(posOnSlider);
                    }
                }
            }

            if(mouseOverSlider3(x, y) || helping == Helping.BLUE){
                if(this.helping ==Helping.NONE) this.helping = Helping.BLUE;

                if(this.helping == Helping.BLUE){
                    int baseX = this.getX() + maxStringLen + barMargin + colourSize;
                    int startX = baseX + barMargin*3 + (int)barWidth*2, endX = baseX + barMargin*3 + (int)barWidth*3;

                    if(mousePressedAt <= startX){
                        this.colourSetting.setBlue(0);
                    } else if(mousePressedAt >= endX){
                        this.colourSetting.setBlue(this.colourSetting.maxPos);
                    } else {
                        int posOnSlider = (int) Math.round((mousePressedAt - startX)
                                / (endX - startX)
                                * this.colourSetting.maxPos);
                        this.colourSetting.setBlue(posOnSlider);

                    }
                }
            }
        } else {
            this.helping = Helping.NONE;
        }

        double mousePosOnSlider1 = Math.min(this.getWidth() + maxStringLen, Math.max(0, x - this.getX()));





        this.sliderWidth1 = (double)(this.barWidth) //the bar width of the lsider
                * ((double) (this.colourSetting.getRed()) //bar width in int terms
                / (this.colourSetting.maxPos + 1D));//divided by total width of grey bar
        //divided by total width of grey bar

        this.sliderWidth2 = (double)(this.barWidth)//the bar width of the lsiderç
                * (((double)this.colourSetting.getGreen()) //bar width in int terms
                / (this.colourSetting.maxPos + 1D));//divided by total width of grey bar
        //divided by total width of grey bar

        this.sliderWidth3 = (double)(this.barWidth) //the bar width of the lsider
                * (((double)this.colourSetting.getBlue()) //bar width in int terms
                / (this.colourSetting.maxPos + 1D));//divided by total width of grey bar
        //divided by total width of grey bar
    }

    private boolean mouseOverSlider3(int x, int y) {
        int baseX = this.getX() + maxStringLen + barMargin + colourSize;
        int startX = baseX + barMargin*3 + (int)barWidth*2, endX = baseX + barMargin*3 + (int)barWidth*3;
        int startY = this.getY(), endY =  this.getY() + this.barHeight;
        if(x >= startX && x <= endX && y >= startY && y <= endY)
            return true;
        return false;
    }

    private boolean mouseOverSlider2(int x, int y) {
        int baseX = this.getX() + maxStringLen + barMargin + colourSize;
        int startX = baseX + barMargin*2 + (int)barWidth, endX = baseX + barMargin*2 + (int)barWidth*2;
        int startY = this.getY(), endY =  this.getY() + this.barHeight;
        if(x >= startX && x <= endX && y >= startY && y <= endY)
            return true;
        return false;
    }

    private boolean mouseOverSlider1(int x, int y) {
        int baseX = this.getX() + maxStringLen + barMargin + colourSize;
        int startX = baseX + barMargin, endX = baseX + barMargin + (int)barWidth;
        int startY = this.getY(), endY =  this.getY() + this.barHeight;
        if(x >= startX && x <= endX && y >= startY && y <= endY)
            return true;
        return false;
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

    public enum Helping {
        RED,
        GREEN,
        BLUE,
        NONE;
    }
}
