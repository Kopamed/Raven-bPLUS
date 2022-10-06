package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class KvModuleComponent extends KvComponent{

    private Module module;

    private final static ResourceLocation settingIcon = RenderUtils.getResourcePath("/assets/keystrokesmod/kvclickgui/gear.png");;
    private ResourceLocation moduleIcon;
    private int toggleX, toggleY, toggleWidth, toggleHeight,
    			settingX, settingY, settingWidth, settingHeight,
    			settingX2, settingY2, settingWidth2, settingHeight2,
    			titleBoxX, titleBoxY, titleBoxWidth, titleBoxHeight,
    			settingsBoxX, settingsBoxY, settingsBoxWidth, settingsBoxHeight,
    			nameHeight;
    private List<KvComponent> settings = new ArrayList<KvComponent>();

    public KvModuleComponent(Module module) {
        this.module = module;
        moduleIcon = RenderUtils.getResourcePath("/assets/keystrokesmod/kvclickgui/" + module.moduleCategory().getName() + "/" + module.getName().toLowerCase() + ".png");
        for(Setting setting : module.getSettings())
			try {
				Class<? extends KvComponent> clazz = setting.getComponentType();
				clazz.getDeclaredConstructor(Setting.class).newInstance(setting);
        	} catch(Exception e)  {}
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //sorry sigma
        RenderUtils.drawRoundedRect(x, y, x + width, y + height, 12, 0xA0000000);
        RenderUtils.drawRoundedRect(toggleX, toggleY + 1, toggleX + toggleWidth, toggleY + toggleHeight + 1,12, module.isEnabled() ? 0xFF00FF00 : 0xFFFF0000, new boolean[] {false, true, false, false});
        RenderUtils.drawRoundedOutline(x, y, x + width, y + height, 12, 2, Utils.Client.rainbowDraw(1, 0));

        Minecraft.getMinecraft().getTextureManager().bindTexture(moduleIcon);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1f);
        Gui.drawModalRectWithCustomSizedTexture(x + (FontUtil.normal.getHeight()/2), y, 0, 0, width - FontUtil.normal.getHeight(), nameHeight, width - FontUtil.normal.getHeight(), nameHeight);

        Gui.drawRect(toggleX, toggleY, toggleX + width, toggleY + 1, Utils.Client.rainbowDraw(1, 0));
        Gui.drawRect(settingX, settingY, settingX + 1, settingY + settingHeight, Utils.Client.rainbowDraw(1, 0));

        FontUtil.normal.drawCenteredString(module.getName(), x + (width / 2), y + nameHeight, 0xFFFFFFFF);
        FontUtil.two.drawCenteredString(module.isEnabled() ? "Enabled" : "Disabled", toggleX + (toggleWidth / 2), toggleY + (toggleHeight / 2), 0xFF000000);

        Minecraft.getMinecraft().getTextureManager().bindTexture(settingIcon);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1f);
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
                Utils.Client.rainbowDraw(1, 0), 0x30000000,
                new boolean[] {false, true, true, false});
    }


    @Override
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
        nameHeight = height - toggleHeight - FontUtil.normal.getHeight() - 1;
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
