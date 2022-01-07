package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class RangeSliderComponent extends Component {
    private final RangeSetting rangeSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;
    private boolean draggingThumb;
    private DragType dragType = DragType.NONE;

    public RangeSliderComponent(RangeSetting rangeSetting, ModuleComponent moduleComponent){
        this.rangeSetting = rangeSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }

    @Override
    public boolean isVisible() {
        return rangeSetting.isVisible();
    }

    @Override
    public void paint(FontRenderer fr) {
        Theme theme = Raven.client.getClickGui().getTheme();
        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + this.getWidth()),
                (int)(this.getY() + this.getHeight()),
                theme.getSelectionBackgroundColour().getRGB()
        );
        double ogSize = this.getHeight() / 1.65;
        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = ogSize * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;

        double barSpace = this.getHeight() - ogSize;
        int barHeight = 1; // pixels
        int barY = (int)(this.getY() + ogSize + (int)(barSpace - barHeight) * 0.5);

        String parent = rangeSetting.getName() + ":";
        String value = rangeSetting.getInputMin() + ", " + rangeSetting.getInputMax();

        double text1Y = this.getY() + (ogSize - desiredTextSize) * 0.5;
        double parentX = (this.getX() + textMargin);

        int thumbSize = barHeight * 3;
        int barLength = (int)(this.getWidth() - textMargin * 2);
        double barStart = this.getX() // x
                + textMargin // base margin
                + ((rangeSetting.getInputMin() - rangeSetting.getMin())// finding out the percentage of the bar
                / (rangeSetting.getMax()
                - rangeSetting.getMin()))
                * barLength;

        double barWidth = ((rangeSetting.getInputMax() - rangeSetting.getInputMin()) /
                (rangeSetting.getMax() - rangeSetting.getMin())) *
                barLength;

        Gui.drawRect( //drawing the background
                (int)(this.getX() + textMargin),
                barY,
                (int)(this.getX() + textMargin + barLength),
                barY + barHeight,
                theme.getSelectionForegroundColour().getRGB()
        );

        Gui.drawRect(
                (int)(barStart),
                barY,
                (int)(barStart + barWidth),
                barY + barHeight,
                theme.getAccentColour().getRGB()
        );

        Gui.drawRect(
                (int)(barStart - thumbSize * 0.5),
                (int)(barY - thumbSize * 0.5),
                (int)(barStart + thumbSize * 0.5),
                (int)(barY + barHeight + thumbSize * 0.5),
                theme.getSecondBackgroundColour().getRGB()
        );

        Gui.drawRect(
                (int)(barStart + barWidth - thumbSize * 0.5),
                (int)(barY - thumbSize * 0.5),
                (int)(barStart + barWidth + thumbSize * 0.5),
                (int)(barY + barHeight + thumbSize * 0.5),
                theme.getSecondBackgroundColour().getRGB()
        );


        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString(
                parent,
                (float)(parentX * coordFactor),
                (float)(text1Y * coordFactor),
                theme.getTextColour().getRGB(),
                false
        );
        double fatFuck = fr.getStringWidth(parent + " ") * scaleFactor;
        fr.drawString(
                value,
                (float)((parentX + fatFuck)  * coordFactor),
                (float)(text1Y * coordFactor),
                theme.getAccentColour().getRGB(),
                false
        );
        GL11.glPopMatrix();


        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    @Override
    public void update(int x, int y) {
        super.update(x, y);
        float textMargin = (float)this.getWidth() * 0.0625f;
        int barLength = (int)(this.getWidth() - textMargin * 2);

        double barMinX = this.getX() // x
                + textMargin // base margin
                + ((rangeSetting.getInputMin()  - rangeSetting.getMin()) // finding out the percentage of the bar
                / (rangeSetting.getMax()
                - rangeSetting.getMin()))
                * barLength;

        double barWidth = ((rangeSetting.getInputMax() - rangeSetting.getInputMin()) /
                (rangeSetting.getMax() - rangeSetting.getMin())) *
                barLength;

        double barMaxX = barWidth + barMinX;

        if(draggingThumb){
            // calculate to which thumb the mouse is closest to
            if(dragType == DragType.NONE){
                // first frame before the drag
                double distanceToMin = x - barMinX;
                double distanceToMax = barMaxX - x;
                if(distanceToMin < distanceToMax){
                    this.dragType = DragType.MIN;
                } else {
                    this.dragType = DragType.MAX;
                }
            }

            if(dragType == DragType.MIN){
                double mousePosX = Math.min(barMaxX, Math.max(this.getX() + textMargin, x)) - (this.getX() + textMargin);
                double percentage = mousePosX / barLength;
                rangeSetting.setValueMin(percentage * (rangeSetting.getMax() - rangeSetting.getMin()) + rangeSetting.getMin());
            }else if(dragType == DragType.MAX){
                double mousePosX = Math.min(this.getX() + this.getWidth() - textMargin, Math.max(barMinX, x)) - (this.getX() + textMargin);
                double percentage = mousePosX / barLength;
                rangeSetting.setValueMax(percentage * (rangeSetting.getMax() - rangeSetting.getMin())  + rangeSetting.getMin());
            }
        }


    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        super.mouseDown(x, y, mb);
        if(mouseOver(x, y))
            this.draggingThumb = true;

    }

    @Override
    public void mouseReleased(int x, int y, int mb) {
        super.mouseReleased(x, y, mb);
        this.draggingThumb = false;
        this.dragType = DragType.NONE;
    }

    public enum DragType {
        MIN,
        MAX,
        NONE
    }


}
