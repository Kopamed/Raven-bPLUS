package me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.components.*;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ModuleComponent extends Component {
    private final int c1 = (new Color(0, 85, 255)).getRGB();
    private final int c2 = (new Color(154, 2, 255)).getRGB();
    private final int c3 = (new Color(175, 143, 233) ).getRGB();
    private final CategoryComponent categoryComponent;
    public Module module;
    public ButtonCategory category;
    public int o;
    private final ArrayList<me.kopamed.raven.bplus.client.visual.clickgui.raven.Component> settings;
    public boolean po;

    public ModuleComponent(Module mod, CategoryComponent categoryComponent) {
        this.module = mod;
        this.settings = new ArrayList();
        this.categoryComponent = categoryComponent;
    }/*

    public void setModuleStartAt(int n) {
        this.o = n;
        int y = this.o + 16;
        Iterator var3 = this.settings.iterator();

        while(true) {
            while(var3.hasNext()) {
                me.kopamed.raven.bplus.client.visual.clickgui.raven.Component co = (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component)var3.next();
                co.setModuleStartAt(y);
                if (co instanceof ButtonSlider  || co instanceof ButtonMinMaxSlider) {
                    y += 16;
                } else if (co instanceof ButtonTick || co instanceof AutoConfig || co instanceof ButtonDesc || co instanceof ButtonMode) {
                    y += 12;
                }
            }

            return;
        }
    }

    public static void e() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void f() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
        GL11.glEdgeFlag(true);
    }

    public static void g(int h) {
        float a = 0.0F;
        float r = 0.0F;
        float g = 0.0F;
        float b = 0.0F;
        if (Gui.guiTheme.getInput() == 1.0D) {
            a = (float)(h >> 14 & 255) / 255.0F;
            r = (float)(h >> 5 & 255) / 255.0F;
            g = (float)(h >> 5 & 255) / 2155.0F;
            b = (float)(h & 255);
        } else if (Gui.guiTheme.getInput() == 2.0D) {
            a = (float)(h >> 14 & 255) / 255.0F;
            r = (float)(h >> 5 & 255) / 2155.0F;
            g = (float)(h >> 5 & 255) / 255.0F;
            b = (float)(h & 255);
        } else if (Gui.guiTheme.getInput() == 3.0D) {
        }

        GL11.glColor4f(r, g, b, a);
    }

    public static void v(float x, float y, float x1, float y1, int t, int b) {
        e();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        g(t);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        g(b);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        f();
    }

    public void draw() {
        v((float)this.category.getX(), (float)(this.category.getY() + this.o), (float)(this.category.getX() + this.category.getWidth()), (float)(this.category.getY() + 15 + this.o), this.mod.isEnabled() ? this.c2 : -12829381, this.mod.isEnabled() ? this.c2 : -12302777);
        GL11.glPushMatrix();
        // module text button
        int button_rgb = Gui.guiTheme.getInput() == 3.0D ? (this.mod.isEnabled() ? this.c1 : Color.lightGray.getRGB()) : (Gui.guiTheme.getInput() == 4.0D? (this.mod.isEnabled() ? this.c3 : Color.lightGray.getRGB()) : Color.lightGray.getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (float)(this.category.getX() + this.category.getWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.mod.getName()) / 2), (float)(this.category.getY() + this.o + 4), button_rgb);
        GL11.glPopMatrix();
        if (this.po && !this.settings.isEmpty()) {
            for (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component c : this.settings) {
                c.draw();
            }
        }

    }

    public void update(int x, int y) {
        if (!this.settings.isEmpty()) {
            for (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component c : this.settings) {
                c.update(x, y);
            }
        }

    }

    public void mouseDown(int x, int y, int b) {

        if (this.ii(x, y) && b == 0) {
            this.mod.toggle();
        }

        if (this.ii(x, y) && b == 1) {
            this.po = !this.po;
            this.category.r3nd3r();
        }

        for (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component c : this.settings) {
            c.mouseDown(x, y, b);
        }

    }

    public void mouseReleased(int x, int y, int b) {
        for (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component c : this.settings) {
            c.mouseReleased(x, y, b);
        }

    }

    public void keyTyped(char t, int k) {
        for (me.kopamed.raven.bplus.client.visual.clickgui.raven.Component c : this.settings) {
            c.keyTyped(t, k);
        }

    }

    public boolean ii(int x, int y) {
        return x > this.category.getX() && x < this.category.getX() + this.category.getWidth() && y > this.category.getY() + this.o && y < this.category.getY() + 16 + this.o;
    }*/

    @Override
    public void paint(GlyphPageFontRenderer fr) {
        Theme currentTheme = Raven.client.getClickGui().getTheme();
        float textMargin = (float)this.getWidth() * 0.0625f;
        double desiredTextSize = this.getHeight() * 0.6;
        double scaleFactor = desiredTextSize/ fr.getFontHeight();
        double coordFactor = 1/scaleFactor;
        double textY = this.getY() + (this.getHeight() - desiredTextSize) /2;

        Gui.drawRect((int)this.getX(), (int)this.getY(), (int)(this.getX() + this.getWidth()), (int)(this.getY() + this.getHeight()), module.isEnabled() ? currentTheme.getAccentColour().getRGB() : currentTheme.getSecondBackgroundColour().getRGB());

        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fr.drawString(module.getName(), (float)((this.getX() + textMargin) * coordFactor), (float)(textY * coordFactor), module.isEnabled() ? currentTheme.getSecondBackgroundColour().getRGB() : currentTheme.getTextColour().getRGB(), false);
        GL11.glPopMatrix();
        for(Component component: this.getComponents()){
            component.paint(fr);
        }
    }

    @Override
    public void update(int x, int y) {
        super.update(x, y);
    }

    @Override
    public void onResize() {
        this.setSize(categoryComponent.getWidth() - 2, categoryComponent.getHeight() * 0.8);
        super.onResize();
    }

    @Override
    public void mouseDown(int x, int y, int mb) {
        if(mouseOver(x, y))
            module.toggle();
        super.mouseDown(x, y, mb);
    }
}
