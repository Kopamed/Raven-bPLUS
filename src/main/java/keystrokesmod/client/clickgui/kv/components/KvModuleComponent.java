package keystrokesmod.client.clickgui.kv.components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
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
    			nameHeight, bindBoxY, bindBoxHeight, halfSettingsBoxWidth,
    			rx, ry;
    private List<KvComponent> settings = new ArrayList<KvComponent>();
    private KvBindComponent bindComponent;

    public KvModuleComponent(Module module) {
        this.module = module;
        bindComponent = new KvBindComponent(module);
        moduleIcon = RenderUtils.getResourcePath("/assets/keystrokesmod/kvclickgui/" + module.moduleCategory().getName() + "/" + module.getName().toLowerCase() + ".png");
        for(Setting setting : module.getSettings())
			try {
				Class<? extends KvComponent> clazz = setting.getComponentType();
				settings.add(clazz.getDeclaredConstructor(Setting.class).newInstance(setting));
        	} catch(Exception e)  {}
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //sorry sigma
    	x = rx;
    	y = ry + KvModuleSection.moduleScroll;
        toggleX = x;
        toggleY = y + (int) ((3 * height) / 3.8);
        toggleWidth = width - (int) (width/3.8);
        toggleHeight = (int) (height - ((3 * height) / 3.8));
        settingX = x + toggleWidth;
        settingY = toggleY;
        settingWidth = width - toggleWidth;
        settingHeight = toggleHeight + 1;
        nameHeight = height - toggleHeight - FontUtil.normal.getHeight() - 1;

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

    public void drawOpen(int mouseX, int mouseY) {
        //bind + enable/disablebutton
        RenderUtils.drawRoundedRect(
                settingsBoxX,
                bindBoxY,
                settingsBoxX + halfSettingsBoxWidth,
                bindBoxY + bindBoxHeight,
                8,
                module.isEnabled() ? 0xFF00FF00 : 0xFFFF0000,
                new boolean[] {false, true, false, false});
        Gui.drawRect(settingsBoxX, bindBoxY, settingsBoxX + settingsBoxWidth, bindBoxY + 1, Utils.Client.rainbowDraw(1, 0));
        Minecraft.getMinecraft().fontRendererObj.drawString(module.isEnabled() ? "Enabled" : "Disabled", settingsBoxX + (halfSettingsBoxWidth/4), (bindBoxY + (bindBoxHeight/2)) - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2), 0xFF000000);
        bindComponent.draw(mouseX, mouseY);

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

        //settings icon
        Gui.drawModalRectWithCustomSizedTexture(settingX2, settingY2, 0, 0, settingWidth2, settingHeight2, settingWidth2, settingHeight2);

        //settings box
        RenderUtils.drawBorderedRoundedRect(
                settingsBoxX,
                settingsBoxY,
                settingsBoxX + settingsBoxWidth,
                settingsBoxY + settingsBoxHeight,
                8,
                2,
                Utils.Client.rainbowDraw(1, 0), 0x30000000,
                new boolean[] {false, true, true, false});

        //settings
        int yOffset = KvModuleSection.padding;
        int xOffset = KvModuleSection.padding;
        int i = 0;
        for(KvComponent component : settings) {
        	i++;
        	component.setCoords(settingsBoxX + xOffset, settingsBoxY + yOffset + KvModuleSection.moduleScroll);
        	component.setDimensions((settingsBoxWidth/2) - (KvModuleSection.padding * 2), 12);
        	yOffset += component.getHeight() + 2;
        	if(i == (settings.size()/2)) {
        		yOffset = KvModuleSection.padding;
        		xOffset = settingsBoxWidth/2;
        	}
        }
        int sf = new ScaledResolution(Raven.mc).getScaleFactor();
        GL11.glScissor(settingsBoxX * sf, (titleBoxY - ((titleBoxHeight - bindBoxHeight) + (KvModuleSection.padding * 3)))* sf, settingsBoxWidth * sf, (settingsBoxHeight - bindBoxHeight) * sf);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        for(KvComponent component : settings)
			component.draw(mouseX, mouseY);
        	//fucked lol should have done this before
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }


    @Override
	public void clicked(int mouseButton, int x, int y) {
    	if(KvModuleSection.moduleSec.openModule != null) {
    		if ((x > settingX2) && (x < (settingX2 + settingWidth2)) && (y > settingY2) && (y < (settingY2 + settingHeight2))) {
    			KvModuleSection.moduleSec.setOpenmodule(null);
    			return;
    		}
    		if ((x > settingsBoxX) && (x < (settingsBoxX + halfSettingsBoxWidth)) && (y > bindBoxY) && (y < (bindBoxY + bindBoxHeight)))
    			module.toggle();
    		bindComponent.mouseDown(x, y, mouseButton);
    		for(KvComponent component : settings)
				component.mouseDown(x, y, mouseButton);
        }

		else if ((x > settingX) && (x < (settingX + settingWidth)) && (y > settingY) && (y < (settingY + settingHeight)))
			KvModuleSection.moduleSec.setOpenmodule(this);
		else if ((x > toggleX) && (x < (toggleX + toggleWidth)) && (y > bindBoxY) && (y < (bindBoxY + bindBoxHeight)))
			module.toggle();
    }

    @Override
    public void setCoords(int x, int y) {
        rx = x;
        ry = y;
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

        bindBoxHeight = height/7;
        bindBoxY = (settingsBoxY + settingsBoxHeight) - bindBoxHeight;
        halfSettingsBoxWidth = settingsBoxWidth/2;

        bindComponent.setCoords(settingsBoxX + halfSettingsBoxWidth, bindBoxY);
        bindComponent.setDimensions(halfSettingsBoxWidth, bindBoxHeight);

    }

    @Override
    public void mouseReleased(int x, int y, int button) {
    	for(KvComponent component : settings)
			component.mouseReleased(x, y, button);
    }

    @Override
    public void keyTyped(char t, int k) {
    	bindComponent.keyTyped(t, k);
    }

    public int maxScroll() {
    	return settings.isEmpty() ? 0 : (int) -(settings.size()/2) * settings.get(0).getHeight();
    }

}
