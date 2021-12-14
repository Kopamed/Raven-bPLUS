package me.kopamed.raven.bplus.client.visual.clickgui.plus;

import me.kopamed.raven.bplus.client.feature.module.BindMode;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.Component;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.CategoryComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.Terminal;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.component.components.settings.BindComponent;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.ArcDark;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.MaterialDark;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.themes.Vape;
import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.helper.manager.version.Version;
import me.kopamed.raven.bplus.helper.utils.Timer;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlusGui extends GuiScreen {
    private final String defaultTooltip;
    private ScheduledFuture sf;
    private Timer aT;
    private Timer aL;
    private Timer aE;
    private Timer aR;
    private ScaledResolution sr;
    private GuiButtonExt s;
    private GuiTextField c;
    private final Theme theme;

    //private final NotificationManager notificationManager;

    private String tooltip;
    private Object setter;

    private final ArrayList<CategoryComponent> categories;
    private final Terminal terminal;
    private FontRenderer fontRenderer;

    public static final double goldenRatio = 1.618033988749894;


    public PlusGui() {
        this.categories = new ArrayList<>();
        this.tooltip = "";
        this.terminal = new Terminal();
        terminal.setColor(new Color(48, 48, 48));
        terminal.setVisible(false);
        //this.notificationManager = new NotificationManager();
        Version clientVersion = Raven.client.getVersionManager().getClientVersion();
        this.defaultTooltip = "b" + clientVersion.getBranchCommit() + " of v" + clientVersion.getVersion() + " on branch " + clientVersion.getBranchName();
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

        int categoryNumber = ModuleCategory.values().length;
        double marginX = sr.getScaledWidth() * 0.01;
        double marginY = sr.getScaledHeight() * 0.01;
        double totalMarginSpace = (categoryNumber + 1) * marginX;
        double catWidth = (width - totalMarginSpace) / categoryNumber;
        double catHeight = catWidth * (1/(goldenRatio * 3));
        double currentX = marginX;

        // todo @Kopamed one thing u should add is instead of having the category tabs be closed and vertically stacked by default have them open and stacked horizontally instead
        for(CategoryComponent categoryComponent : categories){
            categoryComponent.setSize(catWidth, catHeight);
            categoryComponent.setLocation(currentX, marginY);
            categoryComponent.onResize();
            currentX += catWidth + marginX;
        }

        terminal.onResize();
        terminal.setLocation((sr.getScaledWidth() - terminal.getWidth()) * 0.5, (sr.getScaledHeight() - terminal.getHeight()) * 0.5);
    }

    public void drawScreen(int x, int y, float p) {
        ScaledResolution sr = new ScaledResolution(mc);
        double width = sr.getScaledWidth();
        double height = sr.getScaledHeight();

        double marginX = width * 0.01;
        double marginY = height * 0.01;

        double desiredTextSize = height * 0.024;
        double scaleFactor = desiredTextSize / fontRenderer.FONT_HEIGHT;
        double coordFactor = 1/scaleFactor;

        double desiredTooltipSize = desiredTextSize * 0.5;
        double tooltipScaleFactor = desiredTooltipSize / fontRenderer.FONT_HEIGHT;
        double tooltipCoordFactor = 1/tooltipScaleFactor;

        double barHeight = desiredTextSize * 1.6;
        double barTextY = (height - barHeight + (barHeight - desiredTextSize) / 2);
        double dateX = (width - (fontRenderer.getStringWidth(Utils.Java.getDate()) * scaleFactor + marginX));
        double tooltipX = 0;
        double tooltipSize = fontRenderer.getStringWidth(tooltip.isEmpty() ? defaultTooltip : tooltip) * (tooltip.isEmpty() ? scaleFactor : tooltipScaleFactor);
        tooltipX = (width - tooltipSize) / 2;


        double entitySize = width * 0.05;
        double entityX = width - marginX - entitySize;
        double entityY = height - barHeight - 1 - marginY - entitySize;

        //bg
        drawRect(0, 0, (int)width, (int)height, (int)this.aR.getValueFloat(0.0F, Utils.Java.setTransparent(theme.getBackdropColour(), 90).getRGB(), 2));

        //GuiInventory.drawEntityOnScreen((int) (entityX - entitySize * 0.2), (int) (entityY + entitySize * 0.2), (int) entitySize, (float)(entityX + entitySize* 0.5 - x), (float)(entityY + entitySize* 0.5 - y), this.mc.thePlayer);

        //task bar
        Gui.drawRect(0, (int)(height - barHeight), (int)width, (int) height, theme.getBackgroundColour().getRGB());
        Gui.drawRect(0, (int)(height - barHeight - 1),(int)width, (int) (height - barHeight), theme.getAccentColour().getRGB());

        //drawing all the text
        GL11.glPushMatrix();
        GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
        fontRenderer.drawString("Made by Kopamed and Blowsy", (float)(marginX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);
        fontRenderer.drawString(Utils.Java.getDate(), (float)(dateX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);
        if(tooltip.isEmpty())
            fontRenderer.drawString(tooltip.isEmpty() ? defaultTooltip : tooltip, (float) (tooltipX * coordFactor), (float)(barTextY * coordFactor), theme.getTextColour().getRGB(), false);
        GL11.glPopMatrix();

        if(!tooltip.isEmpty()) {
            GL11.glPushMatrix();
            GL11.glScaled(tooltipScaleFactor, tooltipScaleFactor, tooltipScaleFactor);
            fontRenderer.drawString(tooltip, (float) (tooltipX * tooltipCoordFactor), (float) (barTextY * tooltipCoordFactor), theme.getTextColour().getRGB(), false);
            GL11.glPopMatrix();
        }


        terminal.update(x, y);
        terminal.paint(fontRenderer);


        for (CategoryComponent category : categories) {
            category.update(x, y);
            category.paint(fontRenderer);
        }
    }

    public void mouseClicked(int x, int y, int mouseButton) throws IOException {
        int categoryNumber = categories.size();

        terminal.mouseDown(x, y, mouseButton);

        for(int i = 0; i < categoryNumber; i++){
            CategoryComponent categoryComponent = categories.get(i);
            categoryComponent.mouseDown(x, y, mouseButton);
        }
    }

    public void mouseReleased(int x, int y, int s) {
        int categoryNumber = categories.size();

        terminal.mouseReleased(x, y, s);

        for(int i = 0; i < categoryNumber; i++){
            CategoryComponent categoryComponent = categories.get(i);
            categoryComponent.mouseReleased(x, y, s);
        }
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        System.out.println("KeyTyped: " + typedChar + " " + keyCode);

        terminal.keyTyped(typedChar, keyCode);

        if(!binding()){
            try {
                super.keyTyped(typedChar, keyCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(CategoryComponent categoryComponent : categories){
            categoryComponent.keyTyped(typedChar, keyCode);
        }
    }

    public void onGuiClosed() {
        this.aL = null;
        if (this.sf != null) {
            this.sf.cancel(true);
            this.sf = null;
        }

        for(Module module : Raven.client.getModuleManager().getModules()){
            module.onGuiClose();
        }

        Raven.client.getConfigManager().saveConfig();
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

    //public NotificationManager getNotificationManager() {
        //return notificationManager;
    //}

    private boolean binding() {
        for (CategoryComponent categoryComponent : categories) {
            for (Component module : categoryComponent.getComponents()) {
                for (Component setting : module.getComponents()) {
                    if (setting instanceof BindComponent) {
                        if (((BindComponent) setting).isListening())
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
