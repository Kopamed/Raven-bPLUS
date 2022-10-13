package keystrokesmod.client.clickgui.kv.components;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import keystrokesmod.client.clickgui.kv.KvComponent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;

public class KvTickComponent extends KvComponent {

	private TickSetting setting;
	private CoolDown timer = new CoolDown(1);

	public KvTickComponent(Setting setting) {
		this.setting = (TickSetting) setting;
	}

    @Override
	public void draw(int mouseX, int mouseY) {

        int x = this.x + 5;

        float percent = Utils.Client.smoothPercent((setting.isToggled() ?  timer.getTimeLeft() : timer.getElapsedTime())/(float) timer.getCooldownTime());
        int red = (int) (percent * 255);
        int green = 255 - red;
        final int colour = new Color(red, green, 0).getRGB();
        float offSet = percent * 5;
        int fh = (Raven.mc.fontRendererObj.FONT_HEIGHT/2) + 1;

        RenderUtils.drawBorderedRoundedRect(x, y + fh, x + 15, y + height, 4, 2, Utils.Client.rainbowDraw(1, 0), 0xFF000000);
        RenderUtils.drawBorderedRoundedRect(x + offSet, y + fh, x + 10 + offSet, y + height, 4, 2, Utils.Client.rainbowDraw(1, 0), colour);
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().fontRendererObj.drawString(
                setting.getName(),
                (float) ((x + 17) * 2),
                (float) ((y + (height/2)) * 2),
                0xFEFFFFFF,
                false);
        GL11.glPopMatrix();
    }


    @Override
	public void clicked(int button, int x, int y) {
        timer.setCooldown(500);
        timer.start();
    	setting.toggle();
	}
}
