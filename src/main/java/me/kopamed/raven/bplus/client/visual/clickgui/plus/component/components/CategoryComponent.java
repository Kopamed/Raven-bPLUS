package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components;

import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;

public class CategoryComponent extends me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component {
    public ModuleCategory category;
    private boolean opened = false;
    private String name;
    private boolean dragging;

    public CategoryComponent(ModuleCategory category) {
        this(category.name());
        this.category = category;

        for(Iterator<Module> var3 = Raven.client.getModuleManager().getModulesInCategory(category).iterator(); var3.hasNext();) {
            Module mod = (Module) var3.next();
            ModuleComponent b = new ModuleComponent(mod, this);
            this.add(b);
        }
    }

    public CategoryComponent(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean on) {
        this.opened = on;
    }

    @Override
    public void paint(GlyphPageFontRenderer fr) {
        Theme currentTheme = Raven.client.getClickGui().getTheme();
        Gui.drawRect((int)this.getX(),
                (int)this.getY(),
                (int) (this.getX() + this.getWidth()),
                (int) (this.getY() + this.getHeight()),
                currentTheme.getBackgroundColour().getRGB());

        float textmargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.getFontHeight();
        double coordFactor = 1/scaleFactor;
        String status = this.opened ? "-" : "+";

        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        double retard = this.getX() + this.getWidth() - textmargin - fr.getStringWidth(status) * scaleFactor;
        fr.drawString(status, (float)(retard * coordFactor), (float)((this.getY() + this.getHeight() * 0.2) * coordFactor), currentTheme.getTextColour().getRGB(), false);
        fr.drawString(getName(), (float)((this.getX() + textmargin) * coordFactor), (float)((this.getY() + this.getHeight() * 0.2) * coordFactor), currentTheme.getTextColour().getRGB(), false);
        GL11.glPopMatrix();

        if(opened){
            double renderY = this.getY() + this.getHeight();
            for(Component component: this.getComponents()){
                if(component instanceof ModuleComponent){
                    component.setLocation(this.getX() + 1, renderY);
                    ModuleComponent moduleComponent = (ModuleComponent) component;
                    moduleComponent.paint(fr);
                    renderY += component.getHeight();
                }
            }
        }

    }

    @Override
    public void update(int x, int y) {
        PlusGui clickGui = Raven.client.getClickGui();;
        if(this.mouseOver(x, y)){
            clickGui.setTooltip("Left Click to see the modules, CTRL and drag to move", this);
        } else if(!this.mouseOver(x, y) && clickGui.getTooltipSetter() == this){
            clickGui.clearTooltip();
        }
        if(opened) {
            super.update(x, y);
        }
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        this.mouseDown = true;

        if(draggable && mouseOver(x, y) && Keyboard.isKeyDown(29)){
            this.dragging = true;
            this.mouseStartDragX = x;
            this.mouseStartDragY = y;
            this.windowStartDragX = getX();
            this.windowStartDragY = getY();
        }

        if(opened){
            for(Component component : this.getComponents()){
                component.mouseDown(x, y, mb);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y, int mb) {
        this.mouseDown = false;
        if(draggable)
            this.dragging = false;

        if(opened) {
            for (Component component : this.getComponents()) {
                component.mouseReleased(x, y, mb);
            }
        }
    }
}
