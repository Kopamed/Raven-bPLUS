package keystrokesmod.client.clickgui.raven.components;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;

public class TickComponent extends SettingComponent {

    private TickSetting setting;
    private CoolDown timer = new CoolDown(1);
    private final int buttonWidth = 15;

    public TickComponent(Setting setting, ModuleComponent category) {
        super(setting, category);
        this.setting = (TickSetting) setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        setDimensions(moduleComponent.getWidth() - 10, 14);
        int x = this.x + 5;

        float percent = Utils.Client.smoothPercent((setting.isToggled() ?  timer.getTimeLeft() : timer.getElapsedTime())/(float) timer.getCooldownTime());
        int red = (int) (percent * 255);
        int green = 255 - red;
        final int colour = new Color(red, green, 0).getRGB();
        float offSet = (percent * buttonWidth)/3;
        int fh = (Raven.mc.fontRendererObj.FONT_HEIGHT/2) + 1;

        RenderUtils.drawBorderedRoundedRect(x, y + fh, x + buttonWidth, y + height, 7, 2, GuiModule.getBoarderColour(), 0xFF000000);
        RenderUtils.drawBorderedRoundedRect(x + offSet, y + fh, x + ((buttonWidth/3)*2) + offSet, y + height, 7, 2, GuiModule.getBoarderColour(), colour);
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getName(),
                (float) ((x + buttonWidth + 2) * 2),
                (float) ((y + (height/2)) * 2),
                0xFEFFFFFF,
                false);
        GL11.glPopMatrix();
    }


    @Override
    public void clicked(int x, int y, int button) {
        timer.setCooldown(500);
        timer.start();
        setting.toggle();
        Utils.Player.sendMessageToSelf(setting.isToggled() + "");
    }

}