package keystrokesmod.client.clickgui.raven.settings;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.ModuleComponent;
import keystrokesmod.client.clickgui.theme.Theme;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class ComboComponent extends Component {
    private final ModuleComponent moduleComponent;
    private final Module module;
    private final ComboSetting comboSetting;

    public ComboComponent(ComboSetting comboSetting, ModuleComponent moduleComponent){
        this.comboSetting = comboSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
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

        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) * 0.5;
        String parent = comboSetting.getName() + ":";
        String value = comboSetting.getMode() + "";


        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString(
                parent,
                (float)((this.getX() + textMargin) * coordFactor),
                (float)(textY * coordFactor),
                theme.getTextColour().getRGB(),
                false
        );
        double strLen = fr.getStringWidth(value) * scaleFactor;
        fr.drawString(
                value,
                (float)((this.getX() + this.getWidth() - textMargin - strLen) * coordFactor),
                (float)(textY * coordFactor),
                theme.getAccentColour().getRGB(),
                false
        );
        GL11.glPopMatrix();

        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        if(mouseOver(x, y))
            comboSetting.nextMode();

        super.mouseDown(x, y, mb);
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}