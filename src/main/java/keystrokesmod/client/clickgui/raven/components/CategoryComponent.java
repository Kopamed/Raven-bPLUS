package keystrokesmod.client.clickgui.raven.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
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
    private ModuleComponent openComponent;
    public Module.ModuleCategory categoryName;
    public boolean categoryOpened, inUse, dragging;
    public boolean visable = true;
    public int scrollheight, dragX, dragY, heightCheck, deltaHeight, prevHeight, bottomX, bottomY;
    public double theta, velo = 0.1;
    public int aHeight = 13;

    private CoolDown timer = new CoolDown(500);
    public float tPercent;

    private final int marginX = 80
                    ,marginY = 3;

    private final float gravity = 0.9f,
                    friction = 0.7f;


    public CategoryComponent(Module.ModuleCategory category) {
        categoryName = category;
        setDimensions(92, aHeight);
        Raven.moduleManager.getModulesInCategory(category).forEach(module -> modulesInCategory.add(new ModuleComponent(module, this)));
        modulesInCategory.sort(Comparator.comparingDouble(module -> FontUtil.normal.getStringWidth(module.mod.getName())));
        Collections.reverse(modulesInCategory);
    }

    public void initGui() {
        bottomX = x + (width/2);
        bottomY = y + (height/2);
    }

    @Override
    public void guiClosed() {
        heightCheck = 0;
    }

    @Override
    public void setCoords(int x, int y) {
        super.setCoords(x, y);
        if (Raven.clientConfig != null)
            Raven.clientConfig.saveConfig();
    }

    public void setOpened(boolean on) {
        categoryOpened = on;
        if (Raven.clientConfig != null)
            Raven.clientConfig.saveConfig();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (!visable)
            return;
        Minecraft mc = Minecraft.getMinecraft();
        //smooth height moving
        int newHeight = 0;
        if(categoryOpened) {
            if(openComponent != null) {
                newHeight = openComponent.getHeight();
            } else {
                for(ModuleComponent moduleComponent : modulesInCategory)
                    newHeight += moduleComponent.getHeight();
            }
        }

        if(heightCheck != newHeight) {
            prevHeight = heightCheck;
            deltaHeight = newHeight - heightCheck;
            heightCheck = newHeight;
            timer.setCooldown(500);
            timer.start();
        }
        tPercent = Utils.Client.smoothPercent(timer.getElapsedTime() / (float) timer.getCooldownTime());
        setDimensions(width, aHeight + prevHeight + (int) (deltaHeight * tPercent));

        //dragging bit
        if(dragging)
            setCoords(mouseX + dragX, mouseY + dragY);

        //swing bit VERY BROKEN
        GL11.glPushMatrix();
        if(GuiModule.isSwingToggled() && GuiModule.isSwingToggled()) { //to make it not work
            int topX = x + (width/2),
                topY = y + (height/2);

            double d = Math.sqrt(Math.pow((topX - bottomX), 2) + Math.pow((topY - bottomY), 2));
            theta = Math.acos(Math.toRadians(
                            ((2 * Math.pow(height,2)) + (Math.pow(d, 2)))
                            /
                            (2 * height * height)
                            ));

            velo = velo + (theta * gravity);
            double ntheta = theta + (velo * friction);
            bottomX = x - (int) (Math.sin(Math.toRadians(ntheta)) * height);
            bottomY = y - (int) (Math.cos(Math.toRadians(ntheta)) * height);
        }

        // background
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.glScissor(x - 1, y, x2 + 1, y2 + 1);
        int bgColor = openComponent != null ? GuiModule.getCategoryBackgroundRGB() : GuiModule.getSettingBackgroundRGB();
        if (!GuiModule.isRoundedToggled()) Gui.drawRect(x, y, x2, y2, bgColor);
        else RenderUtils.drawRoundedRect(x, y, x2, y2, 12, bgColor);

        // drawing modules
        if (categoryOpened || (tPercent < 1))
            if(openComponent != null) {
                openComponent.setCoords(x, y + aHeight);
                openComponent.draw(mouseX, mouseY);
            } else {
                int yOffset = 0;
                for(ModuleComponent module : modulesInCategory) {
                    module.setCoords(x, y + aHeight + yOffset);
                    module.draw(mouseX, mouseY);
                    yOffset += module.getHeight();
                }
            }

        // boarder
        if (GuiModule.isBoarderToggled()) {
            if(isMouseOver(mouseX, mouseY)) {
                if (!GuiModule.isRoundedToggled()) Gui.drawRect(x, y, x2, y2, GuiModule.getCategoryOutlineColor2());
                else RenderUtils.drawRoundedOutline(x, y, x2, y2, 12, 3, GuiModule.getCategoryOutlineColor2());
            } else if (!GuiModule.isRoundedToggled()) Gui.drawRect(x, y, x2, y2, GuiModule.getCategoryOutlineColor1());
            else RenderUtils.drawRoundedOutline(x, y, x2, y2, 12, 3, GuiModule.getCategoryOutlineColor1());
            GlStateManager.resetColor();
        }

        // category name
        if (GuiModule.useCustomFont()) FontUtil.two.drawSmoothString(categoryName.getName(), (float) (x + 2), (float) (y + 4), GuiModule.getCategoryNameRGB());
        else mc.fontRendererObj.drawString(categoryName.getName(), (float) (x + 2), (float) (y + 4),GuiModule.getCategoryBackgroundRGB(), false);

        // +/- bit
        int red = (int) (tPercent * 255);
        int green = 255 - red;
        final int colour = new Color(red, green, 0).getRGB();
        mc.fontRendererObj.drawString(categoryOpened ? "-" : "+", x + marginX, y + marginY, colour, false);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void scroll(float ss) {
        if (openComponent != null)
            openComponent.scroll(ss);
    }

    @Override
    public void clicked(int x, int y, int button) {
        if (overExpandButton(x, y)) {
            setOpened(!categoryOpened);
            return;
        } if (overName(x, y)) {
            dragX = this.x - x;
            dragY = this.y - y;
            dragging = true;
            return;
        }
        if(openComponent == null)
            for(ModuleComponent module : modulesInCategory) {
                if(module.mouseDown(x, y, button))
                    return;
            }
        else
            openComponent.mouseDown(x, y, button);
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        dragging = false;
        modulesInCategory.forEach(module -> module.mouseReleased(x, y, button));
    }

    @Override
    public void keyTyped(char t, int k) {
        if(openComponent == null)
            return;
        openComponent.keyTyped(t,k);
    }


    public void updateModules() {
        modulesInCategory.clear();
        Raven.moduleManager.getModulesInCategory(categoryName).forEach(module -> modulesInCategory.add(new ModuleComponent(module, this)));
        modulesInCategory.sort(Comparator.comparingDouble(module -> FontUtil.normal.getStringWidth(module.mod.getName())));
        Collections.reverse(modulesInCategory);
    }

    public boolean overExpandButton(int mouseX, int mouseY) {
        return (mouseX > (x + marginX)) && (mouseX < (x + width)) && (mouseY > y) && (mouseY < (y + aHeight));
    }

    public boolean overName(int mouseX, int mouseY) {
        return ((mouseX > (x)) && (mouseX < (x2)) && (mouseY > y) && (mouseY < (y + aHeight)));
    }


    public void setOpenModule(ModuleComponent component) {
        openComponent = component;
    }

    public ModuleComponent getOpenModule() {
        return openComponent;
    }

}