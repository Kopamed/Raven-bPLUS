package keystrokesmod.client.clickgui.raven;

import keystrokesmod.client.clickgui.raven.settings.BindComponent;
import keystrokesmod.client.clickgui.raven.settings.RangeSliderComponent;
import keystrokesmod.client.clickgui.raven.settings.SliderComponent;
import keystrokesmod.client.clickgui.theme.Theme;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class ModuleComponent extends Component {
    private final CategoryComponent categoryComponent;
    private final Module module;
    private boolean opened = false;

    public ModuleComponent(Module mod, CategoryComponent categoryComponent) {
        this.module = mod;
        this.categoryComponent = categoryComponent;
        for(Setting setting : module.getSettings()){
            this.add(setting.createComponent(this));
        }
        this.add(new BindComponent(this));
        // todo add TOGGLETYPE setting and HUDSHOW SETTING
    }

    @Override
    public void paint(FontRenderer fr) {
        Theme currentTheme = Raven.clickGui.getTheme();
        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) * 0.5;

        Gui.drawRect(
                (int)this.getX(),
                (int)this.getY(),
                (int)(this.getX() + this.getWidth()),
                (int)(this.getY() + this.getHeight()),
                module.isEnabled() ? currentTheme.getAccentColour().getRGB() : currentTheme.getSecondBackgroundColour().getRGB()
        );


        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString(module.getName(), (float)((this.getX() + textMargin) * coordFactor), (float)(textY * coordFactor), module.isEnabled() ? currentTheme.getSecondBackgroundColour().getRGB() : currentTheme.getTextColour().getRGB(), false);
        GL11.glPopMatrix();
        if(opened){
            for(Component component: this.getComponents()){
                if(component.isVisible())
                    component.paint(fr);
            }
        }
    }

    @Override
    public void update(int x, int y) {
        double startX = this.getX();
        double currentY = this.getY() + this.getHeight();

        if(opened) {
            for (Component component : getComponents()) {
                component.setLocation(startX, currentY);
                if(component.isVisible()){
                    component.update(x, y);
                    currentY += component.getHeight();
                }
            }
        }
    }

    @Override
    public void onResize() {
        Theme currentTheme = Raven.clickGui.getTheme();
        this.setSize(categoryComponent.getWidth() - 2, categoryComponent.getHeight() * 0.65);
        for(Component component : this.getComponents()){
            double multiplier = 0.65;
            if(component instanceof SliderComponent || component instanceof RangeSliderComponent)
                multiplier *= 1.65;
            component.setSize(this.getWidth(), this.getHeight() * multiplier);
            component.setColor(currentTheme.getSelectionBackgroundColour()); //todo change this bruh
        }
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        if(opened) {
            for (Component component : this.getComponents()) {
                if(component.isVisible())
                    component.mouseDown(x, y, mb);
            }
        }

        if(mouseOver(x, y) && mb == 0){
            module.toggle();
        } else if(mouseOver(x, y) && mb == 1) {
            this.opened = !opened;
        }

        this.mouseDown = true;
    }

    public double getFullHeight() {
        double totalY = this.getHeight();
        for (Component component : this.getComponents()){
            if(component.isVisible())
                totalY += component.getHeight();
        }

        return totalY;
    }

    public Module getModule(){
        return module;
    }

    @Override
    public boolean isOpened() {
        return opened;
    }

    private boolean hasSettings(){
        return !module.getSettings().isEmpty();
    }
}
