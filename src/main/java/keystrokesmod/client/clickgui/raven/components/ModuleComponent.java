package keystrokesmod.client.clickgui.raven.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.utils.font.FontUtil;

public class ModuleComponent extends Component {
    public Module mod;
    public CategoryComponent category;
    public ArrayList<SettingComponent> settings = new ArrayList<>();
    public BindComponent bind = new BindComponent(this);
    public int aHeight = 20;

    public ModuleComponent(Module mod, CategoryComponent p) {
        this.mod = mod;
        this.category = p;
        mod.setModuleComponent(this);
        mod.getSettings().forEach(setting -> {
            Class<? extends SettingComponent> clazz = setting.getRavenComponentType();
            try {
                settings.add(clazz.getDeclaredConstructor(Setting.class, this.getClass()).newInstance(setting, this));
            } catch (Exception e) {e.printStackTrace();};
        });
        setDimensions(p.getWidth(), aHeight);
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

    public static void g(int rgb) {
        Color c = new Color(rgb);
        float a = 255;
        float r = c.getRed() / 255f;
        float g = c.getGreen() / 255f;
        float b = c.getBlue() / 255f;
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

    @Override
    public void draw(int mouseX, int mouseY) {

        //background
        //Utils.Player.sendMessageToSelf(GuiModule.useCustomFont() + "");
        if (GuiModule.showGradientEnabled() && mod.isEnabled()) v(x, y, x2, y + aHeight, GuiModule.getEnabledBottomRGB(), GuiModule.getEnabledTopRGB());
        if (GuiModule.showGradientDisabled() && !mod.isEnabled()) v(x, y, x2, y + aHeight, GuiModule.getDisabledBottomRGB(), GuiModule.getDisabledTopRGB());

        //name
        int button_rgb = mod.isEnabled() ? GuiModule.getEnabledTextRGB() : this.mod.canBeEnabled() ? GuiModule.getDisabledTextRGB() : 0xFF999999;
        GL11.glPushMatrix();
        if (GuiModule.useCustomFont()) FontUtil.normal.drawCenteredStringWithShadow(mod.getName(), x + (width/2), y + (aHeight/2), button_rgb);
        else Raven.mc.fontRendererObj.drawString(mod.getName(), (x + (width/2)) - (Raven.mc.fontRendererObj.getStringWidth(mod.getName())/2), y + (aHeight/2), button_rgb, true);
        GL11.glPopMatrix();

        //settings
        if(category.getOpenModule() == this) {
            int yOffset = 0;
            for(SettingComponent setting : settings) {
                setting.setCoords(x, y + aHeight + yOffset);
                setting.draw(mouseX, mouseY);
                yOffset += setting.getHeight();
            }
            setDimensions(width, aHeight + yOffset);
        }
    }

    @Override
    public void clicked(int x, int y, int b) {
        if(b == 0) {
            if(insideNameArea(x, y))
                mod.toggle();
        } else if(b == 1)
            if(!settings.isEmpty() && insideNameArea(x, y)) {
                category.setOpenModule(category.getOpenModule() == this ? null : this);
                setDimensions(category.getWidth(), aHeight);
            }
    }

    public boolean insideNameArea(int mouseX, int mouseY) {
        return ((mouseX > (x)) && (mouseX < (x2)) && (mouseY > y) && (mouseY < (y + aHeight)));
    }
}