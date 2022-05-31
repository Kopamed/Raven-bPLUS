package keystrokesmod.client.clickgui.raven.settings;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.ModuleComponent;
import keystrokesmod.client.clickgui.theme.Theme;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class SliderComponent extends Component {
    private final SliderSetting numberSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;
    private boolean draggingThumb = false;

    public SliderComponent(SliderSetting numberSetting, ModuleComponent moduleComponent){
        this.numberSetting = numberSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void paint(FontRenderer fr) {
        Theme theme = Raven.clickGui.getTheme();

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + this.getWidth()),
                (int)(this.getY() + this.getHeight()),
                theme.getSelectionBackgroundColour().getRGB()
        );

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + Raven.clickGui.barWidth),
                (int)(this.getY() + this.getHeight()),
                theme.getAccentColour().getRGB()
        );

        double ogSize = this.getHeight() / 1.65;
        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = ogSize * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;

        double barSpace = this.getHeight() - ogSize;
        int barHeight = 1; // pixels
        int barY = (int)(this.getY() + ogSize + (int)(barSpace - barHeight) * 0.5);

        String parent = numberSetting.getName() + ":";
        String value = numberSetting.getInput() + "";

        double text1Y = this.getY() + (ogSize - desiredTextSize) * 0.5;
        double parentX = (this.getX() + textMargin);

        int thumbSize = barHeight * 3;
        int barLength = (int)(this.getWidth() - textMargin * 2);
        double barWidth = ((numberSetting.getInput() - numberSetting.getMin()) /
                (numberSetting.getMax() - numberSetting.getMin())) *
                barLength;

        Gui.drawRect( //drawing the background
                (int)(this.getX() + textMargin),
                barY,
                (int)(this.getX() + textMargin + barLength),
                barY + barHeight,
                theme.getSelectionForegroundColour().getRGB()
        );

        Gui.drawRect(
                (int)(this.getX() + textMargin),
                barY,
                (int)(this.getX() + textMargin + barWidth),
                barY + barHeight,
                theme.getAccentColour().getRGB()
        );

        Gui.drawRect(
                (int)(this.getX() + textMargin + barWidth - thumbSize * 0.5),
                (int)(barY - thumbSize * 0.5),
                (int)(this.getX() + textMargin + barWidth + thumbSize * 0.5),
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
        float textMargin = (float)this.getWidth() * 0.0625f;
        int barLength = (int)(this.getWidth() - textMargin * 2);

        if(draggingThumb){
            double mousePosX = Math.min(this.getX() + this.getWidth() - textMargin, Math.max(this.getX() + textMargin, x)) - (this.getX() + textMargin);
            double percentage = mousePosX / barLength;
            numberSetting.setValue(((numberSetting.getMax() - numberSetting.getMin() )* percentage) + numberSetting.getMin());
        }
        super.update(x, y);
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
    }
}
