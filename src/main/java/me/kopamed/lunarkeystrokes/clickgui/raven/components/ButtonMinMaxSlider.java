package me.kopamed.lunarkeystrokes.clickgui.raven.components;

import me.kopamed.lunarkeystrokes.clickgui.raven.Component;
import me.kopamed.lunarkeystrokes.module.setting.settings.RangeSlider;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ButtonMinMaxSlider extends Component {
    private final RangeSlider doubleSlider;
    private final ButtonModule module;
    private double barWidth;
    private double blankWidth;
    private int sliderStartX;
    private int sliderStartY;
    private int moduleStartY;
    private boolean mouseDown, inMotion;
    private Helping mode = Helping.NONE;

    private final int boxMargin = 4;
    private final int boxHeight = 4;
    private final int textSize = 11;

    public ButtonMinMaxSlider(RangeSlider doubleSlider, ButtonModule module, int moduleStartY){
        this.doubleSlider = doubleSlider;
        this.module = module;
        this.sliderStartX = this.module.category.getX() + boxMargin;
        this.sliderStartY = moduleStartY + module.category.getY();
        this.moduleStartY = moduleStartY;
    }

    public void draw(){
        //drawing the grey box lol
        net.minecraft.client.gui.Gui.drawRect(this.module.category.getX() + boxMargin, this.module.category.getY() + this.moduleStartY + textSize, this.module.category.getX() - boxMargin + this.module.category.getWidth(), this.module.category.getY() + this.moduleStartY + textSize + boxHeight, -12302777);
        int startToDrawFrom = this.module.category.getX() + boxMargin + (int) this.blankWidth;
        int finishDrawingAt = startToDrawFrom + (int)this.barWidth;
        int middleThing = (int)Utils.Java.round(this.barWidth/2, 0) + this.module.category.getX() + (int) this.blankWidth + boxMargin - 1;

        //drwing the main colourded bar
        net.minecraft.client.gui.Gui.drawRect(startToDrawFrom, this.module.category.getY() + this.moduleStartY + textSize, finishDrawingAt, this.module.category.getY() + this.moduleStartY + textSize + boxHeight, Utils.Client.astolfoColorsDraw(14, 10));
        net.minecraft.client.gui.Gui.drawRect(middleThing, this.module.category.getY() + this.moduleStartY + textSize - 1, middleThing+(middleThing%2==0? 2:1), this.module.category.getY() + this.moduleStartY + textSize + boxHeight + 1, 0xff1D1D1F);
        //drawing le text
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.doubleSlider.getName() + ": " + this.doubleSlider.getInputMin() + ", " + this.doubleSlider.getInputMax(), (float)((int)((float)(this.module.category.getX() + 4) * 2.0F)), (float)((int)((float)(this.module.category.getY() + this.moduleStartY + 3) * 2.0F)), -1);
        GL11.glPopMatrix();
    }

    public void setModuleStartAt(int posY) {
        this.moduleStartY = posY;
    }

    public void compute(int mousePosX, int mousePosY){
        this.sliderStartY = this.module.category.getY() + this.moduleStartY;
        this.sliderStartX = this.module.category.getX() + boxMargin;

        double mousePressedAt = Math.min(this.module.category.getWidth() - boxMargin*2 /*we make it so that the mouse press HAS to be inside the box*/, Math.max(0, mousePosX - this.sliderStartX));
        this.blankWidth = (double)(this.module.category.getWidth() - boxMargin*2) //the bar width in pixels
                * (this.doubleSlider.getInputMin() - this.doubleSlider.getMin()) //bar width in int terms
                / (this.doubleSlider.getMax() - this.doubleSlider.getMin());
        this.barWidth = (double)(this.module.category.getWidth() - boxMargin*2) //the bar width of the lsider
                * (this.doubleSlider.getInputMax() - this.doubleSlider.getInputMin()) //bar width in int terms
                / (this.doubleSlider.getMax() - this.doubleSlider.getMin());//divided by total width of grey bar
        //divided by total width of grey bar



      //  if(this.module.category.inUse && this.mode==Helping.NONE){
           if(this.mouseDown) {

               if (mousePressedAt > blankWidth + barWidth / 2 || mode == Helping.MAX) {
                   //manipulate max slider
                   if (this.mode == Helping.NONE) this.mode = Helping.MAX;
                   if(this.mode == Helping.MAX){
                       if (mousePressedAt <= blankWidth) {
                           this.doubleSlider.setValueMax(this.doubleSlider.getInputMin());
                       } else {
                           double n = r(mousePressedAt
                                   / (double) (this.module.category.getWidth() - boxMargin * 2)
                                   * (this.doubleSlider.getMax() - this.doubleSlider.getMin())
                                   + this.doubleSlider.getMin(), 2);
                           this.doubleSlider.setValueMax(n);
                       }
                   }
                   //this.mode = Helping.MAX;
               }

               if (mousePressedAt < blankWidth + barWidth / 2 || mode == Helping.MIN) {
                   //manipulate min slider
                   if (this.mode == Helping.NONE) this.mode = Helping.MIN;
                   if(this.mode == Helping.MIN) {
                       if (mousePressedAt == 0.0D) {
                           this.doubleSlider.setValueMin(this.doubleSlider.getMin());
                       } else if(mousePressedAt >= barWidth + blankWidth){
                           this.doubleSlider.setValueMin(this.doubleSlider.getMax());
                       }else {
                           double n = r(mousePressedAt
                                   / (double) (this.module.category.getWidth() - boxMargin*2)
                                   * (this.doubleSlider.getMax() - this.doubleSlider.getMin())
                                   + this.doubleSlider.getMin(), 2);
                           this.doubleSlider.setValueMin(n);
                       }
                   }
               }
           } else {
               if(mode != Helping.NONE) mode = Helping.NONE;
           }

       // }

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

    public void mouseDown(int x, int y, int b) {
        if (this.u(x, y) && b == 0 && this.module.po) {
            this.mouseDown = true;
        }

        if (this.i(x, y) && b == 0 && this.module.po) {
            this.mouseDown = true;
        }

    }

    public void mouseReleased(int x, int y, int m) {
        this.mouseDown = false;
    }

    public boolean u(int x, int y) {
        return x > this.sliderStartX && x < this.sliderStartX + this.module.category.getWidth() / 2 + 1 && y > this.sliderStartY && y < this.sliderStartY + 16;
    }

    public boolean i(int x, int y) {
        return x > this.sliderStartX + this.module.category.getWidth() / 2 && x < this.sliderStartX + this.module.category.getWidth() && y > this.sliderStartY && y < this.sliderStartY + 16;
    }

    public enum Helping {
        MIN,
        MAX,
        NONE;
    }
}
