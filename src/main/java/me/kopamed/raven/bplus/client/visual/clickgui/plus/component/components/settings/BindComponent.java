package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.ModuleComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class BindComponent extends Component {
    private final ModuleComponent moduleComponent;
    private final String bindText = "Bind: ";
    private boolean listening = false;

    public BindComponent(ModuleComponent moduleComponent){
        this.moduleComponent = moduleComponent;
    }

    @Override
    public void paint(GlyphPageFontRenderer fr) {
        Theme currentTheme = Raven.client.getClickGui().getTheme();

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + this.getWidth()),
                (int)(this.getY() + this.getHeight()),
                currentTheme.getSelectionBackgroundColour().getRGB()
        );

        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.getFontHeight();
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) * 0.5;
        String bindedTo = listening ? "Press ESC to stop" : moduleComponent.getModule().getBindAsString();


        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        float endText = (float)(this.getX() + this.getWidth() - fr.getStringWidth(bindedTo) * scaleFactor - textMargin);

        fr.drawString(
                bindText,
                (float)((this.getX() + textMargin) * coordFactor),
                (float)(textY * coordFactor),
                currentTheme.getTextColour().getRGB(),
                false
        );

        fr.drawString(
                bindedTo,
                (float)(endText * coordFactor),
                (float)(textY * coordFactor),
                listening ? currentTheme.getContrastColour().getRGB() : currentTheme.getAccentColour().getRGB(),
                false
        );
        GL11.glPopMatrix();

        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        if(mb == 0 && mouseOver(x, y)) {
            this.listening = true;
            moduleComponent.getModule().clearBinds();
        }
        super.mouseDown(x, y, mb);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(listening){
            if(keyCode == 1 || !moduleComponent.getModule().canAddMoreBinds()){
                this.listening = false;
            } else {
                moduleComponent.getModule().addKeyCode(keyCode);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    public boolean isListening(){
        return listening;
    }
}
