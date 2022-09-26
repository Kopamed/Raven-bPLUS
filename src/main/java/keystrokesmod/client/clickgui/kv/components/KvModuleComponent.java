package keystrokesmod.client.clickgui.kv.components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class KvModuleComponent extends KvComponent{

    public Module module;

    private static ResourceLocation settingIcon;
    private int toggleX, toggleY, toggleWidth, toggleHeight,
    			settingX, settingY, settingWidth, settingHeight,
    			settingX2, settingY2, settingWidth2, settingHeight2,
    			titleBoxX, titleBoxY, titleBoxWidth, titleBoxHeight,
    			settingsBoxX, settingsBoxY, settingsBoxWidth, settingsBoxHeight;

    static {
        InputStream ravenLogoInputStream = HUD.class.getResourceAsStream("/assets/keystrokesmod/kvclickgui/gear.png");
        BufferedImage bf;
        try {
            assert ravenLogoInputStream != null;
            bf = ImageIO.read(ravenLogoInputStream);
            settingIcon = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("raven",
                    new DynamicTexture(bf));
        } catch (IOException | IllegalArgumentException | NullPointerException noway) {
            noway.printStackTrace();
            settingIcon = null;
        }

    }

    public KvModuleComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //sorry sigma
        RenderUtils.drawRoundedRect(x, y, x + width, y + height, 12, 0xA0000000);
        RenderUtils.drawRoundedRect(toggleX, toggleY + 1, toggleX + toggleWidth, toggleY + toggleHeight + 1,12, module.isEnabled() ? 0xFF00FF00 : 0xFFFF0000, new boolean[] {false, true, false, false});
        RenderUtils.drawRoundedOutline(x, y, x + width, y + height, 12, 2, Utils.Client.rainbowDraw(1, 0));

        Gui.drawRect(toggleX, toggleY, toggleX + width, toggleY + 1, Utils.Client.rainbowDraw(1, 0));
        Gui.drawRect(settingX, settingY, settingX + 1, settingY + settingHeight, Utils.Client.rainbowDraw(1, 0));

        FontUtil.normal.drawCenteredString(module.getName(), x + (width / 2), (y + height) - (int) (((3 * height) / 3.8) / 2), 0xFFFFFFFF);
        FontUtil.two.drawCenteredString(module.isEnabled() ? "Enabled" : "Disabled", toggleX + (toggleWidth / 2), toggleY + (toggleHeight / 2), 0xFF000000);

        Minecraft.getMinecraft().getTextureManager().bindTexture(settingIcon);
        Gui.drawModalRectWithCustomSizedTexture(settingX, settingY, 0, 0, settingWidth, settingHeight, settingWidth, settingHeight);
    }

    public void drawOpen() {
        //title box
        RenderUtils.drawBorderedRoundedRect(
                titleBoxX,
                titleBoxY,
                titleBoxX + titleBoxWidth,
                titleBoxY + titleBoxHeight,
                8,
                2,
                Utils.Client.rainbowDraw(1, 0), 0x00FFFFFF);
        FontUtil.normal.drawString(module.getName(), titleBoxX + 2, titleBoxY + (titleBoxHeight/2), 0xFFFFFFFF);


        Minecraft.getMinecraft().getTextureManager().bindTexture(settingIcon);
        Gui.drawModalRectWithCustomSizedTexture(settingX2, settingY2, 0, 0, settingWidth2, settingHeight2, settingWidth2, settingHeight2);


        //settings
        RenderUtils.drawBorderedRoundedRect(
                settingsBoxX,
                settingsBoxY,
                settingsBoxX + settingsBoxWidth,
                settingsBoxY + settingsBoxHeight,
                8,
                2,
                Utils.Client.rainbowDraw(1, 0), 0xA0FFFFFF,
                new boolean[] {false, true, true, false});
    }

    
    public void clicked(int mouseButton, int x, int y) {
    	System.out.println("b");
    	if ((x > settingX2) && (x < (settingX2 + settingWidth2)) && (y > settingY2) && (y < (settingY2 + settingHeight2)))
			KvModuleSection.moduleSec.setOpenmodule(null);
		else if ((x > settingX) && (x < (settingX + settingWidth)) && (y > settingY) && (y < (settingY + settingHeight)))
			KvModuleSection.moduleSec.setOpenmodule(this);
		else if ((x > toggleX) && (x < (toggleX + toggleWidth)) && (y > toggleY) && (y < (toggleY + toggleHeight)))
			module.toggle();
    }

    @Override
    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
        toggleX = x;
        toggleY = y + (int) ((3 * height) / 3.8);
        toggleWidth = width - (int) (width/3.8);
        toggleHeight = (int) (height - ((3 * height) / 3.8));
        settingX = x + toggleWidth;
        settingY = toggleY;
        settingWidth = width - toggleWidth;
        settingHeight = toggleHeight + 1;
    }

    public void setBoxCoords(int x, int y, int width, int height) {
        titleBoxX = x + KvModuleSection.padding;
        titleBoxY = y + KvModuleSection.padding;
        titleBoxWidth = width - (KvModuleSection.padding * 2);
        titleBoxHeight = FontUtil.normal.getHeight() + 12;

        settingX2 = (titleBoxX + titleBoxWidth) - titleBoxHeight - 1;
        settingY2 = titleBoxY;
        settingWidth2 = titleBoxHeight;
        settingHeight2 = titleBoxHeight;

        settingsBoxX = titleBoxX + KvModuleSection.padding;
        settingsBoxY = titleBoxY + titleBoxHeight;
        settingsBoxWidth = titleBoxWidth - (KvModuleSection.padding * 2);
        settingsBoxHeight = height - titleBoxHeight - (KvModuleSection.padding * 2);
    }

    @Override
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
