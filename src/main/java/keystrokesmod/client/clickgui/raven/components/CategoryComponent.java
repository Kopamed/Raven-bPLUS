package keystrokesmod.client.clickgui.raven.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.raven.Component;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class CategoryComponent extends Component {
    public ArrayList<ModuleComponent> modulesInCategory = new ArrayList<>();
    public ModuleComponent OpenComponent;
    public Module.ModuleCategory categoryName;
    public boolean moduleOpened, categoryOpened, inUse;
    public boolean visable = true;
    public int scrollheight;

    private CoolDown timer = new CoolDown(1);
    public float tPercent;

    private final int bh = 13;
    private final int marginX = 80;
    private final int marginY = 3;
    private final static int width = 92;

    public CategoryComponent(Module.ModuleCategory category) {
        categoryName = category;
        Raven.moduleManager.getModulesInCategory(category)
                .forEach(module -> modulesInCategory.add(new ModuleComponent(module, this)));
        modulesInCategory
                .sort(Comparator.comparingDouble(module -> FontUtil.normal.getStringWidth(module.mod.getName())));
    }

    public void loadSpecificModule(ModuleComponent component) {
        OpenComponent = component;
    }

    @Override
    public void setCoords(int x, int y) {
        super.setCoords(x, y);
        if (Raven.clientConfig != null)
            Raven.clientConfig.saveConfig();
    }

    public void setOpened(boolean on) {
        categoryOpened = on;
        timer.setCooldown(500);
        timer.start();
        if (Raven.clientConfig != null)
            Raven.clientConfig.saveConfig();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (!visable)
            return;

        Minecraft mc = Minecraft.getMinecraft();

        tPercent = Utils.Client.smoothPercent(categoryOpened ? timer.getElapsedTime() / (float) timer.getCooldownTime()
                : timer.getTimeLeft() / (float) timer.getCooldownTime());

        height = 0;
        modulesInCategory.forEach(module -> height += module.getHeight());
        height = (int) (height * tPercent);

        // background
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.glScissor(x - 1, y, x + width + 1, y + bh + height);
        int bgColor = moduleOpened ? GuiModule.getCategoryBackgroundRGB() : GuiModule.getSettingBackgroundRGB();
        if (!GuiModule.isRoundedToggled())
            Gui.drawRect(x - 1, y, x + width + 1, y + bh + height, bgColor);
        else
            RenderUtils.drawRoundedRect(x - 1, y, x + width + 1, y + bh + height,
                    12, bgColor);

        // boarder
        if (GuiModule.isBoarderToggled()) {
            if (!GuiModule.isRoundedToggled())
                Gui.drawRect(x - 1, y, x + width + 1, y + bh + 3,
                        GuiModule.getBackgroundRGB());
            else
                RenderUtils.drawRoundedOutline(x - 1, y, x + width + 1,
                        y + bh + height, 12, 3, GuiModule.getBoarderColour());
            GlStateManager.resetColor();
        }
        // category name
        if (GuiModule.useCustomFont())
            FontUtil.two.drawSmoothString(categoryName.getName(), (float) (x + 2), (float) (y + 4),
                    GuiModule.getCategoryBackgroundRGB());
        else
            mc.fontRendererObj.drawString(categoryName.getName(), (float) (x + 2), (float) (y + 4),
                    GuiModule.getCategoryBackgroundRGB(), false);

        // +/- bit
        int red = (int) (tPercent * 255);
        int green = 255 - red;
        final int colour = new Color(red, green, 0).getRGB();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.fontRendererObj.drawString(categoryOpened ? "-" : "+", x + marginX, y + marginY, 0xFFFFFFFF,
                false);

        // drawing modules
        if (tPercent > 0)
            modulesInCategory.forEach(module -> module.draw(mouseX, mouseY));

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void scroll(float ss) {
        if (OpenComponent != null)
            OpenComponent.scroll(ss);
    }

    public void clicked() {
        System.out.println("clicked");
        if (overExpandButton())
            setOpened(!categoryOpened);
    }


    public void updateModules() {
        modulesInCategory.clear();
        Raven.moduleManager.getModulesInCategory(categoryName)
                .forEach(module -> modulesInCategory.add(new ModuleComponent(module, this)));
        modulesInCategory
                .sort(Comparator.comparingDouble(module -> FontUtil.normal.getStringWidth(module.mod.getName())));
    }

    public boolean overExpandButton() {
        return (x > (x + marginX)) && (x < (x + width)) && (y > y) && (y < (y + height));
    }

}