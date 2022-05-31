package keystrokesmod.client.clickgui.raven.settings;

import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.clickgui.raven.ModuleComponent;
import keystrokesmod.client.clickgui.theme.Theme;
import keystrokesmod.client.main.Raven;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class BindComponent extends Component {
    private final ModuleComponent moduleComponent;
    private final String bindText = "Bind: ";
    private boolean listening = false;

    public BindComponent(ModuleComponent moduleComponent){
        this.moduleComponent = moduleComponent;
    }

    @Override
    public void paint(FontRenderer fr) {
        Theme currentTheme = Raven.clickGui.getTheme();

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + this.getWidth()),
                (int)(this.getY() + this.getHeight()),
                currentTheme.getSelectionBackgroundColour().getRGB()
        );

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + Raven.clickGui.barWidth),
                (int)(this.getY() + this.getHeight()),
                currentTheme.getAccentColour().getRGB()
        );

        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) * 0.5;
        String bindedTo = listening ? "Press ESC to unbind" : moduleComponent.getModule().getBindAsString();


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
            listening = false;
            moduleComponent.getModule().setbind(keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    public boolean isListening(){
        return listening;
    }
}
