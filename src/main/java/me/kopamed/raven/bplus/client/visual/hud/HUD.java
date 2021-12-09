package me.kopamed.raven.bplus.client.visual.hud;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HUD {
    private InputStream inputStream;
    private ResourceLocation ravenLogo;

    private final double x;
    private final double y;
    private double width;
    private double height;
    private double logoWidthRatio = 0.14;
    private double logoHeightRatio =  0.063;
    private final double marginYRatio = 0.003;
    private final double textSizeRatio = 0.03;

    private final double speed = 1;
    private boolean dropShadow = false;

    public HUD(){
        this.x = 5;
        this.y = 5;
        this.setUpLogo();
    }

    private void setUpLogo() {
        inputStream = HUD.class.getResourceAsStream("/assets/raven/image/raven.png");
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(inputStream);
            ravenLogo = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven", new DynamicTexture(bf));
        } catch (IOException noway) {
            noway.printStackTrace();
            ravenLogo = null;
        } catch (IllegalArgumentException nowayV2) {
            nowayV2.printStackTrace();
            ravenLogo = null;
        } catch (NullPointerException nowayV3) {
            nowayV3.printStackTrace();
            ravenLogo = null;
        }
    }

    public void draw(TickEvent.RenderTickEvent ev){
        if(!canDraw())
            return;

        ScaledResolution sr = new ScaledResolution(Raven.client.getMc());
        FontRenderer fr = Raven.client.getFontRenderer();
        Theme theme = Raven.client.getClickGui().getTheme();

        double currentY = y;
        double marginY = sr.getScaledHeight() * marginYRatio;
        double logoHeight = sr.getScaledHeight() * logoHeightRatio;

        // drawing the logo
        if(logoLoaded()) {
            currentY += marginY + logoHeight;
        }

        ArrayList<Module> modules = Raven.client.getModuleManager().getEnabledModules();
        if(!modules.isEmpty()){
            modules.sort((o1, o2) -> fr.getStringWidth(o2.getName()) - fr.getStringWidth(o1.getName()));
            double desiredTextSize = sr.getScaledHeight() * textSizeRatio;
            double scaleFactor = desiredTextSize/ fr.FONT_HEIGHT;
            double coordFactor = 1/scaleFactor;

            GL11.glPushMatrix();
            GL11.glScaled(scaleFactor, scaleFactor, scaleFactor);
            for(Module module : modules){
                if(!((BooleanSetting)module.getSettingByName("Shown on HUD")).isToggled())
                    continue;
                fr.drawString(module.getName(), (float)(x), (float)(currentY * coordFactor), theme.getArrayListColour(currentY, sr.getScaledHeight(), speed).getRGB(), dropShadow);
                currentY += desiredTextSize + marginY;
            }
            GL11.glPopMatrix();
        }

        if(logoLoaded()){
            double logoWidth = sr.getScaledWidth() * logoWidthRatio;
            Minecraft.getMinecraft().getTextureManager().bindTexture(ravenLogo);
            GL11.glColor4f(1, 1, 1, 1);
            Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, 0, 0, (int) logoWidth, (int) logoHeight, (int) logoWidth, (int) logoHeight);
        }
    }

    public boolean logoLoaded(){
        return ravenLogo != null;
    }

    public double getLogoWidthRatio() {
        return logoWidthRatio;
    }

    public void setLogoWidthRatio(double logoWidthRatio) {
        this.logoWidthRatio = logoWidthRatio;
    }

    public double getLogoHeightRatio() {
        return logoHeightRatio;
    }

    public void setLogoHeightRatio(double logoHeightRatio) {
        this.logoHeightRatio = logoHeightRatio;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isDropShadow() {
        return dropShadow;
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    private boolean canDraw(){
        Minecraft mc = Raven.client.getMc();
        return mc.currentScreen == null && !mc.gameSettings.showDebugInfo;
    }
}

