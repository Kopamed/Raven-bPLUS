package me.kopamed.raven.bplus.client.visual.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class HUD {
    private InputStream ravenLogoInputStream;
    private ResourceLocation mResourceLocation;
    public HUD(){
        this.setUpLogo();
    }

    private void setUpLogo() {
        ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/raven/image/raven.png");
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(ravenLogoInputStream);
            mResourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven", new DynamicTexture(bf));
        } catch (IOException noway) {
            noway.printStackTrace();
            mResourceLocation = null;
        } catch (IllegalArgumentException nowayV2) {
            nowayV2.printStackTrace();
            mResourceLocation = null;
        } catch (NullPointerException nowayV3) {
            nowayV3.printStackTrace();
            mResourceLocation = null;
        }
    }
}

