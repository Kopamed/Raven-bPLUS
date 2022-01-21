package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class DescriptionComponent extends Component {
    private final DescriptionSetting descriptionSetting;
    private final ModuleComponent moduleComponent;
    private final Module module;

    public DescriptionComponent(DescriptionSetting descriptionSetting, ModuleComponent moduleComponent){
        this.descriptionSetting = descriptionSetting;
        this.moduleComponent = moduleComponent;
        this.module = moduleComponent.getModule();
    }

    @Override
    public boolean isVisible() {
        return descriptionSetting.canShow();
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

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + PlusGui.barWidth),
                (int)(this.getY() + this.getHeight()),
                theme.getAccentColour().getRGB()
        );

        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) * 0.5;

        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString(
                descriptionSetting.getDesc(),
                (float)((this.getX() + textMargin) * coordFactor),
                (float)(textY * coordFactor),
                theme.getContrastColour().getRGB(),
                false
        );
        GL11.glPopMatrix();

        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }
}
