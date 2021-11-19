package me.kopamed.raven.bplus.client.visual.clickgui.plus;

import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.CategoryComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.ArcDark;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.MaterialDark;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.Vape;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.CommandLine;
import me.kopamed.raven.bplus.client.visual.clickgui.raven.components.ButtonCategory;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.helper.manager.VersionManager;
import me.kopamed.raven.bplus.helper.utils.Timer;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.superblaubeere27.client.utils.fontRenderer.GlyphPageFontRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlusGui extends GuiScreen {
    private ScheduledFuture sf;
    private Timer aT;
    private Timer aL;
    private Timer aE;
    private Timer aR;
    private ScaledResolution sr;
    private GuiButtonExt s;
    private GuiTextField c;
    private Theme theme;

    private String tooltip;
    private Object setter;

    private ArrayList<CategoryComponent> categories;
    private GlyphPageFontRenderer fontRenderer;


    public PlusGui() {
        this.categories = new ArrayList<>();
        this.tooltip = "";
        this.theme = new Vape();

        for(ModuleCategory moduleCategory : ModuleCategory.values()){
            CategoryComponent categoryComponent = new CategoryComponent(moduleCategory);
            categoryComponent.setDraggable(true);
            categories.add(categoryComponent);
        }
    }

    public void initMain() {
        (this.aT = this.aE = this.aR = new Timer(500.0F)).start();
        this.sf = Raven.client.getExecutor().schedule(() -> {
            (this.aL = new Timer(650.0F)).start();
        }, 650L, TimeUnit.MILLISECONDS);

    }

    public void initGui() {
        super.initGui();
        this.sr = new ScaledResolution(this.mc);
        this.fontRenderer = Raven.client.getFontRenderer();

        double marginX = sr.getScaledWidth() * 0.01;
        double marginY = sr.getScaledHeight() * 0.01;
        double catWidth = width * 0.1083;
        double catHeight = height * 0.04;
        double currentY = marginY;

        for(CategoryComponent categoryComponent : categories){
            categoryComponent.setSize(catWidth, catHeight);
            categoryComponent.setLocation(marginX, currentY);
            currentY += catHeight + marginY;
        }
    }

    public void drawScreen(int x, int y, float p) {
        ScaledResolution sr = new ScaledResolution(mc);
        double width = sr.getScaledWidth();
        double height = sr.getScaledHeight();

        double marginX = width * 0.01;
        double marginY = height * 0.01;

        double desiredTextSize = height * 0.024;
        double scaleFactor = desiredTextSize / fontRenderer.getFontHeight();
        double coordFactor = 1/scaleFactor;

        double barHeight = desiredTextSize * 1.6;
        double barTextY = (height - barHeight + (barHeight - desiredTextSize) / 2);
        double dateX = (width - (fontRenderer.getStringWidth(Utils.Java.getDate()) * scaleFactor + marginX));

        double entitySize = width * 0.05;
        double entityX = width - marginX - entitySize;
        double entityY = height - barHeight - 1 - marginY - entitySize;

        //bg
        drawRect(0, 0, (int)width, (int)height, (int)this.aR.getValueFloat(0.0F, Utils.Java.setTransparent(theme.getBackdropColour(), 90).getRGB(), 2));

        GuiInventory.drawEntityOnScreen((int) (entityX - entitySize * 0.2), (int) (entityY + entitySize * 0.2), (int) entitySize, (float)(entityX + entitySize/2 - x), (float)(entityY + entitySize/2 - y), this.mc.thePlayer);

        //task bar
        Gui.drawRect(0, (int)(height - barHeight), (int)width, (int) height, theme.getBackgroundColour().getRGB());
        Gui.drawRect(0, (int)(height - barHeight - 1),(int)width, (int) (height - barHeight), theme.getAccentColour().getRGB());

        //drawing all the text
        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fontRenderer.drawString("Made by Kopamed and Blowsy", (float)(marginX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);
        fontRenderer.drawString(Utils.Java.getDate(), (float)(dateX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);
        GL11.glPopMatrix();

        for (CategoryComponent category : categories) {
            category.update(x, y);
            category.paint(Raven.client.getFontRenderer());
        }
    }

    public void mouseClicked(int x, int y, int mouseButton) throws IOException {
        int categoryNumber = categories.size();

        for(int i = 0; i < categoryNumber; i++){
            CategoryComponent categoryComponent = categories.get(i);
            categoryComponent.mouseDown(x, y, mouseButton);
        }
    }

    public void mouseReleased(int x, int y, int s) {
        int categoryNumber = categories.size();

        for(int i = 0; i < categoryNumber; i++){
            CategoryComponent categoryComponent = categories.get(i);
            categoryComponent.mouseReleased(x, y, s);
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        System.out.println("KeyTyped: " + typedChar + " " + keyCode);
        //todo search
        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onGuiClosed() {
        this.aL = null;
        if (this.sf != null) {
            this.sf.cancel(true);
            this.sf = null;
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTooltip(String tooltip, Object setter) {
        this.tooltip = tooltip;
        this.setter = setter;
    }

    public Object getTooltipSetter() {
        return setter;
    }

    public String getTooltip(){
        return tooltip;
    }

    public void clearTooltip(){
        this.tooltip = "";
    }
}
